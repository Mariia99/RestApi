package test.register_test;

import com.opencart.pages.HomePage;
import com.opencart.pages.account.*;
import com.opencart.pages.admin.AdminCustomerPage;
import com.opencart.pages.admin.AdminHomePage;
import com.opencart.pages.admin.AdminLoginPage;
import com.opencart.tools.*;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import static org.apache.commons.lang.RandomStringUtils.*;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

public class RegisterTest extends TestRunner{
    JsonDataConfig jsonDataConfig = new JsonDataConfig("TestData.json");
    AdminManager adminAccess = new AdminManager();
    String email = jsonDataConfig.getEmailFromJson(0);

    @AfterMethod
    public void finishLogout() {
        try {
            logoutUser();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            adminAccess.deleteCustomerFromAdmin(email);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Parameters({"registerText"})
    @Test(priority = 1)
    @Description("Verify that user cant register with empty data")
    public void registerWithEmptyFieldsTest(String registerText) {
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.clickRegisterButton();
        Assert.assertTrue(registerPage.getTitleRegisterBlockText().contains(registerText));
        Assert.assertEquals(true, registerPage.isFirstNameAlertPresent());
        Assert.assertEquals(true, registerPage.isLastNameAlertPresent());
        Assert.assertEquals(true, registerPage.isEmailAlertPresent());
        Assert.assertEquals(true, registerPage.isTelephoneAlertPresent());
        Assert.assertEquals(true, registerPage.isPasswordAlertPresent());
        Assert.assertTrue(registerPage.getWarningText().contains("Privacy Policy"));
    }

    @Test(priority = 2)
    @Description("Verify that user cant register with empty firstname")
    public void registerWithEmptyFirstNameTest() {
        String password = randomAlphabetic(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                "",
                randomAlphabetic(5),
                email,
                randomAlphabetic(5),
                password,
                password);
        Assert.assertEquals(true, registerPage.isFirstNameAlertPresent());
    }

    @Test(priority = 3)
    @Description("Verify that user cant register with empty lastname")
    public void registerWithEmptyLastNameTest() {
        String password = randomAlphabetic(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                "",
                email,
                randomAlphabetic(5),
                password,
                password);
        Assert.assertEquals(true, registerPage.isLastNameAlertPresent());
    }

    //first last names longer than 32 characters
    @Test(priority = 4)
    @Description("Verify that user cant register with long first and lastname (more than 32 symbols)")
    public void registerWithLongFirstLastNamesTest() {
        String password = randomAlphabetic(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(35),
                randomAlphabetic(35),
                email,
                randomAlphabetic(5),
                password,
                password);
        Assert.assertEquals(true, registerPage.isFirstNameAlertPresent());
        Assert.assertEquals(true, registerPage.isLastNameAlertPresent());
    }

    @Test(priority = 5)
    @Description("Verify that user cant register with empty email")
    public void registerWithEmptyEmailTest() {
        String password = randomAlphabetic(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                "",
                randomAlphabetic(5),
                password,
                password);
        Assert.assertEquals(true, registerPage.isEmailAlertPresent());
    }

    @Test(priority = 6)
    @Description("Verify that user cant register with existed email")
    public void registerWithExistedEmailTest() throws InterruptedException {
        String existedEmail = adminAccess.getExistedEmailFromAdmin();
        String password = randomAscii(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                 randomAlphabetic(10),
                 randomAlphabetic(10),
                 existedEmail,
                 randomAlphabetic(5),
                 password,
                 password);
        Assert.assertTrue(registerPage.getWarningText().contains("E-Mail"));
    }

    @Parameters({"loginText", "registerText"})
    @Test(priority = 7)
    @Description("Verify that user cant register with email without @ symbol")
    public void registerEmailWithoutAtSymbolTest(String loginText, String registerText) throws InterruptedException {
        String password = randomAscii(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(10),
                randomAlphabetic(10),
                randomAlphabetic(5),
                randomAlphabetic(5),
                password,
                password);
        Assert.assertTrue(registerPage.getTitleRegisterBlockText().contains(registerText));
    }

    @Parameters({"loginText"})
    @Test(priority = 8)
    @Description("Verify that user cant register with email without . symbol")
    public void registerEmailWithoutDotSymbolTest(String loginText) throws InterruptedException {
        String badEmail = randomAlphabetic(5)+"@"+randomAlphabetic(3);
        String password = randomAscii(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(10),
                randomAlphabetic(10),
                badEmail,
                randomAlphabetic(5),
                password,
                password);
        Assert.assertEquals(true, registerPage.isEmailAlertPresent());
    }


    @Test(priority = 9)
    @Description("Verify that user cant register with empty telephone")
    public void registerWithEmptyTelephoneTest() {
        String password = randomAlphabetic(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                email,
                "",
                password,
                password);
        Assert.assertEquals(true, registerPage.isTelephoneAlertPresent());
    }

    //telephone shorter than 3 characters
    @Test(priority = 10)
    @Description("Verify that user cant register with email with short telephone (less than 3 symbols)")
    public void registerWithShortTelephoneTest() {
        String password = randomAscii(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                email,
                randomAlphabetic(2),
                password,
                password);
        Assert.assertEquals(true, registerPage.isTelephoneAlertPresent());
    }

    //telephone longer than 32 characters
    @Test(priority = 11)
    @Description("Verify that user cant register with long telephone (more than 32 symbols)")
    public void registerWithLongTelephoneTest() throws InterruptedException {
        String password = randomAscii(5);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                email,
                randomAlphabetic(35),
                password,
                password);
        Assert.assertEquals(true, registerPage.isTelephoneAlertPresent());
    }

    @Test(priority = 12)
    @Description("Verify that user cant register with empty password")
    public void registerWithEmptyPasswordTest() {
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                email,
                randomAlphabetic(10),
                "",
                randomAlphabetic(5));
        Assert.assertEquals(true, registerPage.isPasswordAlertPresent());
        Assert.assertEquals(true, registerPage.isConfirmAlertPresent());
    }

    //password shorter than 4 characters
    @Test(priority = 13)
    @Description("Verify that user cant register with short password (less than 3 symbols)")
    public void registerWithShortPasswordTest() {
        String password = randomAlphabetic(3);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                email,
                randomAlphabetic(5),
                password,
                password);
        Assert.assertEquals(true, registerPage.isPasswordAlertPresent());
    }

    //password longer than 20 characters
    @Test(priority = 14)
    @Description("Verify that user cant register with long password (more than 32 symbols)")
    public void registerWithLongPasswordTest() throws InterruptedException {
        String password = randomAscii(25);
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                email,
                randomAlphabetic(5),
                password,
                password);
        Assert.assertEquals(true, registerPage.isPasswordAlertPresent());//bug
    }

    @Test(priority = 15)
    @Description("Verify that user cant register with bad password confirmation")
    public void registerWithBadConfirmTest() {
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.register(
                randomAlphabetic(5),
                randomAlphabetic(5),
                email,
                randomAlphabetic(5),
                randomAlphabetic(5),
                randomAlphabetic(5));
        Assert.assertEquals(true, registerPage.isConfirmAlertPresent());
    }

    @Test(priority = 16)
    @Description("Verify that user cant register without privacy police agreement")
    public void registerWithoutPrivacyPolicyAgreementTest() {
        RegisterPage registerPage = getHomePage().goToRegisterPage();
        registerPage.fillInputFirstname(jsonDataConfig.getFirstNameFromJson(0));
        registerPage.fillInputLastname(jsonDataConfig.getLastNameFromJson(0));
        registerPage.fillInputEmail(jsonDataConfig.getEmailFromJson(0));
        registerPage.fillInputTelephone(jsonDataConfig.getTelephoneFromJson(0));
        registerPage.fillInputPassword(jsonDataConfig.getPasswordFromJson(0));
        registerPage.fillInputConfirmPassword(jsonDataConfig.getPasswordFromJson(0));
        registerPage.clickRegisterButton();
        Assert.assertTrue(registerPage.getWarningText().contains("Privacy Policy"));
    }

    public void logoutUser() throws InterruptedException {
        if (getHomePage().isExistMyAccountDropdownOption("My Account")){
            AccountLogoutPage logoutPage = getHomePage().goToLogoutPage();
            logoutPage.logout();
        }
        else getHomePage();
    }

}
