package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.util.List;

@Epic("Authentication")
@Feature("Register - Email OTP Validation")
public class RegisterEmailTest extends BaseTest {

    private static final String URL =
            "https://dev-stoex-website.p2eppl.com/auth/register";

    // COMMON EXECUTION METHOD

    private void executeEmailTest(String emailInput) {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        // ---------- INPUT DATA ----------
        Allure.step("Entering Email Data");
        Allure.addAttachment("Email Input", emailInput);

        register.enterDetails(
                "Yash",
                "Dhiman",
                "9876543210",
                emailInput,
                "Valid@1234",
                "Valid@1234"
        );

        Allure.step("Clicking Send OTP");
        register.clickEmailSendOtp();

        // ---------- INLINE ERRORS ----------
        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors",
                inlineErrors.isEmpty() ? "No Inline Error" : inlineErrors.toString());

        // ---------- TOAST ERROR ----------
        String toastError = register.getMobileToastError();
        Allure.addAttachment("Toast Error",
                toastError.isEmpty() ? "No Toast Error" : toastError);

        // ---------- BROWSER VALIDATION ----------
        String browserValidation = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.querySelector('[name=\"email\"]').validationMessage;");
        Allure.addAttachment("Browser Validation Message",
                browserValidation.isEmpty() ? "No Browser Validation" : browserValidation);

        // ---------- ASSERT ----------
        Assert.assertTrue(
                inlineErrors.size() > 0 ||
                        !toastError.isEmpty() ||
                        !browserValidation.isEmpty(),
                "Validation message should appear"
        );
    }

    // TEST CASES (10 SAMPLE)

    @Test(description = "Validates the Empty scenario to confirm the expected application behavior for this input combination.")
    public void EM_01_Empty() {
        executeEmailTest("");
    }

    @Test(description = "Validates the No At scenario to confirm the expected application behavior for this input combination.")
    public void EM_02_NoAt() {
        executeEmailTest("testmail.com");
    }

    @Test(description = "Validates the No Domain scenario to confirm the expected application behavior for this input combination.")
    public void EM_03_NoDomain() {
        executeEmailTest("test@");
    }

    @Test(description = "Validates the Special Char scenario to confirm the expected application behavior for this input combination.")
    public void EM_04_SpecialChar() {
        executeEmailTest("@@@@");
    }

    @Test(description = "Validates the Spaces scenario to confirm the expected application behavior for this input combination.")
    public void EM_05_Spaces() {
        executeEmailTest("     ");
    }

    @Test(description = "Validates the Invalid Format scenario to confirm the expected application behavior for this input combination.")
    public void EM_06_InvalidFormat() {
        executeEmailTest("hjhj");
    }

    @Test(description = "Validates the Missing Dot scenario to confirm the expected application behavior for this input combination.")
    public void EM_07_MissingDot() {
        executeEmailTest("test@mailcom");
    }

    @Test(description = "Validates the Double At scenario to confirm the expected application behavior for this input combination.")
    public void EM_08_DoubleAt() {
        executeEmailTest("test@@mail.com");
    }

    @Test(description = "Validates the Long Email scenario to confirm the expected application behavior for this input combination.")
    public void EM_09_LongEmail() {
        executeEmailTest("veryveryverylongemailtestingaddress@mail.com");
    }

    @Test(description = "Validates the Numeric Only scenario to confirm the expected application behavior for this input combination.")
    public void EM_10_NumericOnly() {
        executeEmailTest("123456");
    }
}
