package page_object.page.profile;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_object.page.BasePage;

import java.util.Objects;

public class ProfilePage extends BasePage {
    public static final String TITLE_TEXT = "%s %s: Details";
    private static final By TITLE = By.xpath("//div[@id='page_heading_cont']//h1");
    private static final By FULL_NAME_FIELD = By.xpath("//div[@id='fullname_cont']//span");

    protected final String firstName;
    protected final String secondName;

    public ProfilePage(WebDriver driver, WebDriverWait wait, String firstName, String secondName) {
        super(driver, wait);
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public ProfilePage waitForLoad() {
        wait.until(isTrue -> Objects.equals(driver.findElement(TITLE).getText(), TITLE_TEXT.formatted(firstName, secondName)));
        return this;
    }

    public String findProfileFullNameField() {
        return driver.findElement(FULL_NAME_FIELD).getText();
    }
}
