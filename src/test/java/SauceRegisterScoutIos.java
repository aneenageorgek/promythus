import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Rule;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Loginpage;
import pages.Registration;
import pages.TalentPage;
import java.net.URL;
import java.text.MessageFormat;

public class SauceRegisterScoutIos implements SauceOnDemandSessionIdProvider {
    private AppiumDriver<WebElement> driver;
    private String sessionId;
    public SauceOnDemandAuthentication authentication=new SauceOnDemandAuthentication("aneenageorgek","f8727bd5-12dc-40b2-8522-e5a22663ab1e");
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @BeforeClass
    public void setUp() throws Exception {
        String sauceUserName = authentication.getUsername();
        String sauceAccessKey = authentication.getAccessKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform","ios");
        capabilities.setCapability("platformVersion", "9.3");
        capabilities.setCapability("deviceName", "iPhone 6");
        capabilities.setCapability("browserName", "safari");
        capabilities.setCapability("browserVersion","8.0.8");
        //capabilities.setCapability("app", "https://appium.s3.amazonaws.com/TestApp7.1.app.zip");
        driver = new IOSDriver<WebElement>(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
        this.sessionId = driver.getSessionId().toString();
        //values = new ArrayList<Integer>();
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

