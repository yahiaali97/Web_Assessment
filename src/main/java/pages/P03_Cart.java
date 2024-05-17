package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P03_Cart extends PageBase {
    public P03_Cart(WebDriver driver) {
        super(driver);
    }

    public By firstProduct = By.xpath("//a[@class='hrefch' and contains(@href, 'prod.html?idp_=8')]");
    public By secondProduct = By.xpath("//a[@class='hrefch' and contains(@href, 'prod.html?idp_=9')]");
    public By addToCartBtn = By.linkText("Add to cart");
    public By firstProductInCart = By.cssSelector("tr.success:nth-child(1) > td:nth-child(2)");
    public By secondProductInCart = By.cssSelector("tr.success:nth-child(2) > td:nth-child(2)");
    public By firstProductPriceInCart = By.cssSelector("tr.success:nth-child(2) > td:nth-child(3)");
    public By secondProductPriceInCart = By.cssSelector("tr.success:nth-child(2) > td:nth-child(3)");
    public By totalPrice = By.id("totalp");
    public By placeHolderBtn = By.cssSelector("button.btn.btn-success");
    public By totalPriceInPlaceholder = By.id("totalm");
    public By namePlaceholder = By.id("name");
    public By countryPlaceholder = By.id("country");
    public By cityPlaceholder = By.id("city");
    public By cardPlaceholder = By.id("card");
    public By monthPlaceholder = By.id("month");
    public By yearPlaceholder = By.id("year");
    public By purchaseBtn = By.cssSelector(".modal-footer .btn.btn-primary[onclick=\"purchaseOrder()\"]");
    public By successMsg = By.xpath("//h2[text()='Thank you for your purchase!']");

    public void clickOnFirstProduct() {
        driver.findElement(firstProduct).click();
    }

    public void clickOnSecondProduct() {
        driver.findElement(secondProduct).click();
    }

    public void addToCart() {
        driver.findElement(addToCartBtn).click();
    }

    public void clickPlaceholderButton() {
        driver.findElement(placeHolderBtn).click();
    }

    public void fillPlaceholder(String name,String country,String city,String card,String month,String year) {
        driver.findElement(namePlaceholder).sendKeys(name);
        driver.findElement(countryPlaceholder).sendKeys(country);
        driver.findElement(cityPlaceholder).sendKeys(city);
        driver.findElement(cardPlaceholder).sendKeys(card);
        driver.findElement(monthPlaceholder).sendKeys(month);
        driver.findElement(yearPlaceholder).sendKeys(year);
    }

    public void clickOnPurchase() {
        driver.findElement(purchaseBtn).click();
    }
}