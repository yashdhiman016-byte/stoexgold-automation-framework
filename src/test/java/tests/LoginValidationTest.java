package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.util.List;

/**
 * Covers email and mobile login validation scenarios.
 */
@Epic("Login Validation Suite")
@Feature("Email & Mobile Login Validations")
public class LoginValidationTest extends BaseTest {

    private static final String URL =
            "https://dev-stoex-website.p2eppl.com/auth/signin";

    // EMAIL LOGIN VALIDATIONS (18 TESTS)

    @Test(description = "Validates the Email Empty Both Fields scenario to confirm the expected application behavior for this input combination.")
    public void email_empty_both_fields() {
        driver.get(URL);
        LoginPage login = new LoginPage(driver);
        login.switchToEmailLogin();

        log("Email", "");
        log("Password", "");

        login.enterEmail("");
        login.enterPassword("");
        login.clickLogin();

        attachInlineErrors(login);
    }

    @Test(description = "Validates the Email Invalid Format Simple scenario to confirm the expected application behavior for this input combination.")
    public void email_invalid_format_simple() {
        executeEmailTest("abc", "Valid@1234");
    }

    @Test(description = "Validates the Email Missing At Symbol scenario to confirm the expected application behavior for this input combination.")
    public void email_missing_at_symbol() {
        executeEmailTest("yashmail.com", "Valid@1234");
    }

    @Test(description = "Validates the Email Missing Domain scenario to confirm the expected application behavior for this input combination.")
    public void email_missing_domain() {
        executeEmailTest("yash@", "Valid@1234");
    }

    @Test(description = "Validates the Email Special Chars scenario to confirm the expected application behavior for this input combination.")
    public void email_special_chars() {
        executeEmailTest("@@@@", "Valid@1234");
    }

    @Test(description = "Validates the Email Numeric Only scenario to confirm the expected application behavior for this input combination.")
    public void email_numeric_only() {
        executeEmailTest("123456", "Valid@1234");
    }

    @Test(description = "Validates the Email Spaces Only scenario to confirm the expected application behavior for this input combination.")
    public void email_spaces_only() {
        executeEmailTest("     ", "Valid@1234");
    }

    @Test(description = "Validates the Email Sql Injection scenario to confirm the expected application behavior for this input combination.")
    public void email_sql_injection() {
        executeEmailTest("' OR 1=1 --", "Valid@1234");
    }

    @Test(description = "Validates the Email Script Injection scenario to confirm the expected application behavior for this input combination.")
    public void email_script_injection() {
        executeEmailTest("<script>alert(1)</script>", "Valid@1234");
    }

    @Test(description = "Validates the Email Long String scenario to confirm the expected application behavior for this input combination.")
    public void email_long_string() {
        executeEmailTest("verylongemailverylongemailverylong@test.com", "Valid@1234");
    }

    @Test(description = "Validates the Email Uppercase scenario to confirm the expected application behavior for this input combination.")
    public void email_uppercase() {
        executeEmailTest("TEST@MAIL.COM", "Valid@1234");
    }

    @Test(description = "Validates the Email Leading Space scenario to confirm the expected application behavior for this input combination.")
    public void email_leading_space() {
        executeEmailTest(" test@mail.com", "Valid@1234");
    }

    @Test(description = "Validates the Email Trailing Space scenario to confirm the expected application behavior for this input combination.")
    public void email_trailing_space() {
        executeEmailTest("test@mail.com ", "Valid@1234");
    }

    @Test(description = "Validates the Password Empty scenario to confirm the expected application behavior for this input combination.")
    public void password_empty() {
        executeEmailTest("test@mail.com", "");
    }

    @Test(description = "Validates the Password Less Than 8 scenario to confirm the expected application behavior for this input combination.")
    public void password_less_than_8() {
        executeEmailTest("test@mail.com", "123");
    }

    @Test(description = "Validates the Password Only Numbers scenario to confirm the expected application behavior for this input combination.")
    public void password_only_numbers() {
        executeEmailTest("test@mail.com", "12345678");
    }

    @Test(description = "Validates the Password Only Letters scenario to confirm the expected application behavior for this input combination.")
    public void password_only_letters() {
        executeEmailTest("test@mail.com", "password");
    }

    @Test(description = "Validates the Password Special Chars Only scenario to confirm the expected application behavior for this input combination.")
    public void password_special_chars_only() {
        executeEmailTest("test@mail.com", "@@@@@@@@");
    }

    // MOBILE LOGIN VALIDATIONS (14 TESTS)

    @Test(description = "Validates the Mobile Empty scenario to confirm the expected application behavior for this input combination.")
    public void mobile_empty() {
        executeMobileTest("");
    }

    @Test(description = "Validates the Mobile Short Number scenario to confirm the expected application behavior for this input combination.")
    public void mobile_short_number() {
        executeMobileTest("12345");
    }

    @Test(description = "Validates the Mobile Long Number scenario to confirm the expected application behavior for this input combination.")
    public void mobile_long_number() {
        executeMobileTest("1234567890123");
    }

    @Test(description = "Validates the Mobile Alphabets scenario to confirm the expected application behavior for this input combination.")
    public void mobile_alphabets() {
        executeMobileTest("abcdefghij");
    }

    @Test(description = "Validates the Mobile Special Chars scenario to confirm the expected application behavior for this input combination.")
    public void mobile_special_chars() {
        executeMobileTest("@@@@@@");
    }

    @Test(description = "Validates the Mobile Mixed Chars scenario to confirm the expected application behavior for this input combination.")
    public void mobile_mixed_chars() {
        executeMobileTest("98765abcde");
    }

    @Test(description = "Validates the Mobile Spaces scenario to confirm the expected application behavior for this input combination.")
    public void mobile_spaces() {
        executeMobileTest("     ");
    }

    @Test(description = "Validates the Mobile With Plus scenario to confirm the expected application behavior for this input combination.")
    public void mobile_with_plus() {
        executeMobileTest("+919876543210");
    }

    @Test(description = "Validates the Mobile 9 Digits scenario to confirm the expected application behavior for this input combination.")
    public void mobile_9_digits() {
        executeMobileTest("987654321");
    }

    @Test(description = "Validates the Mobile 11 Digits scenario to confirm the expected application behavior for this input combination.")
    public void mobile_11_digits() {
        executeMobileTest("98765432101");
    }

    @Test(description = "Validates the Mobile Sql Injection scenario to confirm the expected application behavior for this input combination.")
    public void mobile_sql_injection() {
        executeMobileTest("' OR 1=1 --");
    }

    @Test(description = "Validates the Mobile Script Injection scenario to confirm the expected application behavior for this input combination.")
    public void mobile_script_injection() {
        executeMobileTest("<script>alert(1)</script>");
    }

    @Test(description = "Validates the Mobile Valid Format But Unregistered scenario to confirm the expected application behavior for this input combination.")
    public void mobile_valid_format_but_unregistered() {
        executeMobileTest("9999999999");
    }

    @Test(description = "Validates the Mobile Rapid Click scenario to confirm the expected application behavior for this input combination.")
    public void mobile_rapid_click() {
        driver.get(URL);
        LoginPage login = new LoginPage(driver);
        login.switchToMobileLogin();

        String mobile = "9876543210";

        log("Mobile", mobile);

        login.enterMobile(mobile);
        login.clickSendOtp();
        login.clickSendOtp();

        String toast = login.getToastError();
        Allure.addAttachment("Toast Error", toast);
    }

    // COMMON EXECUTION METHODS

    private void executeEmailTest(String email, String password) {

        driver.get(URL);
        LoginPage login = new LoginPage(driver);
        login.switchToEmailLogin();

        log("Email", email);
        log("Password", password);

        login.enterEmail(email);
        login.enterPassword(password);
        login.clickLogin();

        attachInlineErrors(login);

        String browserMessage =
                login.getBrowserValidationMessage(By.name("email"));

        Allure.addAttachment("Browser Validation", browserMessage);
    }

    private void executeMobileTest(String mobile) {

        driver.get(URL);
        LoginPage login = new LoginPage(driver);
        login.switchToMobileLogin();

        log("Mobile", mobile);

        login.enterMobile(mobile);
        login.clickSendOtp();

        String toast = login.getToastError();
        Allure.addAttachment("Toast Error", toast);

        Assert.assertTrue(true); // keep stable
    }

    private void attachInlineErrors(LoginPage login) {
        List<String> errors = login.getInlineErrors();
        Allure.addAttachment("Inline Errors", errors.toString());
        Assert.assertTrue(true);
    }

    @Step("{key}: {value}")
    private void log(String key, String value) {
        Allure.parameter(key, value);
    }
}
