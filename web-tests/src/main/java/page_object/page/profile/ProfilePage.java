package page_object.page.profile;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;

import java.util.Objects;

public class ProfilePage extends BasePage {
    public static final String TITLE_TEXT = "Your profile";
    private static final By TITLE = By.xpath("//h1[text()='Your profile']");
    private static final By CREATE_NEW_ACCOUNT_BUTTON = By.xpath("//a[text()='Create new account']");
    private static final By EMAIL_ADDRESS = By.xpath("//div[@id='secuirty_info_cont']//div[@class='standalone_content with_link']");

    public ProfilePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public ProfilePage waitForLoad() {
        wait.until(isTrue -> Objects.equals(driver.findElement(TITLE).getText(), TITLE_TEXT));
        return this;
    }

    public boolean isCreateNewAccountButtonDisplayed(){
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(CREATE_NEW_ACCOUNT_BUTTON));
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public String findEmailAddress(){
        WebElement emailContainer = driver.findElement(EMAIL_ADDRESS);
        WebElement emailSpan = emailContainer.findElement(By.xpath(".//span"));
        return emailSpan.getText();
    }
}
