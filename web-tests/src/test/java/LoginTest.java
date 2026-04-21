import org.testng.annotations.Test;
import page_object.page.main.MainPage;
import page_object.page.login.PreLoginPage;
import page_object.page.login.LoginPage;
import page_object.page.profile.ProfilePage;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "web")
public class LoginTest extends BaseTest {

    public void shouldLoginWithValidCredentials() {
        MainPage mainPage = new MainPage(driver, wait);
        PreLoginPage preLoginPage = new PreLoginPage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        ProfilePage profile = new ProfilePage(driver,wait);
        final String emailAddress = "panas.nadysha@gmail.com";
        final String password = "fudhy4Hn";
        mainPage.open()
                .waitForLoad()
                .clickLoginButton();
        preLoginPage.waitForLoad()
                .clickLoginButton();
        loginPage.waitForLoad()
                .enterEmailAddress(emailAddress)
                .enterPassword(password)
                .clickContinueButton();
        assertThat(profile.isLoaded())
                .as("Страница профиля не открыта")
                .isTrue();
    }

    public void testEmptyEmailError(){
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

    public void testEmptyPasswordError(){
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
                .as("Некорректный текст ошибки для поля Password address")
                .contains("Password is required");
    }
}
