package quantrosAutomation.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by sboreisha on 3/27/2017.
 */
public class GenericAction extends Page {
    public GenericAction(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(xpath = "//*[contains(text(), 'Best Choice')]")
    WebElement buttonBestChoice;

    @FindBy(how = How.ID, using = "divTop1")
    private WebElement report1;

    @FindBy(how = How.ID, using = "divSub0")
    private WebElement report2;

    @FindBy(how = How.ID, using = "divSub2_1")
    private WebElement report3;

    @FindBy(how = How.ID, using = "frmMenu")
    private WebElement frameMenu;

    @FindBy(how = How.ID, using = "frmMain")
    private WebElement frameMain;

    @FindBy(how = How.CSS, using = ".tasksRowNormal")
    private List<WebElement> reportList;

    @FindBy(how = How.CSS, using = ".accordionTabHeaderNormal")
    public List<WebElement> buttonList;


    public void doClickReport() {
        switchToMenuframe();
        report1.click();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switchToMainframe();
        report2.click();
        report3.click();
        webDriver.switchTo().defaultContent();
        waitForJSandJQueryToLoad();
        switchToMainframe();
        reportList.get(1).click();
        webDriver.switchTo().defaultContent();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switchToMainframe();
        buttonList.get(1).click();
        try {
            Thread.sleep(13000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchToMenuframe() {
        WebDriverWait wait = new WebDriverWait(webDriver, 60);
        webDriver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameMenu));
    }

    public void switchToMainframe() {
        WebDriverWait wait = new WebDriverWait(webDriver, 60);
        webDriver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameMain));
    }

    public boolean waitForJSandJQueryToLoad() {
        WebDriverWait wait = new WebDriverWait(webDriver, 60);
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) getWebDriver()).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };
        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) getWebDriver()).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }
}
