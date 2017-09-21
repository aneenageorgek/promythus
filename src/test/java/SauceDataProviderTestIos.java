import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Rule;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import pages.Loginpage;
import pages.TalentPage;
import pages.TestConstants;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
     * Simple test which demonstrates how to run an <a href="https://github.com/appium/appium">Appium</a>
     * using <a href="http://saucelabs.com">Sauce Labs</a>.
     * <p>
     * This test also includes the <a href="https://github.com/saucelabs/sauce-java/tree/master/junit">Sauce JUnit</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
     * <p>
     * In order to use the {@link SauceOnDemandTestWatcher}, the test must implement the {@link SauceOnDemandSessionIdProvider} interface.
     * <p>
     * <p/>
     * The test relies on SAUCE_USER_NAME and SAUCE_ACCESS_KEY environment variables being set which reference
     * the Sauce username/access key.
     *
     * @author Ross Rowe
     */
    public class SauceDataProviderTestIos implements SauceOnDemandSessionIdProvider {

    private AppiumDriver<WebElement> driver;

    private List<Integer> values;

    private static final int MINIMUM = 0;
    private static final int MAXIMUM = 10;

    private String sessionId;

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("aneenageorgek4",
            "b4e01ded-3bbc-4981-afa6-7bc8f77d9e76");

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

    @Test(dataProviderClass = Dataprovidersauce.class, dataProvider = "testSauce", priority = 1)
    public void setupDriverTest(String platformVersion, String deviceName, String browserName,
                                String browserVersion, String platformName) throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("browserVersion",browserVersion);
        capabilities.setCapability("platformName",platformName);

        String sauceUserName = authentication.getUsername();
        String sauceAccessKey = authentication.getAccessKey();
        driver = new IOSDriver<WebElement>(new URL(MessageFormat.
                format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub",sauceUserName, sauceAccessKey)), capabilities);
        this.sessionId = driver.getSessionId().toString();
        values = new ArrayList<Integer>();
        driver.get("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/");
        Loginpage log=new Loginpage(driver, TestConstants.availableEmail,TestConstants.password);
        log.clickLogin();
        TalentPage tal=new TalentPage(driver);
        tal.logout();

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

    public String getSessionId() {
        return sessionId;
    }
}


   /* @Test(dataProviderClass = Dataprovider.class,
            dataProvider = "LoginTestDataProvider", enabled = true, description = "Login", groups = {"Smoke"}, priority = 2)
    public void testWithDataProvider(String username, String password) {
        Loginpage log = new Loginpage(driver, username, password);
        log.clickLogin();
    }*/


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
        /*@AfterMethod
        public void logout() {
            TalentPage tal = new TalentPage(driver);
            tal.logout();
        }*/

       /* @AfterClass
        public void tearDown() throws Exception {
            driver.quit();
        }

        public String getSessionId() {
            return sessionId;
        }
    }*/

