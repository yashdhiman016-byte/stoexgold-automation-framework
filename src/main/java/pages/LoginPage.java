package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page object for login flows using email/password and mobile OTP.
 */
public class LoginPage {

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Email login locators.
    private final By emailTab = By.xpath("//button[contains(text(),'Login with Email')]");
    private final By emailField = By.name("email");
    private final By passwordField = By.name("password");
    private final By loginBtn = By.xpath("//button[text()='Login']");

    // Mobile login locators.
    private final By mobileTab = By.xpath("//button[contains(text(),'Login with Mobile')]");
    private final By mobileField = By.name("mobile");
    private final By sendOtpBtn = By.xpath("//button[text()='Send OTP']");

    private final By inlineErrors = By.xpath("//p[@data-slot='form-message']");
    private final By toastError = By.xpath("//div[@data-title]");

    public void switchToEmailLogin() {
        WaitUtils.waitForClickable(driver, emailTab).click();
        WaitUtils.waitForSeconds(1);
    }

    public void switchToMobileLogin() {
        WaitUtils.waitForClickable(driver, mobileTab).click();
        WaitUtils.waitForSeconds(1);
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

    /**
     * Returns only non-empty inline validation messages.
     */
    public List<String> getInlineErrors() {
        WaitUtils.waitForSeconds(1);

        return driver.findElements(inlineErrors).stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Reads browser-native HTML5 validation text (for required/pattern/type errors).
     */
    public String getBrowserValidationMessage(By locator) {
        try {
            WebElement element = WaitUtils.waitForVisible(driver, locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (String) js.executeScript("return arguments[0].validationMessage;", element);
        } catch (Exception e) {
            return "";
        }
    }

    public String getToastError() {
        WaitUtils.waitForSeconds(1);
        try {
            return WaitUtils.waitForVisible(driver, toastError).getText();
        } catch (Exception e) {
            return "";
        }
    }

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
