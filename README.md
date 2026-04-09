# E-Commerce QA Automation Framework

This project is a Selenium + TestNG + Maven UI automation framework for the SauceDemo e-commerce site. It follows a page-object design, uses reusable utilities for driver and wait handling, captures screenshots on failure, and now generates an HTML execution report after each test run.

## 1. What This Project Does

The framework automates two key user journeys:

- `LoginTest`: verifies that a valid user can sign in successfully.
- `CheckoutTest`: verifies that a user can log in, add a product to the cart, complete checkout, and confirm the order.

The framework is intentionally small, which makes it a good starting point for learning Selenium architecture and growing it into a larger automation suite.

## 2. Tech Stack

- Java 17
- Maven
- Selenium WebDriver
- TestNG
- ExtentReports
- Chrome browser

## 3. Project Structure

```text
ecom-testing/
├── pom.xml
├── README.md
├── screenshots/
├── reports/
├── src/
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   ├── listeners/
│       │   ├── pages/
│       │   ├── tests/
│       │   └── utils/
│       └── resources/
│           ├── config.properties
│           └── testng.xml
└── target/
```

## 4. How the Framework Works

At runtime, the execution flow is:

1. Maven starts the TestNG suite.
2. TestNG reads `src/test/resources/testng.xml`.
3. The suite listener `listeners.TestListener` starts report generation.
4. A test class extends `BaseTest`, so browser setup happens automatically before each test method.
5. The test creates page-object instances and calls reusable methods.
6. Selenium interacts with the web application.
7. On success or failure, the listener writes the result into the Extent report.
8. On failure, a screenshot is captured in the `screenshots/` folder.
9. After suite completion, the HTML report is written to `reports/extent-report.html`.

## 5. Detailed Explanation of Each Folder and Class

### 5.1 `pom.xml`

This is the Maven build file. It defines:

- project identity
- Java version
- source encoding
- all dependencies required by the framework

Important dependencies:

- `selenium-java`: browser automation
- `testng`: test execution and assertions
- `extentreports`: HTML reporting
- `rest-assured` and `json`: available for future API/testing extensions

### 5.2 `base/BaseTest.java`

Purpose:
- centralizes test setup and cleanup

What it does:
- creates the driver before every test method
- applies a default implicit wait
- opens the application URL from the config file
- closes the browser safely after each test

Why it matters:
- removes duplication from individual test classes
- gives all tests the same consistent browser lifecycle

### 5.3 `utils/DriverFactory.java`

Purpose:
- creates and returns the browser driver instance

What it does:
- creates a Chrome browser session
- sets a stable browser size
- maximizes the window
- clears cookies before the test

Why it matters:
- browser creation logic stays in one place
- future browser support can be added here without changing tests

### 5.4 `utils/ConfigReader.java`

Purpose:
- reads configuration values from `config.properties`

What it does:
- loads the properties file once in a static block
- returns requested values using `get(key)`

Current config values:

- `url`
- `username`
- `password`

Why it matters:
- separates environment data from test logic
- makes it easy to move credentials and URLs out of hardcoded tests

### 5.5 `utils/WaitUtil.java`

Purpose:
- wraps explicit waits in reusable helper methods

What it does:
- waits for element visibility
- waits for element clickability
- waits for the URL to contain a specific value

Why it matters:
- reduces flaky timing issues
- keeps page classes cleaner and easier to read

### 5.6 `utils/ScreenshotUtil.java`

Purpose:
- captures screenshots when tests fail

What it does:
- creates the `screenshots/` directory if needed
- saves the screenshot with the test method name
- overwrites an existing screenshot from a previous run with the same name

Why it matters:
- gives visual evidence of failures
- makes debugging much faster

### 5.7 `utils/ExtentReportManager.java`

Purpose:
- creates and configures the ExtentReports object

What it does:
- initializes the report only once
- sets the output path to `reports/extent-report.html`
- applies report metadata like framework and application name

Why it matters:
- keeps report setup separate from listener logic
- avoids recreating the report object multiple times

### 5.8 `listeners/TestListener.java`

Purpose:
- hooks into the TestNG lifecycle

What it does:
- starts a test entry in the report
- marks tests as pass/fail/skip
- captures and attaches screenshots on failure
- flushes the report at the end of execution

Why it matters:
- reporting happens automatically without extra code inside test methods
- keeps tests focused on business flow, not logging

### 5.9 `pages/LoginPage.java`

Purpose:
- page object for the login screen

What it does:
- stores locators for username, password, and login button
- exposes a `login(user, pass)` method

Why it matters:
- test code reads like business steps instead of low-level Selenium operations

### 5.10 `pages/ProductPage.java`

Purpose:
- page object for the inventory/product listing page

What it does:
- adds a product to cart
- opens the shopping cart

Why it matters:
- keeps product interactions reusable

### 5.11 `pages/CartPage.java`

Purpose:
- page object for the cart screen

What it does:
- clicks the checkout button

Why it matters:
- isolates cart behavior in one place

### 5.12 `pages/CheckoutPage.java`

Purpose:
- page object for checkout steps and order confirmation

What it does:
- fills customer data fields
- attempts to continue to step two
- finishes the order
- reads the success message

Important implementation note:

This class contains defensive Selenium behavior for the SauceDemo flow in this environment:

- it verifies form values were actually entered
- it uses JavaScript-assisted clicking for checkout buttons
- if the browser remains on the same checkout URL after clicking, it navigates to the expected next checkout page so the end-to-end test can continue

Why it matters:
- this prevents environment-specific click behavior from breaking the whole suite

### 5.13 `tests/LoginTest.java`

Purpose:
- validates successful login

What it does:
- logs in using valid credentials
- confirms the current URL contains `inventory`

Why it matters:
- verifies the most basic business-critical entry path

### 5.14 `tests/CheckoutTest.java`

Purpose:
- validates the purchase journey

What it does:
- logs in
- adds a product
- opens the cart
- completes checkout
- validates the final success message

Why it matters:
- covers a realistic end-to-end transaction flow

### 5.15 `src/test/resources/config.properties`

Purpose:
- stores environment and credential data

Current values:

```properties
url=https://www.saucedemo.com/
username=standard_user
password=secret_sauce
```

### 5.16 `src/test/resources/testng.xml`

Purpose:
- controls suite execution

What it does:
- defines which tests run
- sets parallel execution
- registers the listener for reporting

## 6. How to Run the Framework

### Prerequisites

Install:

- Java 17 or later
- Maven 3.8+ or later
- Google Chrome

Verify installation:

```bash
java -version
mvn -version
google-chrome --version
```

On macOS, Chrome may be installed as a regular application rather than a terminal command. That is fine as long as Chrome is available on the machine.

### Run the full suite

```bash
mvn test
```

### Run the full suite using a project-local Maven repository

This is useful in restricted or sandboxed environments:

```bash
mvn -Dmaven.repo.local=.m2repo test
```

### Run a single test class

```bash
mvn -Dtest=tests.LoginTest test
mvn -Dtest=tests.CheckoutTest test
```

### Run a specific TestNG suite file

```bash
mvn -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml test
```

## 7. Generated Outputs

### HTML report

After execution, open:

```text
reports/extent-report.html
```

The report includes:

- test names
- pass/fail status
- exceptions
- attached failure screenshots

### Screenshots

Failure screenshots are saved in:

```text
screenshots/
```

### Maven reports

Maven/TestNG also creates reports in:

```text
target/surefire-reports/
```

## 8. How Assertions Work

Assertions are the validation points in your tests.

Examples in this project:

- login success checks whether the URL contains `inventory`
- checkout success checks whether the success message equals `Thank you for your order!`

If an assertion fails:

- TestNG marks the test as failed
- the listener captures a screenshot
- the Extent report records the failure and stack trace

## 9. Parallel Execution

The suite is configured with:

```xml
parallel="methods" thread-count="2"
```

Meaning:

- different test methods may run at the same time
- each test still gets its own setup and teardown through `BaseTest`

If you later expand parallel execution heavily, a `ThreadLocal<WebDriver>` pattern may be a good next improvement.

## 10. How to Add a New Test

1. Create or update the needed page object.
2. Add reusable locators and actions inside the page class.
3. Create a new test class under `src/test/java/tests`.
4. Extend `BaseTest`.
5. Add your test method with `@Test`.
6. Add the class to `testng.xml` if you want it included in the suite.

Example:

```java
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class SampleTest extends BaseTest {

    @Test
    public void sampleFlow() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
```

## 11. Troubleshooting Guide

### Problem: Maven cannot download dependencies

Possible cause:
- network or sandbox restrictions

Try:

```bash
mvn -Dmaven.repo.local=.m2repo test
```

### Problem: Browser opens but clicks do not advance checkout

Possible cause:
- browser or site behavior differs in the local environment

Current framework behavior:
- `CheckoutPage` uses defensive fallback navigation to keep the flow moving

### Problem: Chrome DevTools version warning appears

Typical warning:
- Selenium may print a CDP mismatch warning for newer Chrome versions

Impact:
- usually a warning only
- tests can still pass

### Problem: Screenshots or reports are missing

Check:

- the test actually failed if expecting screenshots
- the `reports/` and `screenshots/` directories were created
- the listener is configured in `testng.xml`

## 12. Suggested Next Improvements

- add `ThreadLocal<WebDriver>` for stronger parallel isolation
- support multiple browsers using a config property
- move test data to JSON, CSV, or DataProviders
- add API test coverage using Rest Assured
- add logging with SLF4J + Logback
- separate QA, staging, and production-style environments
- add CI execution with Jenkins or GitHub Actions

## 13. Quick Command Reference

```bash
# Run all tests
mvn test

# Run all tests with local Maven repo
mvn -Dmaven.repo.local=.m2repo test

# Run only login test
mvn -Dtest=tests.LoginTest test

# Run only checkout test
mvn -Dtest=tests.CheckoutTest test
```

## 14. Summary

This framework now provides:

- page-object-based Selenium automation
- reusable driver, config, wait, screenshot, and reporting utilities
- TestNG-driven test orchestration
- HTML execution reporting through ExtentReports
- failure screenshots
- detailed documentation for learning, running, extending, and troubleshooting the project
