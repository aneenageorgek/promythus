import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

public class Dataprovidersauce {
    public static WebDriver driver;

    @DataProvider(name="testSauce")
    public static Object[][] getlogData() {
        Object[][] data = new Object[1][5];
        data[0][0] = "9.3";
        data[0][1] = "iPhone 6";
        data[0][2]="safari";
        data[0][3]="10.0.0";
        data[0][4]="ios";
        return data;
    }

}
