import org.apache.hc.core5.http.nio.entity.AbstractCharDataConsumer;
import org.testng.annotations.Test;
import page_object.page.main.MainPage;
import page_object.page.sign_up.AccountsActivatePage;
import page_object.page.sign_up.SignUpPage;
import com.github.javafaker.Faker;

import static org.assertj.core.api.Assertions.assertThat;

@Test(groups = "web")
public class SignUpTest extends BaseTest{

    public void shouldSignUpWithValidCredentials(){
        MainPage mainPage = new MainPage(driver, wait);
        SignUpPage signUpPage = new SignUpPage(driver, wait);
        AccountsActivatePage accountsActivatePage = new AccountsActivatePage(driver, wait);
        Faker faker = new Faker();
        mainPage.open()
                .waitForLoad()
                .clickSignUpButton();
        signUpPage.waitForLoad()
                .enterFirstName(faker.name().firstName())
                .enterLastName(faker.name().lastName())
                .enterEmail(faker.internet().safeEmailAddress())
                .enterPassword(faker.internet().password(8, 14, true, true, true))
                .clickCreateMyAccountButton();
        accountsActivatePage.waitForLoad();
        assertThat(accountsActivatePage.isLoaded())
                .as("Страница активации аккаунта не открыта")
                .isTrue();
    }
}
