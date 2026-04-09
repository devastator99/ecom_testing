package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import listeners.TestListener;
import utils.ConfigReader;
import utils.DriverFactory;

import java.time.Duration;

@Listeners(TestListener.class)
public class BaseTest {



    public WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.initDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get(ConfigReader.get("url"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
