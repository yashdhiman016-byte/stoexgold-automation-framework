package utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;

/**
 * Helper for attaching browser screenshots to Allure reports.
 */
public class ScreenshotUtils {

    /**
     * Captures screenshot bytes from current browser state.
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] capture(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
