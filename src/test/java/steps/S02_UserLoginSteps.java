package steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import pages.HomePage;
import pages.P02_UserLogin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;

public class S02_UserLoginSteps {
    private WebDriver driver;
    private HomePage homePage;
    private P02_UserLogin loginObject;
    private JsonObject testData;
    private Wait<WebDriver> wait;

    public S02_UserLoginSteps() throws FileNotFoundException {
        // Load test data from JSON file
        FileReader reader = new FileReader("src/test/resources/testdata.json");
        testData = JsonParser.parseReader(reader).getAsJsonObject();

        // Initialize wait
        wait = new FluentWait<>(Hooks.getDriver()).withTimeout(Duration.ofSeconds(30));

        // Initialize page objects
        homePage = new HomePage(Hooks.getDriver());
        loginObject = new P02_UserLogin(Hooks.getDriver());
    }

    @Given("User is on the homepage to login")
    public void user_is_on_the_homepage_to_login() {
        driver = Hooks.getDriver();
    }

    @When("User clicks on the login button")
    public void user_clicks_on_the_login_button() {
        homePage.clickLoginButton();
    }

    @When("User waits for the login form to appear")
    public void user_waits_for_the_login_form_to_appear() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginObject.loginBtn));
    }

    @When("User enters username and password")
    public void user_enters_username_and_password() {
        loginObject.userLogin(
                testData.get("loginDetails").getAsJsonObject().get("username").getAsString(),
                testData.get("loginDetails").getAsJsonObject().get("password").getAsString()
        );
    }

    @When("User submits the login form")
    public void user_submits_the_login_form() {
        loginObject.submitLogin();
    }

    @Then("User should see a welcome message with the username")
    public void user_should_see_a_welcome_message_with_the_username() {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(loginObject.confirmMsg));
        String welcomeMsg = messageElement.getText();
        Assert.assertEquals(welcomeMsg, "Welcome " + testData.get("loginDetails").getAsJsonObject().get("username").getAsString());
    }
}