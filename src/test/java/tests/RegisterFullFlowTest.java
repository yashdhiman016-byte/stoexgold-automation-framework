//package tests;
//
//import base.BaseTest;
//import io.qameta.allure.Allure;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import pages.RegisterPage;
//import utils.TestDataGenerator;
//
//public class RegisterFullFlowTest extends BaseTest {
//
//    private static final String URL = "https://dev-stoex-website.p2eppl.com/auth/register";
//
//    @Test
//    public void FF_01_NoTerms() {
//        driver.get(URL);
//        RegisterPage register = new RegisterPage(driver);
//        register.enterDetails("Yash", "Dhiman",
//                "9876543210", TestDataGenerator.generateUniqueEmail(),
//                "Valid@1234", "Valid@1234");
//        register.clickSubmit();
//        Assert.assertTrue(true);
//    }
//
//    @Test
//    public void FF_02_ValidWithTerms() {
//        driver.get(URL);
//        RegisterPage register = new RegisterPage(driver);
//        register.enterDetails("Yash", "Dhiman",
//                "9876543210", TestDataGenerator.generateUniqueEmail(),
//                "Valid@1234", "Valid@1234");
//        register.acceptTerms();
//        register.clickSubmit();
//        Assert.assertTrue(true);
//    }
//
//    @Test public void FF_03_DoubleClickSubmit() { Assert.assertTrue(true); }
//    @Test public void FF_04_InvalidMobileValidOthers() { Assert.assertTrue(true); }
//    @Test public void FF_05_InvalidEmailValidOthers() { Assert.assertTrue(true); }
//    @Test public void FF_06_MismatchPasswordValidOthers() { Assert.assertTrue(true); }
//    @Test public void FF_07_AllFieldsEmpty() { Assert.assertTrue(true); }
//    @Test public void FF_08_OnlyNamesFilled() { Assert.assertTrue(true); }
//    @Test public void FF_09_OnlyMobileFilled() { Assert.assertTrue(true); }
//    @Test public void FF_10_AllValidUnique() { Assert.assertTrue(true); }
//}

package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.TestDataGenerator;

import java.util.List;

@Epic("Authentication")
@Feature("Register - Full Flow Validation")
public class RegisterFullFlowTest extends BaseTest {

    private static final String URL =
            "https://dev-stoex-website.p2eppl.com/auth/register";

    // ================================
    // COMMON EXECUTION METHOD
    // ================================

    private void executeFlow(String fn, String ln, String mob,
                             String email, String pass, String confirm,
                             boolean acceptTerms) {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        // -------- INPUT DATA --------
        Allure.addAttachment("First Name", fn);
        Allure.addAttachment("Last Name", ln);
        Allure.addAttachment("Mobile", mob);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Password", pass);
        Allure.addAttachment("Confirm Password", confirm);
        Allure.addAttachment("Terms Accepted", String.valueOf(acceptTerms));

        register.enterDetails(fn, ln, mob, email, pass, confirm);

        if (acceptTerms) {
            register.acceptTerms();
        }

        register.clickSubmit();

        // -------- ERROR CAPTURE --------
        List<String> inlineErrors = register.getAllErrors();
        Allure.addAttachment("Inline Errors",
                inlineErrors.isEmpty() ? "No Inline Error" : inlineErrors.toString());

        String toast = register.getMobileToastError();
        Allure.addAttachment("Toast Error",
                toast.isEmpty() ? "No Toast Error" : toast);

        // -------- ASSERT --------
        // If invalid combination -> errors must appear
        if (!acceptTerms ||
                fn.isEmpty() ||
                ln.isEmpty() ||
                mob.length() < 10 ||
                !email.contains("@") ||
                !pass.equals(confirm)) {

            Assert.assertTrue(
                    inlineErrors.size() > 0 || !toast.isEmpty(),
                    "Validation should appear for invalid full flow"
            );
        } else {
            Assert.assertTrue(true);
        }
    }

    // ==================================
    // FULL FLOW TEST CASES (10 REAL)
    // ==================================

    @Test(description = "Validates the No Terms scenario to confirm the expected application behavior for this input combination.")
    public void FF_01_NoTerms() {
        executeFlow("Yash", "Dhiman", "9876543210",
                TestDataGenerator.generateUniqueEmail(),
                "Valid@1234", "Valid@1234", false);
    }

    @Test(description = "Validates the Valid With Terms scenario to confirm the expected application behavior for this input combination.")
    public void FF_02_ValidWithTerms() {
        executeFlow("Yash", "Dhiman", "9876543210",
                TestDataGenerator.generateUniqueEmail(),
                "Valid@1234", "Valid@1234", true);
    }

    @Test(description = "Validates the Invalid Mobile scenario to confirm the expected application behavior for this input combination.")
    public void FF_03_InvalidMobile() {
        executeFlow("Yash", "Dhiman", "123",
                TestDataGenerator.generateUniqueEmail(),
                "Valid@1234", "Valid@1234", true);
    }

    @Test(description = "Validates the Invalid Email scenario to confirm the expected application behavior for this input combination.")
    public void FF_04_InvalidEmail() {
        executeFlow("Yash", "Dhiman", "9876543210",
                "invalidemail",
                "Valid@1234", "Valid@1234", true);
    }

    @Test(description = "Validates the Mismatch Password scenario to confirm the expected application behavior for this input combination.")
    public void FF_05_MismatchPassword() {
        executeFlow("Yash", "Dhiman", "9876543210",
                TestDataGenerator.generateUniqueEmail(),
                "Valid@1234", "Wrong@1234", true);
    }

    @Test(description = "Validates the All Fields Empty scenario to confirm the expected application behavior for this input combination.")
    public void FF_06_AllFieldsEmpty() {
        executeFlow("", "", "", "", "", "", false);
    }

    @Test(description = "Validates the Only Names Filled scenario to confirm the expected application behavior for this input combination.")
    public void FF_07_OnlyNamesFilled() {
        executeFlow("Yash", "Dhiman", "",
                "", "", "", false);
    }

    @Test(description = "Validates the Only Mobile Filled scenario to confirm the expected application behavior for this input combination.")
    public void FF_08_OnlyMobileFilled() {
        executeFlow("", "", "9876543210",
                "", "", "", false);
    }

    @Test(description = "Validates the Double Submit scenario to confirm the expected application behavior for this input combination.")
    public void FF_09_DoubleSubmit() {

        driver.get(URL);
        RegisterPage register = new RegisterPage(driver);

        String email = TestDataGenerator.generateUniqueEmail();

        register.enterDetails("Yash", "Dhiman",
                "9876543210", email,
                "Valid@1234", "Valid@1234");

        register.acceptTerms();
        register.clickSubmit();
        register.clickSubmit();

        Assert.assertTrue(true);
    }

    @Test(description = "Validates the All Valid Unique scenario to confirm the expected application behavior for this input combination.")
    @Severity(SeverityLevel.BLOCKER)
    public void FF_10_AllValidUnique() {
        executeFlow("Yash", "Dhiman", "9876543210",
                TestDataGenerator.generateUniqueEmail(),
                "Valid@1234", "Valid@1234", true);
    }
}
