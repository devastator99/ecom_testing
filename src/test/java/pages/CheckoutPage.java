package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtil;

public class CheckoutPage {

    private WebDriver driver;
    private WaitUtil wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtil(driver);
    }

    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By zipCode = By.id("postal-code");
    private By continueBtn = By.id("continue");
    private By finishBtn = By.id("finish");
    private By successMsg = By.className("complete-header");

    public void enterDetails(String fName, String lName, String zip) {
        wait.waitForVisibility(firstName).sendKeys(fName);
        wait.waitForVisibility(lastName).sendKeys(lName);
        wait.waitForVisibility(zipCode).sendKeys(zip);
    }

    public void clickContinue() {
        wait.waitForClickable(continueBtn).click();
        wait.waitForUrlContains("checkout-step-two");
    }

    public void clickFinish() {
        wait.waitForClickable(finishBtn).click();
    }

    public String getSuccessMessage() {
        return wait.waitForVisibility(successMsg).getText();
    }
}