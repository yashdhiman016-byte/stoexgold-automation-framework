//
//package pages;
//
//import org.openqa.selenium.*;
//
//public class DashboardPage {
//
//    WebDriver driver;
//
//    public DashboardPage(WebDriver driver){
//        this.driver = driver;
//    }
//
//    public boolean isDashboardLoaded(){
//        return driver.getCurrentUrl().contains("dashboard");
//    }
//}


package pages;

import org.openqa.selenium.WebDriver;

public class DashboardPage {

    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isDashboardLoaded() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("dashboard")
                || currentUrl.contains("sponsor");
    }
}
