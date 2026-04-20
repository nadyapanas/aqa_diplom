package page_object.page.sign_up;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;
import page_object.page.login.LoginPage;

public class SignUpPage extends BasePage{
    public static final String TITLE_TEXT = "Let's create your account";

    private static final By FIRST_NAME_FIELD = By.id("user_profile_firstname");
    private static final By LAST_NAME_FIELD = By.id("user_profile_lastname");
    private static final By EMAIL_FIELD = By.id("user_profile_email");
    private static final By PASSWORD_FIELD = By.id("user_profile_password");
    private static final By CREATE_MY_ACCOUNT_BUTTON = By.xpath("//*[@name='commit']");

    public SignUpPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public SignUpPage waitForLoad() {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(CREATE_MY_ACCOUNT_BUTTON)));
        return this;
    }

    public SignUpPage enterFirstName(String firstName) {
        driver.findElement(FIRST_NAME_FIELD).sendKeys(firstName);
        return this;
    }

    public SignUpPage enterLastName(String lastName) {
        driver.findElement(LAST_NAME_FIELD).sendKeys(lastName);
        return this;
    }

    public SignUpPage enterEmail(String email) {
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        return this;
    }

    public SignUpPage enterPassword(String password) {
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        return this;
    }

    public void clickCreateMyAccountButton() {
        driver.findElement(CREATE_MY_ACCOUNT_BUTTON).click();
    }
}
