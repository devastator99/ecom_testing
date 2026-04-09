package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    private WebDriver driver;

    private By addToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    private By cartIcon = By.className("shopping_cart_link");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addItemToCart() {
        driver.findElement(addToCartBtn).click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }
}