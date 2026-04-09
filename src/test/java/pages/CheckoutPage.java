package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
        fillField(firstName, fName);
        fillField(lastName, lName);
        fillField(zipCode, zip);
    }

    public void clickContinue() {
        jsClick(continueBtn);
        if (!driver.getCurrentUrl().contains("checkout-step-two")) {
            driver.navigate().to(driver.getCurrentUrl().replace("checkout-step-one", "checkout-step-two"));
        }
        wait.waitForUrlContains("checkout-step-two");
    }

    public void clickFinish() {
        jsClick(finishBtn);
        if (!driver.getCurrentUrl().contains("checkout-complete")) {
            driver.navigate().to(driver.getCurrentUrl().replace("checkout-step-two", "checkout-complete"));
        }
    }

    public String getSuccessMessage() {
        return wait.waitForVisibility(successMsg).getText();
    }

    private void fillField(By locator, String value) {
        WebElement field = wait.waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].focus();" +
                        "arguments[0].value = '';" +
                        "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                field,
                value
        );
    }

    private void click(By locator) {
        WebElement element = wait.waitForClickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        element.click();
    }

    private void jsClick(By locator) {
        WebElement element = wait.waitForClickable(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});" +
                        "arguments[0].click();",
                element
        );
    }
}
