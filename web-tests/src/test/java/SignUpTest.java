import org.apache.hc.core5.http.nio.entity.AbstractCharDataConsumer;
import org.testng.annotations.Test;
import page_object.page.main.MainPage;
import page_object.page.sign_up.AccountsActivatePage;
import page_object.page.sign_up.SignUpPage;

import static org.assertj.core.api.Assertions.assertThat;

public class SignUpTest extends BaseTest{
    @Test
    public void shouldSignUpWithValidCredentials(){
        MainPage mainPage = new MainPage(driver, wait);
        SignUpPage signUpPage = new SignUpPage(driver, wait);
        AccountsActivatePage accountsActivatePage = new AccountsActivatePage(driver, wait);
        final String firstName = "Nadya";
        final String lastName = "Test";
        final String email = "nadya.testing@gmail.com";
        final String password = "fudhy4Hn231";
        mainPage.open()
                .waitForLoad()
                .clickSignUpButton();
        signUpPage.waitForLoad()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .clickCreateMyAccountButton();
        accountsActivatePage.waitForLoad();
        assertThat(accountsActivatePage.isLoaded())
                .as("Страница активации аккаунта не открыта")
                .isTrue();
    }
}
