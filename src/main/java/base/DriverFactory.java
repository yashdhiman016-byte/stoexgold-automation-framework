package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Centralized browser driver creation for tests.
 */
public class DriverFactory {

    private static final AtomicBoolean CHROME_SETUP_DONE = new AtomicBoolean(false);

    public static WebDriver initDriver() {
        if (CHROME_SETUP_DONE.compareAndSet(false, true)) {
            try {
                WebDriverManager.chromedriver().setup();
            } catch (Exception e) {
                // Continue and rely on already-available driver in PATH/cache.
                System.out.println("WebDriverManager setup skipped: " + e.getMessage());
            }
        }

        ChromeOptions options = new ChromeOptions();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;
    }
}
