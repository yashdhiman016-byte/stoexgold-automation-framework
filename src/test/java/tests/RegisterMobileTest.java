package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.util.List;

/**
 * Validates mobile number rules and OTP send behavior in registration.
 */
@Epic("Authentication")
@Feature("Register - Mobile Validation")
public class RegisterMobileTest extends BaseTest {

    private static final String URL =
            "https://dev-stoex-website.p2eppl.com/auth/register";

    // COMMON NEGATIVE EXECUTION

    private void executeNegative(String mobileInput) {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        // -------- INPUT --------
        Allure.step("Entering Mobile Number");
        Allure.addAttachment("Mobile Input", mobileInput);

        register.enterDetails(
                "Yash",
                "Dhiman",
                mobileInput,
                "test@mail.com",
                "Valid@1234",
                "Valid@1234"
        );

        Allure.step("Clicking Send OTP");
        register.clickMobileSendOtp();

        // -------- TOAST ERROR --------
        String toastError = register.getMobileToastError();
        Allure.addAttachment("Toast Error",
                toastError.isEmpty() ? "No Toast Error" : toastError);

        // -------- INLINE ERRORS --------
        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors",
                inlineErrors.isEmpty() ? "No Inline Error" : inlineErrors.toString());

        // -------- BROWSER VALIDATION --------
        String browserValidation = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.querySelector('[name=\"contactNumber\"]').validationMessage;");
        Allure.addAttachment("Browser Validation Message",
                browserValidation.isEmpty() ? "No Browser Validation" : browserValidation);

        // -------- ASSERT --------
        Assert.assertTrue(
                !toastError.isEmpty() ||
                        inlineErrors.size() > 0 ||
                        !browserValidation.isEmpty(),
                "Validation message should appear for invalid mobile number"
        );
    }

    // NEGATIVE TEST CASES

    @Test(description = "Validates the Empty scenario to confirm the expected application behavior for this input combination.")
    public void MB_01_Empty() { executeNegative(""); }

    @Test(description = "Validates the Short scenario to confirm the expected application behavior for this input combination.")
    public void MB_02_Short() { executeNegative("123"); }

    @Test(description = "Validates the Long scenario to confirm the expected application behavior for this input combination.")
    public void MB_03_Long() { executeNegative("1234567890123"); }

    @Test(description = "Validates the Alphabet scenario to confirm the expected application behavior for this input combination.")
    public void MB_04_Alphabet() { executeNegative("abcdefghij"); }

    @Test(description = "Validates the Special scenario to confirm the expected application behavior for this input combination.")
    public void MB_05_Special() { executeNegative("@@@@"); }

    @Test(description = "Validates the Mixed scenario to confirm the expected application behavior for this input combination.")
    public void MB_06_Mixed() { executeNegative("9876abc123"); }

    @Test(description = "Validates the Spaces scenario to confirm the expected application behavior for this input combination.")
    public void MB_07_Spaces() { executeNegative("     "); }

    @Test(description = "Validates the Leading Space scenario to confirm the expected application behavior for this input combination.")
    public void MB_08_LeadingSpace() { executeNegative(" 9876543210"); }

    @Test(description = "Validates the Trailing Space scenario to confirm the expected application behavior for this input combination.")
    public void MB_09_TrailingSpace() { executeNegative("9876543210 "); }

    // VALID FORMAT (May Proceed to OTP Screen)

    @Test(description = "Validates the Valid Format scenario to confirm the expected application behavior for this input combination.")
    @Severity(SeverityLevel.CRITICAL)
    public void MB_10_ValidFormat() {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        String mobile = "9876543210";

        Allure.addAttachment("Mobile Input", mobile);

        register.enterDetails(
                "Yash",
                "Dhiman",
                mobile,
                "test@mail.com",
                "Valid@1234",
                "Valid@1234"
        );

        register.clickMobileSendOtp();

        String toastError = register.getMobileToastError();
        Allure.addAttachment("Toast Error", toastError);

        // For valid format, we expect NO validation error
        Assert.assertTrue(toastError.isEmpty(),
                "No validation error should appear for valid mobile format");
    }
}
