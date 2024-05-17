package tests;

import base.TestBase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.P03_Cart;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class T03_CartTest extends TestBase {
    // Page object declarations
    private HomePage homeObject;
    private P03_Cart addToCartObject;
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
        addToCartObject = new P03_Cart(driver);
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void addFirstProductToCart() {
        homeObject.goToLaptopsPage();
        waitForElementAndClick(addToCartObject.firstProduct);
        waitForElementAndClick(addToCartObject.addToCartBtn);
        handleAlert("Product added");
    }

    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    public void addSecondProductToCart() {
        driver.navigate().to("https://www.demoblaze.com/");
        homeObject.goToLaptopsPage();
        waitForElementAndClick(addToCartObject.secondProduct);
        waitForElementAndClick(addToCartObject.addToCartBtn);
        handleAlert("Product added");
    }

    @Test(priority = 3)
    @Severity(SeverityLevel.CRITICAL)
    public void verifyItemsInCart() {
        homeObject.goToCartScreen();

        verifyProductPriceInCart(addToCartObject.firstProductPriceInCart,
                testData.get("firstSet").getAsJsonObject().get("firstProductPrice").getAsString());

        verifyProductPriceInCart(addToCartObject.secondProductPriceInCart,
                testData.get("firstSet").getAsJsonObject().get("secondProductPrice").getAsString());

        verifyTotalPriceInCart(addToCartObject.totalPrice,
                testData.get("firstSet").getAsJsonObject().get("totalPrice").getAsString());

        addToCartObject.clickPlaceholderButton();
        verifyTotalPriceInPlaceholder(addToCartObject.totalPriceInPlaceholder,
                testData.get("firstSet").getAsJsonObject().get("totalPrice").getAsString());
    }

    @Test(priority = 4)
    @Severity(SeverityLevel.CRITICAL)
    public void fillPlaceholder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartObject.purchaseBtn));

        addToCartObject.fillPlaceholder(
                testData.get("secondSet").getAsJsonObject().get("customerName").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("customerCountry").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("customerCity").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("customerCardNo").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("month").getAsString(),
                testData.get("secondSet").getAsJsonObject().get("year").getAsString()
        );

        addToCartObject.clickOnPurchase();

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
}