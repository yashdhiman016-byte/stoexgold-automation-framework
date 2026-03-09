package base;

import io.qameta.allure.Allure;
import listeners.QAEvidenceTestListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.ByteArrayInputStream;

/**
 * Base class for all UI tests.
 * Initializes the WebDriver for each test method and closes it in teardown.
 */
@Listeners(QAEvidenceTestListener.class)
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        try {
            driver = DriverFactory.initDriver();
        } catch (Exception firstAttemptError) {
            // Retry once for transient browser startup failures.
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver = DriverFactory.initDriver();
        }
        driver.get("https://dev-stoex-website.p2eppl.com/auth/signin");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot("FAILED - " + testName);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            attachScreenshot("PASSED - " + testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            attachScreenshot("SKIPPED - " + testName);
        }

        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Adds the current browser screenshot to the Allure report.
     */
    public void attachScreenshot(String name) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
