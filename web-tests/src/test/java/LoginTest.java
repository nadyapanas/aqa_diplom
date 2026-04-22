import org.testng.annotations.Test;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import page_object.page.main.MainPage;
import page_object.page.login.PreLoginPage;
import page_object.page.login.LoginPage;
import page_object.page.sign_up.AccountsActivatePage;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "web")
public class LoginTest extends BaseTest {

    @AllureId("TC-002")
    @Description("Логин. Авторизация с валидными данными")
    public void shouldLoginWithValidCredentials() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        AccountsActivatePage accountsActivatePage = new AccountsActivatePage(driver, wait);
        mainPage.open()
                .waitForLoad()
                .clickLoginButton();
        preLoginPage.waitForLoad()
                .clickLoginButton();
        loginPage.waitForLoad()
                .enterEmailAddress()
                .enterPassword()
                .clickContinueButton();
        accountsActivatePage.waitForLoad();
        assertThat(accountsActivatePage.isLoaded())
                .as("Страница активации аккаунта не открыта")
                .isTrue();
    }

    @AllureId("TC-003")
    @Description("Логин. Проверка валидации обязательного поля 'Email address'")
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

    @AllureId("TC-004")
    @Description("Логин. Проверка валидации обязательного поля 'Password'")
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
                .as("Некорректный текст ошибки для поля Password")
                .contains("Password is required");
    }
}
