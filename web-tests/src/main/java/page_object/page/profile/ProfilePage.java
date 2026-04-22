package page_object.page.profile;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;
import properties.WebProperties;

import java.util.Objects;

public class ProfilePage extends BasePage {
    public static final String TITLE_TEXT = "%s %s: Details";
    private static final By TITLE = By.xpath("//div[@id='page_heading_cont']//h1");
    private static final By FULL_NAME_FIELD = By.xpath("//div[@id='fullname_cont']//span");
    private static final By PROFILE_ICON = By.id("link_account");
    private static final By PROFILE_LOGOUT_BUTTON = By.xpath("//ul[@id='account_nav']//a[@class='signout']");

    public final String firstName;
    public final String secondName;

    private static final WebProperties webProperties = new WebProperties();

    public ProfilePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.firstName = webProperties.getWebUserFirstName();
        this.secondName = webProperties.getWebUserSecondName();
    }

    @Override
    public ProfilePage waitForLoad() {
        wait.until(isTrue -> Objects.equals(driver.findElement(TITLE).getText(), TITLE_TEXT.formatted(firstName, secondName)));
        return this;
    }

    public String findProfileFullNameField() {
        return driver.findElement(FULL_NAME_FIELD).getText();
    }

    public ProfilePage clickProfileIcon() {
        driver.findElement(PROFILE_ICON).click();
        return this;
    }

    public void clickProfileLogOutButton() {
        driver.findElement(PROFILE_LOGOUT_BUTTON).click();
    }
}
