package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends PageBase {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    protected By signUpBtn = By.linkText("Sign up");
    protected By loginBtn = By.linkText("Log in");
    protected By LaptopsBtn = By.linkText("Laptops");
    protected By CartBtn = By.linkText("Cart");

    public void clickSignUpButton() {
        driver.findElement(signUpBtn).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginBtn).click();
    }

    public void goToLaptopsPage() {
        driver.findElement(LaptopsBtn).click();
    }

    public void goToCartScreen() {
        driver.findElement(CartBtn).click();
    }

}