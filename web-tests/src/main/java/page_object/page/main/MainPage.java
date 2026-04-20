package page_object.page.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;

public class MainPage extends BasePage {

    public static final String TITLE_TEXT = "The efficient test case management tool";

    private static final String URL = "https://www.testlodge.com";

    private static final By LOGIN_BUTTON = By.xpath("//a[text()='Login']");

    private static final By SIGN_UP_BUTTON = By.xpath("//a[text()='Try for free']");

    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public MainPage waitForLoad() {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(LOGIN_BUTTON)));
        return this;
    }

    public MainPage open() {
        driver.get(URL);
        return this;
    }

    public void clickLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void clickSignUpButton() {
        driver.findElement(SIGN_UP_BUTTON).click();
    }
}
