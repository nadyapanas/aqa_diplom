import org.testng.annotations.Test;
import page_object.page.main.MainPage;
import page_object.page.login.PreLoginPage;
import page_object.page.login.LoginPage;
import page_object.page.profile.ProfilePage;
import page_object.page.sign_up.AccountsActivatePage;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "web")
public class ProfileTest extends BaseTest{

    public void checkMyProfileIsLoaded() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        ProfilePage profile = new ProfilePage(driver, wait, "Nadya", "Panas");
        AccountsActivatePage accountsActivatePage = new AccountsActivatePage(driver,wait);
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
        accountsActivatePage.waitForLoad()
                .clickMyProfileButton();
        profile.waitForLoad();
        assertThat(profile.isLoaded())
                .as("Страница профиля не открыта")
                .isTrue();
    }

    public void checkEmailAddressInSecurity() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        ProfilePage profile = new ProfilePage(driver, wait, "Nadya", "Panas");
        AccountsActivatePage accountsActivatePage = new AccountsActivatePage(driver,wait);
        final String expectedEmail = "panas.nadysha@gmail.com";
        final String password = "testPassword1";
        mainPage.open()
                .waitForLoad()
                .clickLoginButton();
        preLoginPage.waitForLoad()
                .clickLoginButton();
        loginPage.waitForLoad()
                .enterEmailAddress(expectedEmail)
                .enterPassword(password)
                .clickContinueButton();
        accountsActivatePage.waitForLoad()
                .clickMyProfileButton();
        profile.waitForLoad();
        assertThat(profile.findProfileFullNameField())
                .as("Full name должен совпадать с ожидаемым")
                .isEqualTo("Nadya Panas");
    }
}
