package tests;

import base.TestBase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.P02_UserLogin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class T02_UserLoginTest extends TestBase {
    private HomePage homeObject;
    private P02_UserLogin loginObject;
    private JsonObject testData;
    private Wait<WebDriver> wait;

    @BeforeClass
    public void setup() throws FileNotFoundException {
        // Load test data from JSON file
        FileReader reader = new FileReader("src/test/resources/testdata.json");
        testData = JsonParser.parseReader(reader).getAsJsonObject();

        // Initialize wait
        wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(20));

        // Initialize page objects
        homeObject = new HomePage(driver);
        loginObject = new P02_UserLogin(driver);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void userLogin() {
        // Navigate to the login form
        homeObject.clickLoginButton();

        // Wait for the login form to become visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginObject.loginBtn));

        // Enter user credentials and submit the login form
        loginObject.userLogin(
                testData.get("loginDetails").getAsJsonObject().get("username").getAsString(),
                testData.get("loginDetails").getAsJsonObject().get("password").getAsString()
        );
        loginObject.submitLogin();

        // Wait for the confirmation message and verify it
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginObject.confirmMsg));
        String welcomeMsg = messageElement.getText();
        assertEquals(welcomeMsg, "Welcome " + testData.get("loginDetails").getAsJsonObject().get("username").getAsString());
    }
}