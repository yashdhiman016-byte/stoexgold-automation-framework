package pages;

import org.openqa.selenium.WebDriver;

/**
 * Minimal page object used to confirm post-login navigation.
 */
public class DashboardPage {

    private final WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isDashboardLoaded() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("dashboard") || currentUrl.contains("sponsor");
    }
}
