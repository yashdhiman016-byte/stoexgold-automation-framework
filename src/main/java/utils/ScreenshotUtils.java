package utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;

public class ScreenshotUtils {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] capture(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
