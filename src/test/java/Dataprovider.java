import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

public class Dataprovider {
    public static WebDriver driver;

    @DataProvider(name = "LoginTestDataProvider")

    public static Object[][] getlogData() {
        Object[][] data = new Object[1][2];
        data[0][0] = "aneenageorgek@gmail.com";
        data[0][1] = "aneena123";
        return data;
    }
}







