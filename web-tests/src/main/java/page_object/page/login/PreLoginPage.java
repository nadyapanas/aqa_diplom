package page_object.page.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;

public class PreLoginPage extends BasePage {
    public static final String TITLE_TEXT = "Welcome";

    private static final By LOGIN_BUTTON = By.xpath("//*[@name='commit']");

    public PreLoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public PreLoginPage waitForLoad() {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(LOGIN_BUTTON)));
        return this;
    }

    public void clickLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
    }
}
