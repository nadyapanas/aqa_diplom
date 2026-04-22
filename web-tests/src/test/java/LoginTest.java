import org.testng.annotations.Test;
import page_object.page.main.MainPage;
import page_object.page.login.PreLoginPage;
import page_object.page.login.LoginPage;
import page_object.page.sign_up.AccountsActivatePage;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "web")
public class LoginTest extends BaseTest {

    public void shouldLoginWithValidCredentials() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        AccountsActivatePage accountsActivatePage = new AccountsActivatePage(driver, wait);
        final String emailAddress = "panas.nadysha@gmail.com";
        final String password = "testPassword1";
        mainPage.open()
                .waitForLoad()
                .clickLoginButton();
        preLoginPage.waitForLoad()
                .clickLoginButton();
        loginPage.waitForLoad()
                .enterEmailAddress(emailAddress)
                .enterPassword(password)
                .clickContinueButton();
        accountsActivatePage.waitForLoad();
        assertThat(accountsActivatePage.isLoaded())
                .as("Страница активации аккаунта не открыта")
                .isTrue();
    }

    public void testEmptyEmailError() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        mainPage.open()
                .waitForLoad()
                .clickLoginButton();
        preLoginPage.waitForLoad()
                .clickLoginButton();
        loginPage.waitForLoad()
                .clickContinueButton();
        String errorText = loginPage.getErrorEmailText();
        assertThat(errorText)
                .as("Некорректный текст ошибки для поля Email address")
                .contains("Please enter an email address");
    }

    public void testEmptyPasswordError() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        mainPage.open()
                .waitForLoad()
                .clickLoginButton();
        preLoginPage.waitForLoad()
                .clickLoginButton();
        loginPage.waitForLoad()
                .clickContinueButton();
        String errorText = loginPage.getErrorPasswordText();
        assertThat(errorText)
                .as("Некорректный текст ошибки для поля Password address")
                .contains("Password is required");
    }
}
