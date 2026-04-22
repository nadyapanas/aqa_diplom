import org.testng.annotations.Test;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import page_object.page.main.MainPage;
import page_object.page.login.PreLoginPage;
import page_object.page.login.LoginPage;
import page_object.page.profile.ProfilePage;
import page_object.page.sign_up.AccountsActivatePage;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "web")
public class ProfileTest extends BaseTest {

    @AllureId("TC-005")
    @Description("Профиль. Проверка загрузки страницы профиля")
    public void checkMyProfileIsLoaded() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        ProfilePage profile = new ProfilePage(driver, wait);
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
        accountsActivatePage.waitForLoad()
                .clickMyProfileButton();
        profile.waitForLoad();
        assertThat(profile.isLoaded())
                .as("Страница профиля не открыта")
                .isTrue();
    }

    @AllureId("TC-006")
    @Description("Профиль. Проверка отображения данных пользователя в поле 'Full name'")
    public void checkProfileFullNameField() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        ProfilePage profile = new ProfilePage(driver, wait);
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
        accountsActivatePage.waitForLoad()
                .clickMyProfileButton();
        profile.waitForLoad();
        assertThat(profile.findProfileFullNameField())
                .as("Full name должен совпадать с ожидаемым")
                .isEqualTo(profile.firstName + " " + profile.secondName);
    }

    @AllureId("TC-007")
    @Description("Профиль. Проверка выхода из профиля")
    public void checkProfileLogOut() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        ProfilePage profile = new ProfilePage(driver, wait);
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
        accountsActivatePage.waitForLoad()
                .clickMyProfileButton();
        profile.waitForLoad()
                .clickProfileIcon()
                .clickProfileLogOutButton();
        assertThat(preLoginPage.isLoaded())
                .as("Страница прелогина не открыта")
                .isTrue();
    }
}
