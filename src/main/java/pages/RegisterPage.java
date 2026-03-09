package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page object for registration form interactions and validation capture.
 */
public class RegisterPage {

    private final WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By firstName = By.name("firstName");
    private final By lastName = By.name("lastName");
    private final By mobile = By.name("contactNumber");
    private final By email = By.name("email");
    private final By password = By.name("password");
    private final By confirmPassword = By.name("confirmPassword");

    private final By checkbox = By.cssSelector("input[type='checkbox']");
    private final By submit = By.xpath("//button[text()='Submit']");

    private final By mobileSendOtp = By.xpath("(//button[text()='Send OTP'])[1]");
    private final By emailSendOtp = By.xpath("(//button[text()='Send OTP'])[2]");

    private final By errorMessages = By.xpath("//p[@data-slot='form-message']");
    private final By mobileToastError = By.cssSelector("div[data-title]");

    /**
     * Fills all registration fields with provided values.
     */
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

    public List<String> getAllErrors() {
        return driver.findElements(errorMessages).stream()
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
