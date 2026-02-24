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
//public class RegisterLastNameTest extends BaseTest {
//
//    private static final String URL = "https://dev-stoex-website.p2eppl.com/auth/register";
//
//    private void executeLastNameTest(String lastName) {
//        driver.get(URL);
//        RegisterPage register = new RegisterPage(driver);
//
//        log("Last Name", lastName);
//
//        register.enterDetails("Yash", lastName,
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
//    @Test public void LN_01_Empty() { executeLastNameTest(""); }
//    @Test public void LN_02_SingleChar() { executeLastNameTest("D"); }
//    @Test public void LN_03_Numeric() { executeLastNameTest("1234"); }
//    @Test public void LN_04_SpecialChar() { executeLastNameTest("@@@"); }
//    @Test public void LN_05_AlphaNumeric() { executeLastNameTest("Dhiman123"); }
//    @Test public void LN_06_LongName() { executeLastNameTest("VeryLongLastNameTestVeryLongLastNameTestVeryLongLastNameTest"); }
//    @Test public void LN_07_Spaces() { executeLastNameTest("    "); }
//    @Test public void LN_08_LeadingSpace() { executeLastNameTest(" Dhiman"); }
//    @Test public void LN_09_TrailingSpace() { executeLastNameTest("Dhiman "); }
//    @Test public void LN_10_Valid() { Assert.assertTrue(true); }
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
@Feature("Register - Last Name Validation")
public class RegisterLastNameTest extends BaseTest {

    private static final String URL =
            "https://dev-stoex-website.p2eppl.com/auth/register";

    // ======================================
    // COMMON NEGATIVE EXECUTION METHOD
    // ======================================

    private void executeNegative(String lastName) {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        // -------- INPUT LOGGING --------
        Allure.step("Entering Last Name");
        Allure.addAttachment("Last Name Input", lastName);

        register.enterDetails(
                "Yash",
                lastName,
                "9876543210",
                "test@mail.com",
                "Valid@1234",
                "Valid@1234"
        );

        Allure.step("Clicking Submit");
        register.clickSubmit();

        // -------- INLINE ERRORS --------
        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors",
                inlineErrors.isEmpty() ? "No Inline Error" : inlineErrors.toString());

        // -------- BROWSER VALIDATION --------
        String browserValidation = (String) ((JavascriptExecutor) driver)
                .executeScript("return document.querySelector('[name=\"lastName\"]').validationMessage;");
        Allure.addAttachment("Browser Validation Message",
                browserValidation.isEmpty() ? "No Browser Validation" : browserValidation);

        // -------- ASSERT --------
        Assert.assertTrue(
                inlineErrors.size() > 0 || !browserValidation.isEmpty(),
                "Validation message should appear for invalid Last Name"
        );
    }

    // ======================================
    // NEGATIVE TEST CASES
    // ======================================

    @Test(description = "Validates the Empty scenario to confirm the expected application behavior for this input combination.")
    public void LN_01_Empty() { executeNegative(""); }

    @Test(description = "Validates the Single Char scenario to confirm the expected application behavior for this input combination.")
    public void LN_02_SingleChar() { executeNegative("D"); }

    @Test(description = "Validates the Numeric scenario to confirm the expected application behavior for this input combination.")
    public void LN_03_Numeric() { executeNegative("1234"); }

    @Test(description = "Validates the Special Char scenario to confirm the expected application behavior for this input combination.")
    public void LN_04_SpecialChar() { executeNegative("@@@"); }

    @Test(description = "Validates the Alpha Numeric scenario to confirm the expected application behavior for this input combination.")
    public void LN_05_AlphaNumeric() { executeNegative("Dhiman123"); }

    @Test(description = "Validates the Long Name scenario to confirm the expected application behavior for this input combination.")
    public void LN_06_LongName() {
        executeNegative("VeryLongLastNameTestVeryLongLastNameTestVeryLongLastNameTest");
    }

    @Test(description = "Validates the Spaces scenario to confirm the expected application behavior for this input combination.")
    public void LN_07_Spaces() { executeNegative("    "); }

    @Test(description = "Validates the Leading Space scenario to confirm the expected application behavior for this input combination.")
    public void LN_08_LeadingSpace() { executeNegative(" Dhiman"); }

    @Test(description = "Validates the Trailing Space scenario to confirm the expected application behavior for this input combination.")
    public void LN_09_TrailingSpace() { executeNegative("Dhiman "); }

    // ======================================
    // POSITIVE TEST
    // ======================================

    @Test(description = "Validates the Valid scenario to confirm the expected application behavior for this input combination.")
    @Severity(SeverityLevel.CRITICAL)
    public void LN_10_Valid() {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        String lastName = "Dhiman";

        Allure.addAttachment("Last Name Input", lastName);

        register.enterDetails(
                "Yash",
                lastName,
                "9876543210",
                "valid@mail.com",
                "Valid@1234",
                "Valid@1234"
        );

        register.acceptTerms();
        register.clickSubmit();

        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors After Submit", inlineErrors.toString());

        Assert.assertTrue(inlineErrors.isEmpty(),
                "No validation error should appear for valid Last Name");
    }
}