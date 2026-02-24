////package pages;
////
////import org.openqa.selenium.*;
////import utils.WaitUtils;
////
////public class LoginPage {
////
////    WebDriver driver;
////
////    public LoginPage(WebDriver driver) {
////        this.driver = driver;
////    }
////
////    By email = By.name("email");
////    By password = By.name("password");
////    By loginBtn = By.xpath("//button[text()='Login']");
////    By mobileTab = By.xpath("//button[contains(text(),'Login with Mobile')]");
////    By mobileInput = By.name("mobile");
////    By sendOtp = By.xpath("//button[text()='Send OTP']");
////    By toastError = By.xpath("//div[@data-title]");
////    By error = By.xpath("//p[@data-slot='form-message']");
////
////    public void enterEmail(String value) {
////        WaitUtils.waitForVisible(driver, email).sendKeys(value);
////    }
////
////    public void enterPassword(String value) {
////        WaitUtils.waitForVisible(driver, password).sendKeys(value);
////    }
////
////    public void clickLogin() {
////        WaitUtils.waitForClickable(driver, loginBtn).click();
////    }
////
////    public String getInlineError() {
////        return WaitUtils.waitForVisible(driver, error).getText();
////    }
////
////    public void switchToMobile() {
////        WaitUtils.waitForClickable(driver, mobileTab).click();
////    }
////
////    public void enterMobile(String value) {
////        WaitUtils.waitForVisible(driver, mobileInput).sendKeys(value);
////    }
////
////    public void clickSendOtp() {
////        WaitUtils.waitForClickable(driver, sendOtp).click();
////    }
////
////    public String getToastError() {
////        return WaitUtils.waitForVisible(driver, toastError).getText();
////    }
////}
//
//
//package pages;
//
//import org.openqa.selenium.*;
//import utils.WaitUtils;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class LoginPage {
//
//    WebDriver driver;
//
//    public LoginPage(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    // EMAIL TAB
//    By emailTab = By.xpath("//button[contains(text(),'Login with Email')]");
//    By emailField = By.name("email");
//    By passwordField = By.name("password");
//    By loginBtn = By.xpath("//button[text()='Login']");
//
//    // MOBILE TAB
//    By mobileTab = By.xpath("//button[contains(text(),'Login with Mobile')]");
//    By mobileField = By.name("contactNumber");
//    By sendOtpBtn = By.xpath("//button[text()='Send OTP']");
//
//    // INLINE ERRORS
//    By inlineErrors = By.xpath("//p[@data-slot='form-message']");
//
//    // TOAST ERROR
//    By toastError = By.xpath("//div[@data-title]");
//
//    // -----------------------------
//    // ACTIONS
//    // -----------------------------
//
//    public void switchToEmailLogin() {
//        driver.findElement(emailTab).click();
//    }
//
//    public void switchToMobileLogin() {
//        driver.findElement(mobileTab).click();
//    }
//
//    public void enterEmail(String email) {
//        driver.findElement(emailField).clear();
//        driver.findElement(emailField).sendKeys(email);
//    }
//
//    public void enterPassword(String password) {
//        driver.findElement(passwordField).clear();
//        driver.findElement(passwordField).sendKeys(password);
//    }
//
//    public void enterMobile(String mobile) {
//        driver.findElement(mobileField).clear();
//        driver.findElement(mobileField).sendKeys(mobile);
//    }
//
//    public void clickLogin() {
//        driver.findElement(loginBtn).click();
//    }
//
//    public void clickSendOtp() {
//        driver.findElement(sendOtpBtn).click();
//    }
//
//    // -----------------------------
//    // ERROR CAPTURE
//    // -----------------------------
//
//    // (1) Inline <p data-slot="form-message">
//    public List<String> getInlineErrors() {
//        WaitUtils.waitForSeconds(1);
//
//        List<WebElement> errors = driver.findElements(inlineErrors);
//
//        return errors.stream()
//                .map(WebElement::getText)
//                .filter(text -> !text.isEmpty())
//                .collect(Collectors.toList());
//    }
//
//    // (2) Browser native validation message
//    public String getBrowserValidationMessage(By locator) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        WebElement element = driver.findElement(locator);
//        return (String) js.executeScript(
//                "return arguments[0].validationMessage;", element);
//    }
//
//    // (3) Toast error <div data-title>
//    public String getToastError() {
//        WaitUtils.waitForSeconds(1);
//        try {
//            return driver.findElement(toastError).getText();
//        } catch (Exception e) {
//            return "";
//        }
//    }
//}

package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // ===============================
    // LOCATORS
    // ===============================

    // EMAIL TAB
    By emailTab = By.xpath("//button[contains(text(),'Login with Email')]");
    By emailField = By.name("email");
    By passwordField = By.name("password");
    By loginBtn = By.xpath("//button[text()='Login']");

    // MOBILE TAB
    By mobileTab = By.xpath("//button[contains(text(),'Login with Mobile')]");
    By mobileField = By.name("mobile");     // [FIXED] (was contactNumber)
    By sendOtpBtn = By.xpath("//button[text()='Send OTP']");

    // INLINE ERRORS
    By inlineErrors = By.xpath("//p[@data-slot='form-message']");

    // TOAST ERROR
    By toastError = By.xpath("//div[@data-title]");

    // ===============================
    // ACTIONS
    // ===============================

    public void switchToEmailLogin() {
        WaitUtils.waitForClickable(driver, emailTab).click();
        WaitUtils.waitForSeconds(1); // allow DOM render
    }

    public void switchToMobileLogin() {
        WaitUtils.waitForClickable(driver, mobileTab).click();
        WaitUtils.waitForSeconds(1); // allow DOM render
    }

    public void enterEmail(String email) {
        WebElement element = WaitUtils.waitForVisible(driver, emailField);
        element.clear();
        element.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement element = WaitUtils.waitForVisible(driver, passwordField);
        element.clear();
        element.sendKeys(password);
    }

    public void enterMobile(String mobile) {
        WebElement element = WaitUtils.waitForVisible(driver, mobileField);
        element.clear();
        element.sendKeys(mobile);
    }

    public void clickLogin() {
        WaitUtils.waitForClickable(driver, loginBtn).click();
    }

    public void clickSendOtp() {
        WaitUtils.waitForClickable(driver, sendOtpBtn).click();
    }

    // ===============================
    // ERROR CAPTURE
    // ===============================

    // (1) Inline Errors
    public List<String> getInlineErrors() {
        WaitUtils.waitForSeconds(1);

        List<WebElement> errors = driver.findElements(inlineErrors);

        return errors.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    // (2) Browser Native Validation Message
    public String getBrowserValidationMessage(By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (String) js.executeScript(
                    "return arguments[0].validationMessage;", element);
        } catch (Exception e) {
            return "";
        }
    }

    // (3) Toast Error
    public String getToastError() {
        WaitUtils.waitForSeconds(1);
        try {
            return WaitUtils.waitForVisible(driver, toastError).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // ===============================
    // GETTERS (Used in Validation Tests)
    // ===============================

    public By getEmailLocator() {
        return emailField;
    }

    public By getPasswordLocator() {
        return passwordField;
    }

    public By getMobileLocator() {
        return mobileField;
    }
}

