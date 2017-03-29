package quantrosAutomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Created by sboreisha on 3/27/2017.
 */
public class SemLoginPage extends Page {
    public SemLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(how = How.NAME, using = "username")
    private WebElement loginInput;

    @FindBy(how = How.NAME, using = "password")
    private WebElement passwordInput;

    @FindBy(how = How.CSS, using = "a img")
    private List<WebElement> pageButtons;

    public void doFillLoginInput(String text) {
        loginInput.sendKeys(text);
    }

    public void doFillPasswordInput(String text) {
        passwordInput.sendKeys(text);
    }

    public void doClickLogin() {
        pageButtons.get(0).submit();
    }
}
