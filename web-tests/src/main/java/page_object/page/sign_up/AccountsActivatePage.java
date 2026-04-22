package page_object.page.sign_up;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;

import java.util.Objects;

public class AccountsActivatePage extends BasePage{
    public static final String TITLE_TEXT = "Verify and activate your account";
    private static final By TITLE = By.xpath("//h1[text()='Verify and activate your account']");

    private static final By MY_PROFILE_BUTTON = By.xpath("//nav[@id='nav_side']//a[@class='my_profile']");


    public AccountsActivatePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public AccountsActivatePage waitForLoad() {
        wait.until(isTrue -> Objects.equals(driver.findElement(TITLE).getText(), TITLE_TEXT));
        return this;
    }

    public void clickMyProfileButton() {
        driver.findElement(MY_PROFILE_BUTTON).click();
    }
}
