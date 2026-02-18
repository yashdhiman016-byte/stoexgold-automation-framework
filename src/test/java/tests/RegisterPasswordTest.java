//package tests;
//
//import base.BaseTest;
//import io.qameta.allure.Allure;
//import io.qameta.allure.Step;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import pages.RegisterPage;
//
//import java.util.List;
//
//public class RegisterPasswordTest extends BaseTest {
//
//    private static final String URL = "https://dev-stoex-website.p2eppl.com/auth/register";
//
//    private void executePasswordTest(String pass, String confirm) {
//        driver.get(URL);
//        RegisterPage register = new RegisterPage(driver);
//
//        log("Password", pass);
//        log("Confirm", confirm);
//
//        register.enterDetails("Yash", "Dhiman",
//                "9876543210", "test@mail.com",
//                pass, confirm);
//
//        register.clickSubmit();
//
//        List<String> errors = register.getAllErrors();
//        Allure.addAttachment("Password Errors", errors.toString());
//
//        Assert.assertTrue(errors.size() > 0);
//    }
//
//    @Test public void PW_01_Empty() { executePasswordTest("", ""); }
//    @Test public void PW_02_Short() { executePasswordTest("123", "123"); }
//    @Test public void PW_03_NoUppercase() { executePasswordTest("password1@", "password1@"); }
//    @Test public void PW_04_NoLowercase() { executePasswordTest("PASSWORD1@", "PASSWORD1@"); }
//    @Test public void PW_05_NoNumber() { executePasswordTest("Password@", "Password@"); }
//    @Test public void PW_06_NoSpecialChar() { executePasswordTest("Password1", "Password1"); }
//    @Test public void PW_07_Mismatch() { executePasswordTest("Valid@1234", "Invalid@1234"); }
//    @Test public void PW_08_OnlyNumbers() { executePasswordTest("12345678", "12345678"); }
//    @Test public void PW_09_OnlyLetters() { executePasswordTest("abcdefgh", "abcdefgh"); }
//    @Test public void PW_10_Valid() { Assert.assertTrue(true); }
//
//    @Step("{key}: {value}")
//    public void log(String key, String value) {
//        Allure.parameter(key, value);
//    }
//}



package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.util.List;

@Epic("Authentication")
@Feature("Register - Password Validation")
public class RegisterPasswordTest extends BaseTest {

    private static final String URL =
            "https://dev-stoex-website.p2eppl.com/auth/register";

    // ============================================
    // NEGATIVE EXECUTION METHOD
    // ============================================

    private void executeNegative(String password, String confirmPassword) {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        // -------- INPUT --------
        Allure.step("Entering Password and Confirm Password");
        Allure.addAttachment("Password Input", password);
        Allure.addAttachment("Confirm Password Input", confirmPassword);

        register.enterDetails(
                "Yash",
                "Dhiman",
                "9876543210",
                "test@mail.com",
                password,
                confirmPassword
        );

        register.clickSubmit();

        // -------- INLINE ERRORS --------
        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors",
                inlineErrors.isEmpty() ? "No Inline Errors" : inlineErrors.toString());

        // -------- TOAST ERROR --------
        String toastError = register.getMobileToastError();
        Allure.addAttachment("Toast Error",
                toastError.isEmpty() ? "No Toast Error" : toastError);

        // -------- BROWSER VALIDATION --------
        String browserValidation = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.querySelector('[name=\"password\"]').validationMessage;");
        Allure.addAttachment("Browser Validation Message",
                browserValidation.isEmpty() ? "No Browser Validation" : browserValidation);

        // -------- ASSERT --------
        Assert.assertTrue(
                inlineErrors.size() > 0 || !toastError.isEmpty() || !browserValidation.isEmpty(),
                "Validation message should appear for invalid password"
        );
    }

    // ============================================
    // NEGATIVE TEST CASES
    // ============================================

    @Test(description = "Empty Password")
    public void PW_01_Empty() {
        executeNegative("", "");
    }

    @Test(description = "Too Short")
    public void PW_02_Short() {
        executeNegative("123", "123");
    }

    @Test(description = "No Uppercase")
    public void PW_03_NoUppercase() {
        executeNegative("password1@", "password1@");
    }

    @Test(description = "No Lowercase")
    public void PW_04_NoLowercase() {
        executeNegative("PASSWORD1@", "PASSWORD1@");
    }

    @Test(description = "No Number")
    public void PW_05_NoNumber() {
        executeNegative("Password@", "Password@");
    }

    @Test(description = "No Special Character")
    public void PW_06_NoSpecialChar() {
        executeNegative("Password1", "Password1");
    }

    @Test(description = "Mismatch Password")
    public void PW_07_Mismatch() {
        executeNegative("Valid@1234", "Invalid@1234");
    }

    @Test(description = "Only Numbers")
    public void PW_08_OnlyNumbers() {
        executeNegative("12345678", "12345678");
    }

    @Test(description = "Only Letters")
    public void PW_09_OnlyLetters() {
        executeNegative("abcdefgh", "abcdefgh");
    }

    // ============================================
    // POSITIVE TEST CASE
    // ============================================

    @Test(description = "Valid Password")
    @Severity(SeverityLevel.CRITICAL)
    public void PW_10_Valid() {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        String password = "Valid@1234";

        Allure.addAttachment("Password Input", password);
        Allure.addAttachment("Confirm Password Input", password);

        register.enterDetails(
                "Yash",
                "Dhiman",
                "9876543210",
                "test@mail.com",
                password,
                password
        );

        register.clickSubmit();

        List<String> errors = register.getAllErrors();
        Allure.addAttachment("Inline Errors", errors.toString());

        Assert.assertTrue(errors.isEmpty(),
                "No validation errors should appear for valid password");
    }
}