import static org.junit.Assert.assertEquals;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import pages.Loginpage;
import pages.TalentPage;
import pages.TestConstants;

/**
 * Simple test which demonstrates how to run an <a href="https://github.com/appium/appium">Appium</a>
 * using <a href="http://saucelabs.com">Sauce Labs</a>.
 *
 * This test also includes the <a href="https://github.com/saucelabs/sauce-java/tree/master/junit">Sauce JUnit</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 *
 * In order to use the {@link SauceOnDemandTestWatcher}, the test must implement the {@link SauceOnDemandSessionIdProvider} interface.
 *
 * <p/>
 * The test relies on SAUCE_USER_NAME and SAUCE_ACCESS_KEY environment variables being set which reference
 * the Sauce username/access key.
 *
 * @author Ross Rowe
 */
public class SauceLoginTest implements SauceOnDemandSessionIdProvider {

    private AppiumDriver<WebElement> driver;

    private List<Integer> values;

    private static final int MINIMUM = 0;
    private static final int MAXIMUM = 10;

    private String sessionId;

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("aneenageorgek",
            "f8727bd5-12dc-40b2-8522-e5a22663ab1e");

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @BeforeClass
    public void setUp() throws Exception {
        String sauceUserName = authentication.getUsername();
        String sauceAccessKey = authentication.getAccessKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", "9.3");
        capabilities.setCapability("deviceName", "iPhone 6");
        capabilities.setCapability("browserName", "safari");
        //capabilities.setCapability("app", "https://appium.s3.amazonaws.com/TestApp7.1.app.zip");

        driver = new IOSDriver<WebElement>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
        this.sessionId = driver.getSessionId().toString();
        values = new ArrayList<Integer>();
    }



    private void populate() {
        //populate text fields with two random number
        List<WebElement> elems = driver.findElementsByClassName("UIATextField");
        Random random = new Random();
        for (WebElement elem : elems) {
            int rndNum = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
            elem.sendKeys(String.valueOf(rndNum));
            values.add(rndNum);
        }
    }

    @BeforeMethod
    public void loadLoginPage(){
        driver.get("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/");

    }


    @Test(dataProviderClass = Dataprovider.class,
            dataProvider= "LoginTestDataProvider", enabled=true, description="Login",groups={"Smoke"},priority=1)
    public void testWithDataProvider(String username, String password) {
        Loginpage log = new Loginpage(driver, username, password);
        log.clickLogin();
    }
   /* @Test
    public void testSuccessfulLogin(){
        //Creating instance of loginPage
        Loginpage loginPage = new Loginpage(driver, TestConstants.availableEmail, TestConstants.password);
        //Add the wrong user name
        //loginPage.setEmailAddress(TestConstants.email);
        //loginPage.setPassWord(TestConstants.password);
        loginPage.clickLogin();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html");
    }*/
    @AfterMethod
    public void logout()
    {
        TalentPage tal=new TalentPage(driver);
        tal.logout();
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
    public String getSessionId() {
        return sessionId;
    }
}
