package steps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import pages.HomePage;
import pages.P03_Cart;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class S03_CartSteps {
    private WebDriver driver;
    private HomePage homeObject;
    private P03_Cart addToCartObject;
    private JsonObject testData;
    private Wait<WebDriver> wait;

    public S03_CartSteps() throws FileNotFoundException {
        // Load test data from JSON file
        FileReader reader = new FileReader("src/test/resources/testdata.json");
        testData = JsonParser.parseReader(reader).getAsJsonObject();

        // Initialize wait
        wait = new FluentWait<>(Hooks.getDriver()).withTimeout(Duration.ofSeconds(20));

        // Initialize page objects
        homeObject = new HomePage(Hooks.getDriver());
        addToCartObject = new P03_Cart(Hooks.getDriver());
    }

    @Given("User is on the home page")
    public void user_is_on_the_home_page() {
        driver = Hooks.getDriver();
    }

    @When("User adds the first product to the cart")
    public void user_adds_the_first_product_to_the_cart() {
        homeObject.goToLaptopsPage();
        waitForElementAndClick(addToCartObject.firstProduct);
        waitForElementAndClick(addToCartObject.addToCartBtn);
        handleAlert("Product added");
    }

    @When("User adds the second product to the cart")
    public void user_adds_the_second_product_to_the_cart() {
        driver.navigate().to("https://www.demoblaze.com/");
        homeObject.goToLaptopsPage();
        waitForElementAndClick(addToCartObject.secondProduct);
        waitForElementAndClick(addToCartObject.addToCartBtn);
        handleAlert("Product added");
    }

    @When("User goes to the cart screen")
    public void user_goes_to_the_cart_screen() {
        homeObject.goToCartScreen();
    }

    @Then("User verifies the prices of the products in the cart")
    public void user_verifies_the_prices_of_the_products_in_the_cart() {
        verifyProductPriceInCart(addToCartObject.firstProductPriceInCart,
                testData.get("firstSet").getAsJsonObject().get("firstProductPrice").getAsString());

        verifyProductPriceInCart(addToCartObject.secondProductPriceInCart,
                testData.get("firstSet").getAsJsonObject().get("secondProductPrice").getAsString());
    }

    @Then("User verifies the total price in the cart")
    public void user_verifies_the_total_price_in_the_cart() {
        verifyTotalPriceInCart(addToCartObject.totalPrice,
                testData.get("firstSet").getAsJsonObject().get("totalPrice").getAsString());
    }

    @Then("User clicks on a placeholder button")
    public void user_clicks_on_a_placeholder_button() {
        addToCartObject.clickPlaceholderButton();
    }

    @Then("User verifies the total price in the placeholder")
    public void user_verifies_the_total_price_in_the_placeholder() {
        verifyTotalPriceInPlaceholder(addToCartObject.totalPriceInPlaceholder,
                testData.get("firstSet").getAsJsonObject().get("totalPrice").getAsString());
    }

    @Then("User fills in the required details for purchase")
    public void user_fills_in_the_required_details_for_purchase() {
        fillPlaceholder();
    }

    @Then("User clicks on the purchase button")
    public void user_clicks_on_the_purchase_button() {
        addToCartObject.clickOnPurchase();
    }

    @Then("User verifies the success message for the purchase")
    public void user_verifies_the_success_message_for_the_purchase() {
        WebElement successPurchaseMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartObject.successMsg));
        String successPurchase = successPurchaseMsg.getText();
        Assert.assertEquals(successPurchase, "Thank you for your purchase!");
    }

    // Utility methods
    private void waitForElementAndClick(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    private void handleAlert(String expectedAlertText) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals(alert.getText(), expectedAlertText);
        alert.accept();
    }

    private void verifyProductPriceInCart(By priceLocator, String expectedPrice) {
        String actualPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(priceLocator)).getText();
        assertEquals(actualPrice, expectedPrice);
    }

    private void verifyTotalPriceInCart(By totalPriceLocator, String expectedTotalPrice) {
        String actualTotalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(totalPriceLocator)).getText();
        assertEquals(actualTotalPrice, expectedTotalPrice);
    }

    private void verifyTotalPriceInPlaceholder(By placeholderLocator, String expectedTotalPrice) {
        String actualTotalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(placeholderLocator)).getText();
        assertEquals(actualTotalPrice, "Total: " + expectedTotalPrice);
    }

    private void fillPlaceholder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartObject.purchaseBtn));

        addToCartObject.fillPlaceholder(
                testData.get("secondSet").getAsJsonObject().get("customerName").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("customerCountry").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("customerCity").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("customerCardNo").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("month").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("year").getAsString()
        );
    }
}