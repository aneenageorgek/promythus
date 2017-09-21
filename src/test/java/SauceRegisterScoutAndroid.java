import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Loginpage;
import pages.Registration;
import pages.TalentPage;

import java.net.URL;
import java.util.List;

public class SauceRegisterScoutAndroid implements SauceOnDemandSessionIdProvider {

    private WebDriver driver;

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

    public static final String URL = "https://" + "aneenageorgek4" + ":" + "b4e01ded-3bbc-4981-afa6-7bc8f77d9e76" + "@ondemand.saucelabs.com:443/wd/hub";

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

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Samsung Galaxy S4 Emulator");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("browserName", "browser");
        capabilities.setCapability("deviceOrientation", "portrait");
        capabilities.setCapability("appiumVersion", "1.5.3");
        driver = new AndroidDriver(new URL(URL), capabilities);

    }
    //get the login page
    @BeforeMethod
    public void loadLoginPage(){
        driver.get("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/");
        Loginpage page = new Loginpage(driver);
        page.createAccount();
    }
    //Initailize the logout page
    @AfterMethod
    public void logout() {
        TalentPage talentPage = new TalentPage(driver);
        talentPage.logout();
    }
    //Test for registration of scout
    @Test(dataProviderClass = DataproviderRegistration.class,
            dataProvider= "RegistrationTestDataProvider", enabled=true, description="Login",groups={"Smoke"},priority=1)
    public void testWithDataProvider( String firstname,String middlename,String lastname,String countryname,
                                      String address,String phone,
                                      String currentEmail,
                                      String currentrepassword,
                                      String currentPassword) {
        Registration reg = new Registration(driver, firstname, middlename, lastname, countryname, address, phone, currentEmail,
                currentrepassword, currentPassword);
        reg.createScoutaccount();
        reg.createaccount();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html");
        Loginpage log1 = new Loginpage(driver, currentEmail, currentPassword);
        log1.clickLogin();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html");
    }
    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
    //return session
    public String getSessionId() {
        return sessionId;
    }
}



