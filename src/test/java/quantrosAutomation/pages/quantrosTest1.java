package quantrosAutomation.pages;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class
quantrosTest1 extends TestBase {
    SemLoginPage semLoginPage;
    GenericAction genericAction;
    SoftAssert softAssert = new SoftAssert();
    public static BrowserMobProxyServer proxy;

    @Parameters({"path"})
    @BeforeClass
    public void
    testInit(String path) {
        // Load the page in the browser

    }


    @Test(description = "BSP test results are shown for Google.com")
    public void test1SearchResultLogin() {
        WebDriver driver = null;
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        int port = proxy.getPort();

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        try {
            driver = new RemoteWebDriver(new URL(gridHubUrl), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        semLoginPage = PageFactory.initElements(driver, SemLoginPage.class);
        genericAction = PageFactory.initElements(driver, GenericAction.class);
        driver.get("https://qa4-b.sfo.quantros.com/qxplore/Home");
        genericAction.waitForJSandJQueryToLoad();
        semLoginPage.doFillLoginInput("SergeyB");
        semLoginPage.doFillPasswordInput("Password$");
        semLoginPage.doClickLogin();

        genericAction.doClickReport();
        genericAction.switchToMainframe();

        proxy.newHar("test.har");
        genericAction.buttonList.get(1).click();
        try {
            Thread.sleep(13000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(driver.getTitle(), "Quantros SRM V5.72");
    }

    @AfterClass
    public void shutdown() {
        try {

            // Get the HAR data
            Har har = proxy.getHar();
            File harFile = new File("C:\\server\\test.har");
            har.writeTo(harFile);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        proxy.stop();
    }


}
