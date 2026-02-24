//////
//////package base;
//////
//////import io.qameta.allure.Attachment;
//////import org.openqa.selenium.*;
//////import org.testng.ITestResult;
//////import org.testng.annotations.*;
//////
//////public class BaseTest {
//////
//////    protected WebDriver driver;
//////
//////    @BeforeMethod
//////    public void setup() {
//////        driver = DriverFactory.initDriver();
//////        driver.get("https://dev-stoex-website.p2eppl.com/auth/signin");
//////    }
//////
//////    @AfterMethod
//////    public void tearDown(ITestResult result) {
//////        if (ITestResult.FAILURE == result.getStatus()) {
//////            attachScreenshot();
//////        }
//////        driver.quit();
//////    }
//////
//////    @Attachment(value = "Failure Screenshot", type = "image/png")
//////    public byte[] attachScreenshot() {
//////        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//////    }
//////}
////package base;
////
////import io.qameta.allure.Attachment;
////import org.openqa.selenium.OutputType;
////import org.openqa.selenium.TakesScreenshot;
////import org.openqa.selenium.WebDriver;
////import org.testng.ITestResult;
////import org.testng.annotations.*;
////
////public class BaseTest {
////
////    protected WebDriver driver;
////
////    @BeforeMethod
////    public void setup() {
////        driver = DriverFactory.initDriver();
////        driver.get("https://dev-stoex-website.p2eppl.com/auth/signin");
////    }
////
////    @AfterMethod
////    public void tearDown(ITestResult result) {
////
////        if (ITestResult.FAILURE == result.getStatus()) {
////            attachScreenshot();
////        }
////        driver.quit();
////    }
////
////    @Attachment(value = "Failure Screenshot", type = "image/png")
////    public byte[] attachScreenshot() {
////        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
////    }
////}
//package base;
//
//import io.qameta.allure.Attachment;
//import org.openqa.selenium.*;
//import org.testng.ITestResult;
//import org.testng.annotations.*;
//
//public class BaseTest {
//
//    protected WebDriver driver;
//
//    @BeforeMethod
//    public void setup() {
//        driver = DriverFactory.initDriver();
//        driver.get("https://dev-stoex-website.p2eppl.com/auth/signin");
//    }
//
//    @AfterMethod
//    public void tearDown(ITestResult result) {
//
//        if (ITestResult.FAILURE == result.getStatus()) {
//            captureScreenshot();
//        }
//
//        driver.quit();
//    }
//
//    @Attachment(value = "Failure Screenshot", type = "image/png")
//    public byte[] captureScreenshot() {
//        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    }
//}


package base;

import io.qameta.allure.Allure;
import listeners.QAEvidenceTestListener;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;

@Listeners(QAEvidenceTestListener.class)
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.initDriver();
        driver.get("https://dev-stoex-website.p2eppl.com/auth/signin");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot("FAILED - " + testName);
        }
        else if (result.getStatus() == ITestResult.SUCCESS) {
            attachScreenshot("PASSED - " + testName);
        }
        else if (result.getStatus() == ITestResult.SKIP) {
            attachScreenshot("SKIPPED - " + testName);
        }

        if (driver != null) {
            driver.quit();
        }
    }

    // ✅ Screenshot attachment for Allure
    public void attachScreenshot(String name) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(name,
                    new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
