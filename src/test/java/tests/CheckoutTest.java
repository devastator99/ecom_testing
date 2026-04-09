package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import org.testng.annotations.Listeners;
import listeners.TestListener;
import utils.ConfigReader;

@Listeners(TestListener.class)
public class CheckoutTest extends BaseTest {

    @Test
    public void completePurchaseFlow() {

        // Login
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

        // Add Product
        ProductPage productPage = new ProductPage(driver);
        productPage.addItemToCart();
        productPage.goToCart();

        // Cart
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // Checkout Step 1
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterDetails("John", "Doe", "12345");
        checkoutPage.clickContinue();

        // Debug (optional)
        System.out.println("Current URL: " + driver.getCurrentUrl());

        // Step 2 → Finish
        checkoutPage.clickFinish();

        // Step 3 → Validate
        String actualMessage = checkoutPage.getSuccessMessage();
        Assert.assertEquals(actualMessage, "Thank you for your order!");
    }
}