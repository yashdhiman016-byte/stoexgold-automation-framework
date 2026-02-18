////////package pages;
////////
////////import org.openqa.selenium.*;
////////import utils.WaitUtils;
////////
////////public class ForgotPasswordPage {
////////
////////    WebDriver driver;
////////
////////    public ForgotPasswordPage(WebDriver driver) {
////////        this.driver = driver;
////////    }
////////
////////    By email = By.name("email");
////////    By sendBtn = By.xpath("//button[text()='Send Reset Link']");
////////    By successMsg = By.xpath("//h3[contains(text(),'Email Sent Successfully')]");
////////
////////    public void enterEmail(String value) {
////////        WaitUtils.waitForVisible(driver, email).sendKeys(value);
////////    }
////////
////////    public void clickSend() {
////////        driver.findElement(sendBtn).click();
////////    }
////////
////////    public boolean isSuccessDisplayed() {
////////        return driver.findElement(successMsg).isDisplayed();
////////    }
////////}
//////
//////package pages;
//////
//////import org.openqa.selenium.*;
//////import utils.WaitUtils;
//////
//////public class ForgotPasswordPage {
//////
//////    WebDriver driver;
//////
//////    public ForgotPasswordPage(WebDriver driver) {
//////        this.driver = driver;
//////    }
//////
//////    By email = By.name("email");
//////    By sendBtn = By.xpath("//button[text()='Send Reset Link']");
//////    By successMsg = By.xpath("//h3[contains(text(),'Email Sent Successfully')]");
//////
//////    public void enterEmail(String value) {
//////        WaitUtils.waitForVisible(driver, email).clear();
//////        driver.findElement(email).sendKeys(value);
//////    }
//////
//////    public void clickSend() {
//////        WaitUtils.waitForClickable(driver, sendBtn).click();
//////    }
//////
//////    // ✅ SAFE METHOD (no exception)
//////    public boolean isSuccessDisplayed() {
//////        try {
//////            return driver.findElement(successMsg).isDisplayed();
//////        } catch (NoSuchElementException e) {
//////            return false;
//////        }
//////    }
//////}
//////
////
////package pages;
////
////import org.openqa.selenium.*;
////import org.openqa.selenium.support.ui.ExpectedConditions;
////import org.openqa.selenium.support.ui.WebDriverWait;
////
////import java.time.Duration;
////import java.util.List;
////import java.util.stream.Collectors;
////
////public class ForgotPasswordPage {
////
////    WebDriver driver;
////    WebDriverWait wait;
////
////    public ForgotPasswordPage(WebDriver driver) {
////        this.driver = driver;
////        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////    }
////
////    // ================= LOCATORS =================
////
////    By emailField = By.name("email");
////    By sendResetBtn = By.xpath("//button[text()='Send Reset Link']");
////    By inlineError = By.xpath("//p[@data-slot='form-message']");
////    By successMessage = By.xpath("//h3[contains(text(),'Email Sent Successfully')]");
////
////    // ================= ACTIONS =================
////
////    public void enterEmail(String email) {
////        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
////        element.clear();
////        element.sendKeys(email);
////    }
////
////    public void clickSendReset() {
////        wait.until(ExpectedConditions.elementToBeClickable(sendResetBtn)).click();
////    }
////
////    // ================= VALIDATION =================
////
////    public String getInlineError() {
////        try {
////            return wait.until(ExpectedConditions.visibilityOfElementLocated(inlineError)).getText();
////        } catch (Exception e) {
////            return "";
////        }
////    }
////
////    public String getSuccessMessage() {
////        try {
////            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
////        } catch (Exception e) {
////            return "";
////        }
////    }
////
////    public String getBrowserValidationMessage() {
////        JavascriptExecutor js = (JavascriptExecutor) driver;
////        WebElement element = driver.findElement(emailField);
////        return (String) js.executeScript(
////                "return arguments[0].validationMessage;", element);
////    }
////}
//
//package pages;
//
//import org.openqa.selenium.*;
//import utils.WaitUtils;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ForgotPasswordPage {
//
//    WebDriver driver;
//
//    public ForgotPasswordPage(WebDriver driver) {
//        this.driver = driver;
//    }
//
//    // LOCATORS
//    By emailField = By.name("email");
//    By sendResetBtn = By.xpath("//button[text()='Send Reset Link']");
//    By inlineErrors = By.xpath("//p[@data-slot='form-message']");
//    By successMessage = By.xpath("//h3[contains(text(),'Email Sent Successfully')]");
//
//    // ACTIONS
//
//    public void enterEmail(String email) {
//        WebElement element = WaitUtils.waitForVisible(driver, emailField);
//        element.clear();
//        element.sendKeys(email);
//    }
//
//    public void clickSendReset() {
//        WaitUtils.waitForClickable(driver, sendResetBtn).click();
//    }
//
//    // CAPTURE INLINE ERRORS
//    public List<String> getInlineErrors() {
//        WaitUtils.waitForSeconds(1);
//        List<WebElement> errors = driver.findElements(inlineErrors);
//
//        return errors.stream()
//                .map(WebElement::getText)
//                .filter(text -> !text.isEmpty())
//                .collect(Collectors.toList());
//    }
//
//    // CAPTURE BROWSER VALIDATION MESSAGE
//    public String getBrowserValidationMessage() {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        WebElement element = driver.findElement(emailField);
//        return (String) js.executeScript(
//                "return arguments[0].validationMessage;", element);
//    }
//
//    // CAPTURE SUCCESS MESSAGE
//    public String getSuccessMessage() {
//        try {
//            return WaitUtils.waitForVisible(driver, successMessage).getText();
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

public class ForgotPasswordPage {

    WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    By emailField = By.name("email");
    By sendResetBtn = By.xpath("//button[contains(text(),'Send Reset Link')]");
    By inlineErrors = By.xpath("//p[@data-slot='form-message']");
    By toastError = By.xpath("//div[@data-title]");
    By successMessage = By.xpath("//h3[contains(text(),'Email Sent Successfully')]");

    // =============================
    // ACTIONS
    // =============================

    public void enterEmail(String email) {
        WebElement element = WaitUtils.waitForVisible(driver, emailField);
        element.clear();
        element.sendKeys(email);
    }

    public void clickSendReset() {
        WaitUtils.waitForClickable(driver, sendResetBtn).click();
    }

    // =============================
    // ERROR CAPTURE
    // =============================

    // 1️⃣ Browser native validation
    public String getBrowserValidationMessage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(emailField);
        return (String) js.executeScript(
                "return arguments[0].validationMessage;", element);
    }

    // 2️⃣ Inline <p data-slot="form-message">
    public List<String> getInlineErrors() {

        WaitUtils.waitForSeconds(1);

        List<WebElement> errors = driver.findElements(inlineErrors);

        return errors.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    // 3️⃣ Toast error
    public String getToastError() {
        WaitUtils.waitForSeconds(1);
        try {
            return driver.findElement(toastError).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // 4️⃣ Success message
    public boolean isSuccessMessageDisplayed() {
        try {
            WaitUtils.waitForSeconds(2);
            return driver.findElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
