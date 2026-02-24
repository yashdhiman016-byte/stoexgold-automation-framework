package listeners;

import base.BaseTest;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import reporting.ExcelEvidenceReporter;
import reporting.QaEvidence;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class QAEvidenceTestListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        ExcelEvidenceReporter.initializeReport();
    }

    @Override
    public void onFinish(ISuite suite) {
        ExcelEvidenceReporter.flushAndClose();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        writeResult(result, "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        writeResult(result, "FAIL");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        writeResult(result, "FAIL");
    }

    private void writeResult(ITestResult result, String status) {
        String testName = result.getMethod().getMethodName();
        Method javaMethod = result.getMethod().getConstructorOrMethod().getMethod();
        QaEvidence evidence = javaMethod.getAnnotation(QaEvidence.class);

        String testCaseId = resolveTestCaseId(testName, evidence);
        String description = resolveDescription(javaMethod, evidence, testName);
        String expectedResult = resolveExpectedResult(evidence, description);
        String actualResult = resolveActualResult(result, status);

        String screenshotPath = "";
        Object instance = result.getInstance();
        if (instance instanceof BaseTest baseTest) {
            screenshotPath = ExcelEvidenceReporter.captureScreenshot(baseTest.getDriver(), status, testName);
        }

        ExcelEvidenceReporter.addResult(
                testCaseId,
                testName,
                description,
                expectedResult,
                actualResult,
                status,
                screenshotPath,
                LocalDateTime.now()
        );
    }

    private String resolveTestCaseId(String methodName, QaEvidence evidence) {
        if (evidence != null && !evidence.testCaseId().isBlank()) {
            return evidence.testCaseId();
        }

        String[] tokens = methodName.split("_");
        if (tokens.length >= 2 && tokens[1].matches("\\d+")) {
            return tokens[0] + "_" + tokens[1];
        }
        return methodName;
    }

    private String resolveDescription(Method method, QaEvidence evidence, String methodName) {
        if (evidence != null && !evidence.description().isBlank()) {
            return evidence.description();
        }

        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null && !testAnnotation.description().isBlank()) {
            return testAnnotation.description();
        }

        return methodName.replace('_', ' ');
    }

    private String resolveExpectedResult(QaEvidence evidence, String description) {
        if (evidence != null && !evidence.expectedResult().isBlank()) {
            return evidence.expectedResult();
        }
        return "Application should behave as per scenario: " + description;
    }

    private String resolveActualResult(ITestResult result, String status) {
        if ("PASS".equalsIgnoreCase(status)) {
            return "Test executed successfully.";
        }
        if (result.getThrowable() != null && result.getThrowable().getMessage() != null) {
            return result.getThrowable().getMessage();
        }
        return "Test failed during execution.";
    }
}
