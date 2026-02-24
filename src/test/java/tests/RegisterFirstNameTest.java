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
//public class RegisterFirstNameTest extends BaseTest {
//
//    private static final String URL =
//            "https://dev-stoex-website.p2eppl.com/auth/register";
//
//    private void runTest(String value) {
//        driver.get(URL);
//        RegisterPage register = new RegisterPage(driver);
//
//        log("First Name", value);
//
//        register.enterDetails(value, "Dhiman",
//                "9876543210", "test@mail.com",
//                "Valid@1234", "Valid@1234");
//
//        register.clickSubmit();
//
//        List<String> errors = register.getAllErrors();
//        Allure.addAttachment("Errors", errors.toString());
//
//        Assert.assertTrue(errors.size() > 0);
//    }
//
//    @Test public void FN_01_Empty() { runTest(""); }
//    @Test public void FN_02_Numeric() { runTest("1234"); }
//    @Test public void FN_03_Special() { runTest("@@@"); }
//    @Test public void FN_04_SingleChar() { runTest("A"); }
//    @Test public void FN_05_AlphaNumeric() { runTest("Yash123"); }
//    @Test public void FN_06_LongName() { runTest("VeryLongNameTestingField"); }
//    @Test public void FN_07_Spaces() { runTest("     "); }
//    @Test public void FN_08_LeadingSpace() { runTest(" Yash"); }
//    @Test public void FN_09_TrailingSpace() { runTest("Yash "); }
//    @Test public void FN_10_Valid() { Assert.assertTrue(true); }
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
@Feature("Register - First Name Validation")
public class RegisterFirstNameTest extends BaseTest {

    private static final String URL =
            "https://dev-stoex-website.p2eppl.com/auth/register";

    // ==========================================
    // COMMON EXECUTION METHOD
    // ==========================================

    private void runNegativeTest(String value) {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        // ---------- INPUT ----------
        Allure.step("Entering First Name");
        Allure.addAttachment("First Name Input", value);

        register.enterDetails(
                value,
                "Dhiman",
                "9876543210",
                "test@mail.com",
                "Valid@1234",
                "Valid@1234"
        );

        Allure.step("Clicking Submit");
        register.clickSubmit();

        // ---------- INLINE ERRORS ----------
        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors",
                inlineErrors.isEmpty() ? "No Inline Error" : inlineErrors.toString());

        // ---------- BROWSER VALIDATION ----------
        String browserValidation = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.querySelector('[name=\"firstName\"]').validationMessage;");
        Allure.addAttachment("Browser Validation Message",
                browserValidation.isEmpty() ? "No Browser Validation" : browserValidation);

        // ---------- ASSERT ----------
        Assert.assertTrue(
                inlineErrors.size() > 0 || !browserValidation.isEmpty(),
                "Validation message should appear for invalid First Name"
        );
    }

    // ==========================================
    // NEGATIVE TEST CASES
    // ==========================================

    @Test(description = "Validates the Empty scenario to confirm the expected application behavior for this input combination.")
    public void FN_01_Empty() { runNegativeTest(""); }

    @Test(description = "Validates the Numeric scenario to confirm the expected application behavior for this input combination.")
    public void FN_02_Numeric() { runNegativeTest("1234"); }

    @Test(description = "Validates the Special scenario to confirm the expected application behavior for this input combination.")
    public void FN_03_Special() { runNegativeTest("@@@"); }

    @Test(description = "Validates the Single Char scenario to confirm the expected application behavior for this input combination.")
    public void FN_04_SingleChar() { runNegativeTest("A"); }

    @Test(description = "Validates the Alpha Numeric scenario to confirm the expected application behavior for this input combination.")
    public void FN_05_AlphaNumeric() { runNegativeTest("Yash123"); }

    @Test(description = "Validates the Long Name scenario to confirm the expected application behavior for this input combination.")
    public void FN_06_LongName() { runNegativeTest("VeryLongNameTestingField"); }

    @Test(description = "Validates the Spaces scenario to confirm the expected application behavior for this input combination.")
    public void FN_07_Spaces() { runNegativeTest("     "); }

    @Test(description = "Validates the Leading Space scenario to confirm the expected application behavior for this input combination.")
    public void FN_08_LeadingSpace() { runNegativeTest(" Yash"); }

    @Test(description = "Validates the Trailing Space scenario to confirm the expected application behavior for this input combination.")
    public void FN_09_TrailingSpace() { runNegativeTest("Yash "); }

    // ==========================================
    // POSITIVE TEST
    // ==========================================

    @Test(description = "Validates the Valid scenario to confirm the expected application behavior for this input combination.")
    @Severity(SeverityLevel.CRITICAL)
    public void FN_10_Valid() {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        String value = "Yash";

        Allure.addAttachment("First Name Input", value);

        register.enterDetails(
                value,
                "Dhiman",
                "9876543210",
                "valid@test.com",
                "Valid@1234",
                "Valid@1234"
        );

        register.acceptTerms();
        register.clickSubmit();

        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors After Submit", inlineErrors.toString());

        Assert.assertTrue(inlineErrors.isEmpty(),
                "No validation error should appear for valid First Name");
    }
}