package page_object.page.login;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;

import java.time.Duration;
import java.util.Objects;

public class LoginPage extends BasePage {

    public static final String TITLE_TEXT = "Welcome";

    private static final By EMAIL_ADDRESS_FIELD = By.id("username");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By CONTINUE_BUTTON = By.xpath("//*[@name='action']");
    private static final By ERROR_EMPTY_EMAIL_ADDRESS_FIELD = By.id("error-cs-username-required");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public LoginPage waitForLoad() {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(CONTINUE_BUTTON)));
        return this;
    }

    public LoginPage enterEmailAddress(String emailAddress) {
        driver.findElement(EMAIL_ADDRESS_FIELD).sendKeys(emailAddress);
        return this;
    }

    public LoginPage enterPassword(String password) {
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        return this;
    }

    public void clickContinueButton() {
        driver.findElement(CONTINUE_BUTTON).click();
    }

    public String getErrorEmailText() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_EMPTY_EMAIL_ADDRESS_FIELD));
            assert errorElement != null;
            return errorElement.getText().trim();
        } catch (TimeoutException e) {
            return "";
        }
    }
}
