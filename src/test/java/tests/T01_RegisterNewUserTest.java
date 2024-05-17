package tests;

import base.TestBase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.P01_RegisterNewUser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class T01_RegisterNewUserTest extends TestBase {
    private HomePage homeObject;
    private P01_RegisterNewUser signUpObject;
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
        signUpObject = new P01_RegisterNewUser(driver);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void userSignUp() {
        // Navigate to the sign-up form
        homeObject.clickSignUpButton();

        // Wait for the sign-up form to become visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpObject.signUpBtn));

        // Enter user credentials and submit the sign-up form
        signUpObject.signUpNewUser(
                testData.get("loginDetails").getAsJsonObject().get("username").getAsString(),
                testData.get("loginDetails").getAsJsonObject().get("password").getAsString()
        );
        signUpObject.submitSignUp();

        // Handle the alert and verify the success message
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        assertEquals(alertText, "Sign up successful.");
        alert.accept();
    }
}