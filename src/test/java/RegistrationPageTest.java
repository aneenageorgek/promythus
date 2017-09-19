import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Loginpage;
import pages.Registration;
import pages.TalentPage;
import pages.TestConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegistrationPageTest {
    WebDriver driver;

    //Initailize the driver
    @BeforeClass
    public void setup() {
        Map<String, Object> prefs = new HashMap<String, Object>();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/");
     }


    @BeforeMethod
    //Initialize the login page
    public void init() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html"));
        Loginpage page = new Loginpage(driver);
        page.createAccount();
    }

    @AfterMethod
    //Initailize the logout page
    public void logout() {
        TalentPage talentPage = new TalentPage(driver);
        talentPage.logout();
    }
    @Test(priority = 1,enabled =true, groups = {"Smoke"},description = "Entity created")
    //Test for talent registration
    public void sucessfullRegisrationotherTalent() {
        //String currentEmail=TestConstants.email;
        String currentEmail = RandomStringUtils.randomAlphabetic(7) + "@agenda.com";
        String currentPassword = TestConstants.password;//RandomStringUtils.randomAlphabetic(9);
        //TestConstants.password;
        Registration reg = new Registration(driver, TestConstants.newfirstname,
                TestConstants.newmiddlename,
                TestConstants.newlastname, TestConstants.countryname, TestConstants.address,
                TestConstants.phone, currentEmail,
                currentPassword, currentPassword);
        reg.createaccount();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html");
        Loginpage log1 = new Loginpage(driver, currentEmail, currentPassword);
        log1.clickLogin();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html"));
        TalentPage tal = new TalentPage(driver);
        tal.buttonClick();
        tal.clickOkInDialog();
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html");
    }

    /*@Test(dataProviderClass = DataproviderRegistration.class,
                dataProvider= "RegistrationTestDataProvider", enabled=true, description="Login",groups={"Smoke"},priority=1)
        public void testWithDataProvider( String firstname,String middlename,String lastname,String countryname,
                                         String address,String phone,
                                         String currentEmail,
                                         String currentrepassword,
                                         String currentPassword) {
            Registration reg = new Registration(driver, firstname, middlename, lastname,countryname, address, phone, currentEmail,
                    currentrepassword, currentPassword);
            reg.createaccount();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html");
        Loginpage log1 = new Loginpage(driver, currentEmail, currentPassword);
        log1.clickLogin();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html"));
        TalentPage tal = new TalentPage(driver);
        tal.buttonClick();
        tal.clickOkInDialog();
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html"));
     */





   @Test (priority = 2,enabled = true, dependsOnGroups = {"Smoke"} ,description = "Entity created")
    //Test for yourself registration
    public void sucessfullRegistrationYourSelf() throws InterruptedException {
        String currentEmail = RandomStringUtils.randomAlphabetic(7) + "@agenda.com";
        String currentPassword = TestConstants.password;
        Registration reg = new Registration(driver, TestConstants.newfirstname,
                TestConstants.newmiddlename,
                TestConstants.newlastname,TestConstants.countryname, TestConstants.address,
                TestConstants.phone, currentEmail,
                TestConstants.password, currentPassword);

        reg.createaccount();
        WebDriverWait wait3 = new WebDriverWait(driver, 10);
        wait3.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html"));

        Assert.assertEquals(driver.getCurrentUrl(), "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/sign-in.html");
        Loginpage log = new Loginpage(driver, currentEmail, currentPassword);
        //log.setEmailAddress(TestConstants.email);
        //log.setPassWord(TestConstants.password);
        log.clickLogin();
        WebDriverWait wait4 = new WebDriverWait(driver, 10);
        wait4.until(ExpectedConditions.urlToBe("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talents.html"));
        TalentPage yourself = new TalentPage(driver);
        yourself.clickYourselfRadio();
        WebDriverWait waitAfterRadioClick = new WebDriverWait(driver, 10);
        yourself.clickOkInDialog();
        WebDriverWait wait5 = new WebDriverWait(driver, 20);
        wait5.until(ExpectedConditions.urlToBe("http://52.53.181.39/talent?fill=true"));
        String actual = driver.getCurrentUrl();
        //Assert.assertEquals(actual, "http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talent.html?step=1");
        Assert.assertTrue(actual.contains("http://ec2-52-53-181-39.us-west-1.compute.amazonaws.com/talent.html?step=1"));

    }


    @AfterClass
    //initialize to close the driver

    public void finish() {
        driver.close();
    }
}

