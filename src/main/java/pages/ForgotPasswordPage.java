package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page object for forgot password validations and result checks.
 */
public class ForgotPasswordPage {

    private final WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By emailField = By.name("email");
    private final By sendResetBtn = By.xpath("//button[contains(text(),'Send Reset Link')]");
    private final By inlineErrors = By.xpath("//p[@data-slot='form-message']");
    private final By toastError = By.xpath("//div[@data-title]");
    private final By successMessage = By.xpath("//h3[contains(text(),'Email Sent Successfully')]");

    public void enterEmail(String email) {
        WebElement element = WaitUtils.waitForVisible(driver, emailField);
        element.clear();
        element.sendKeys(email);
    }

    public void clickSendReset() {
        WaitUtils.waitForClickable(driver, sendResetBtn).click();
    }

    /**
     * Reads browser-native HTML5 validation text for the email field.
     */
    public String getBrowserValidationMessage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(emailField);
        return (String) js.executeScript("return arguments[0].validationMessage;", element);
    }

    public List<String> getInlineErrors() {
        WaitUtils.waitForSeconds(1);

        return driver.findElements(inlineErrors).stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    public String getToastError() {
        WaitUtils.waitForSeconds(1);
        try {
            return driver.findElement(toastError).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WaitUtils.waitForSeconds(2);
            return driver.findElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
