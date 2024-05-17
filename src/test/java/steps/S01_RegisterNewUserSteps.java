package steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import pages.HomePage;
import pages.P01_RegisterNewUser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class S01_RegisterNewUserSteps {
    private WebDriver driver;
    private HomePage homeObject;
    private P01_RegisterNewUser signUpObject;
    private JsonObject testData;
    private Wait<WebDriver> wait;

    public S01_RegisterNewUserSteps() throws FileNotFoundException {
        // Load test data from JSON file
        FileReader reader = new FileReader("src/test/resources/testdata.json");
        testData = JsonParser.parseReader(reader).getAsJsonObject();

        // Initialize wait
        wait = new FluentWait<>(Hooks.getDriver()).withTimeout(Duration.ofSeconds(30));

        // Initialize page objects
        homeObject = new HomePage(Hooks.getDriver());
        signUpObject = new P01_RegisterNewUser(Hooks.getDriver());
    }

    @Given("I am on the home page for signing up")
    public void i_am_on_the_home_page_for_signing_up() {
        driver = Hooks.getDriver();
    }

    @When("I click the sign up button")
    public void i_click_the_sign_up_button() {
        homeObject.clickSignUpButton();
    }

    @When("I wait for the sign-up form to be visible")
    public void i_wait_for_the_sign_up_form_to_be_visible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpObject.signUpBtn));
    }

    @When("I enter the user credentials from the test data")
    public void i_enter_the_user_credentials_from_the_test_data() {
        signUpObject.signUpNewUser(
                testData.get("loginDetails").getAsJsonObject().get("username").getAsString(),
                testData.get("loginDetails").getAsJsonObject().get("password").getAsString()
        );
    }

    @When("I submit the sign-up form")
    public void i_submit_the_sign_up_form() {
        signUpObject.submitSignUp();
    }

    @Then("I should see a success alert with the message {string}")
    public void i_should_see_a_success_alert_with_the_message(String expectedMessage) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        assertEquals(alertText, expectedMessage);
        alert.accept();
    }
}
