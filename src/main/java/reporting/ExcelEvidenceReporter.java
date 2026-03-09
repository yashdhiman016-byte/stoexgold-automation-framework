package reporting;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread-safe utility for creating and maintaining the QA Pass/Fail Excel evidence report.
 */
public final class ExcelEvidenceReporter {

    private static final String[] HEADERS = {
            "Test Case ID",
            "Test Name",
            "Description",
            "Expected Result",
            "Actual Result",
            "Status (PASS / FAIL)",
            "Screenshot",
            "Execution Date & Time"
    };

    private static final String STATUS_PASS = "PASS";
    private static final String STATUS_FAIL = "FAIL";
    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DISPLAY_TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Path REPORT_ROOT = Paths.get("QA_Evidence_Reports");
    private static final Path SCREENSHOT_ROOT = REPORT_ROOT.resolve("Screenshots");
    private static final Path PASS_DIR = SCREENSHOT_ROOT.resolve("PASS");
    private static final Path FAIL_DIR = SCREENSHOT_ROOT.resolve("FAIL");

    private static final Object LOCK = new Object();
    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    private static XSSFWorkbook workbook;
    private static Sheet sheet;
    private static Path reportFilePath;
    private static int rowIndex = 1;

    private static CellStyle headerStyle;
    private static CellStyle baseCellStyle;
    private static CellStyle passCellStyle;
    private static CellStyle failCellStyle;
    private static CellStyle linkCellStyle;

    private ExcelEvidenceReporter() {
    }

    public static void initializeReport() {
        synchronized (LOCK) {
            if (INITIALIZED.get()) {
                return;
            }
            try {
                Files.createDirectories(PASS_DIR);
                Files.createDirectories(FAIL_DIR);

                String fileName = "QA_PassFail_Report_" + LocalDateTime.now().format(FILE_TS) + ".xlsx";
                reportFilePath = REPORT_ROOT.resolve(fileName);

                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("QA Evidence");

                createStyles();
                createHeader();
                sheet.createFreezePane(0, 1);

                INITIALIZED.set(true);
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize QA evidence report", e);
            }
        }
    }

    public static String captureScreenshot(WebDriver driver, String status, String testName) {
        synchronized (LOCK) {
            initializeReport();
            if (driver == null) {
                return "";
            }
            try {
                String safeName = sanitize(testName) + "_" + LocalDateTime.now().format(FILE_TS) + ".png";
                Path targetDir = STATUS_FAIL.equalsIgnoreCase(status) ? FAIL_DIR : PASS_DIR;
                Path absoluteTarget = targetDir.resolve(safeName);

                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Files.write(absoluteTarget, bytes);

                String folder = STATUS_FAIL.equalsIgnoreCase(status) ? "FAIL" : "PASS";
                return "Screenshots/" + folder + "/" + safeName;
            } catch (Exception e) {
                return "";
            }
        }
    }

    public static void addResult(String testCaseId,
                                 String testName,
                                 String description,
                                 String expectedResult,
                                 String actualResult,
                                 String status,
                                 String screenshotRelativePath,
                                 LocalDateTime executionTime) {

        synchronized (LOCK) {
            initializeReport();

            Row row = sheet.createRow(rowIndex++);
            createCell(row, 0, safe(testCaseId), baseCellStyle);
            createCell(row, 1, safe(testName), baseCellStyle);
            createCell(row, 2, safe(description), baseCellStyle);
            createCell(row, 3, safe(expectedResult), baseCellStyle);
            createCell(row, 4, safe(actualResult), baseCellStyle);

            String normalizedStatus = safe(status).toUpperCase();
            CellStyle statusStyle = STATUS_PASS.equals(normalizedStatus) ? passCellStyle
                    : STATUS_FAIL.equals(normalizedStatus) ? failCellStyle : baseCellStyle;
            createCell(row, 5, normalizedStatus, statusStyle);

            Cell screenshotCell = row.createCell(6);
            screenshotCell.setCellStyle(linkCellStyle);
            if (!safe(screenshotRelativePath).isEmpty()) {
                screenshotCell.setCellValue(screenshotRelativePath);
                Hyperlink link = workbook.getCreationHelper().createHyperlink(HyperlinkType.FILE);
                link.setAddress(screenshotRelativePath);
                screenshotCell.setHyperlink(link);
            } else {
                screenshotCell.setCellValue("N/A");
            }

            createCell(row, 7, executionTime.format(DISPLAY_TS), baseCellStyle);
        }
    }

    public static void flushAndClose() {
        synchronized (LOCK) {
            if (!INITIALIZED.get()) {
                return;
            }
            try {
                for (int i = 0; i < HEADERS.length; i++) {
                    sheet.autoSizeColumn(i);
                }
                try (OutputStream os = Files.newOutputStream(reportFilePath)) {
                    workbook.write(os);
                }
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to write QA evidence report", e);
            } finally {
                INITIALIZED.set(false);
                workbook = null;
                sheet = null;
                reportFilePath = null;
                rowIndex = 1;
            }
        }
    }

    private static void createHeader() {
        Row header = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            createCell(header, i, HEADERS[i], headerStyle);
        }
    }

    private static void createStyles() {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        Font linkFont = workbook.createFont();
        linkFont.setColor(IndexedColors.BLUE.getIndex());
        linkFont.setUnderline(Font.U_SINGLE);

        headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        applyBorders(headerStyle);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        baseCellStyle = workbook.createCellStyle();
        applyBorders(baseCellStyle);
        baseCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        passCellStyle = workbook.createCellStyle();
        passCellStyle.cloneStyleFrom(baseCellStyle);
        passCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        passCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        failCellStyle = workbook.createCellStyle();
        failCellStyle.cloneStyleFrom(baseCellStyle);
        failCellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        failCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        linkCellStyle = workbook.createCellStyle();
        linkCellStyle.cloneStyleFrom(baseCellStyle);
        linkCellStyle.setFont(linkFont);
    }

    private static void applyBorders(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    private static void createCell(Row row, int cellIndex, String value, CellStyle style) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static String sanitize(String value) {
        return safe(value).replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
}
