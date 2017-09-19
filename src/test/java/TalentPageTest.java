import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Loginpage;
import pages.TalentPage;
import pages.TestConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TalentPageTest {
    WebDriver driver;

    @BeforeClass
    public void init() {
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
    }
    @BeforeMethod
    public  void setup()
    {
        driver.get("http://52.53.181.39/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("http://52.53.181.39/login"));
        Loginpage loginPage = new Loginpage(driver, TestConstants.availableEmail, TestConstants.password);
        //Add the wrong user name
        //loginPage.setEmailAddress(TestConstants.email);
        //loginPage.setPassWord(TestConstants.password);
        loginPage.clickLogin();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        wait1.until(ExpectedConditions.urlToBe("http://52.53.181.39/talents"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://52.53.181.39/talents");
    }
    @Test
    public void viewprofile()
    {
        TalentPage tal=new TalentPage(driver);
        tal.myprofile();
        WebDriverWait waitforViewProfile=new WebDriverWait(driver,10);
        waitforViewProfile.until(ExpectedConditions.urlToBe("http://52.53.181.39/my-profile"));
        //Assert.assertEquals(driver.getCurrentUrl(),"http://52.53.181.39/my-profile");
        WebElement name=driver.findElement(By.id("firstName"));
        String nameOfUser=name.getAttribute("value");
        Assert.assertTrue(nameOfUser.contains("aneena"));
    }
    @Test
    public void logout()
    {
        TalentPage tal=new TalentPage(driver);
        tal.logout();
        WebDriverWait waitforLogout=new WebDriverWait(driver,10);
        waitforLogout.until(ExpectedConditions.urlToBe("http://52.53.181.39/login"));
        Assert.assertEquals(driver.getCurrentUrl(),"http://52.53.181.39/login");
    }

    @AfterClass
    public void finish()
    {
        WebDriverWait waitforCloseWindow=new WebDriverWait(driver,10);
        driver.close();

    }
}
