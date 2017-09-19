import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class MacAppiumTest {

    private WebDriver driver;

    /**
     * Instantiates the {@link #driver} instance by using DesiredCapabilities which specify the
     * 'iPhone Simulator' device and 'safari' app.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone 6");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "9.2");
        capabilities.setCapability("browserName", "safari");
        //driver = new <WebElement>(new URL("http://127.0.0.1:4723/wd/hub");

        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}
