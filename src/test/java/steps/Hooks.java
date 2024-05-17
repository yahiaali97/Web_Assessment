package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Hooks {

    static WebDriver driver;

    @Before
    public static void openBrowser() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.demoblaze.com/");
    }

    @After
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}