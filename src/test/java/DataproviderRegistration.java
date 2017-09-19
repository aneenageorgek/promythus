import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;


    public class DataproviderRegistration {
        public static WebDriver driver;

        @DataProvider(name = "RegistrationTestDataProvider")

        public static Object[][] getlogData() {
            Object[][] data = new Object[1][9];
            data[0][0] = RandomStringUtils.randomAlphabetic(7);
            data[0][1] = RandomStringUtils.randomAlphabetic(7);
            data[0][2] = RandomStringUtils.randomAlphabetic(7);
            data[0][3] = RandomStringUtils.randomAlphabetic(7);
            data[0][4] = RandomStringUtils.randomAlphabetic(7);
            data[0][5] = "4084084564";
            data[0][6] = RandomStringUtils.randomAlphabetic(7) + "@agenda.com";
            data[0][7] = "aneena123";
            data[0][8] = "aneena123";
            return data;
        }

    }
