package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterPage {

    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= LOCATORS =================

    By firstName = By.name("firstName");
    By lastName = By.name("lastName");
    By mobile = By.name("contactNumber");
    By email = By.name("email");
    By password = By.name("password");
    By confirmPassword = By.name("confirmPassword");

    By checkbox = By.cssSelector("input[type='checkbox']");
    By submit = By.xpath("//button[text()='Submit']");

    // OTP Buttons
    By mobileSendOtp = By.xpath("(//button[text()='Send OTP'])[1]");
    By emailSendOtp = By.xpath("(//button[text()='Send OTP'])[2]");

    // Validation Errors
    By errorMessages = By.xpath("//p[@data-slot='form-message']");
//    By mobileToastError = By.xpath("//div[@data-title]");
    By mobileToastError = By.cssSelector("div[data-title]");

    // ================= ACTIONS =================

    public void enterDetails(String fn, String ln, String mob,
                             String em, String pass, String confirm) {

        WaitUtils.waitForVisible(driver, firstName).clear();
        driver.findElement(firstName).sendKeys(fn);

        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(ln);

        driver.findElement(mobile).clear();
        driver.findElement(mobile).sendKeys(mob);

        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(em);

        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);

        driver.findElement(confirmPassword).clear();
        driver.findElement(confirmPassword).sendKeys(confirm);
    }

    public void clickSubmit() {
        driver.findElement(submit).click();
    }

    public void acceptTerms() {
        driver.findElement(checkbox).click();
    }

    public void clickMobileSendOtp() {
        driver.findElement(mobileSendOtp).click();
    }

    public void clickEmailSendOtp() {
        driver.findElement(emailSendOtp).click();
    }

    // ================= ERROR CAPTURE =================

    public List<String> getAllErrors() {
        List<WebElement> errors = driver.findElements(errorMessages);

        return errors.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    public String getMobileToastError() {
        try {
            return driver.findElement(mobileToastError).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
