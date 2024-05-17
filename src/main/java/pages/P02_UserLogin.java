package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P02_UserLogin extends PageBase {
    public P02_UserLogin(WebDriver driver) {
        super(driver);
    }

    protected By username = By.id("loginusername");
    protected By password = By.id("loginpassword");
    public By loginBtn = By.cssSelector("#logInModal .modal-footer .btn-primary");
    public By confirmMsg = By.id("nameofuser");

    public void userLogin(String usernameValue, String passwordValue) {
        driver.findElement(username).sendKeys(usernameValue);
        driver.findElement(password).sendKeys(passwordValue);
    }

    public void submitLogin() {
        driver.findElement(loginBtn).click();
    }
}