package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_RegisterNewUser extends PageBase {
    public P01_RegisterNewUser(WebDriver driver) {
        super(driver);
    }

    protected By username = By.id("sign-username");
    protected By password = By.id("sign-password");
    public By signUpBtn = By.cssSelector("#signInModal .modal-footer .btn-primary");

    public void signUpNewUser(String usernameValue, String passwordValue) {
        driver.findElement(username).sendKeys(usernameValue);
        driver.findElement(password).sendKeys(passwordValue);

    }

    public void submitSignUp() {
        driver.findElement(signUpBtn).click();
    }
}