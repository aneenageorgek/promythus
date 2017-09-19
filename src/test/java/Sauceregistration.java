import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.lang3.RandomStringUtils;
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
import pages.TestConstants;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static org.junit.Assert.assertEquals;

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
    public class Sauceregistration implements SauceOnDemandSessionIdProvider {

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
            Loginpage page = new Loginpage(driver);
            page.createAccount();
        }
        @AfterMethod
        //Initailize the logout page
        public void logout() {
            TalentPage talentPage = new TalentPage(driver);
            talentPage.logout();
        }

        /*@Test
        public void testUIComputation() throws Exception {

            // populate text fields with values
            populate();
            // trigger computation by using the button
            WebElement button = driver.findElementByClassName("UIAButton");
            button.click();
            // is sum equal ?
            WebElement texts = driver.findElementByClassName("UIAStaticText");
            assertEquals(String.valueOf(values.get(0) + values.get(1)), texts.getText());
        }*/

        //Test for talent registration

    @Test(dataProviderClass = DataproviderRegistration.class,
                dataProvider= "RegistrationTestDataProvider", enabled=true, description="Login",groups={"Smoke"},priority=1)
        public void testWithDataProvider( String firstname,String middlename,String lastname,String countryname,
                                         String address,String phone,
                                         String currentEmail,
                                         String currentrepassword,
                                         String currentPassword) {
        Registration reg = new Registration(driver, firstname, middlename, lastname, countryname, address, phone, currentEmail,
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
    }


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
        public void tearDown() throws Exception {
            driver.quit();
        }
        public String getSessionId() {
            return sessionId;
        }
    }


