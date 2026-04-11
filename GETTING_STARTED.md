# Getting Started

This guide gets you from a fresh clone to a successful test run as quickly as possible.

## 1. What You Need

- Java 17
- Maven 3.8+
- Google Chrome installed locally

Optional but helpful:

- An IDE such as IntelliJ IDEA or VS Code
- A terminal with access to the project root

## 2. Open The Project

Make sure your terminal is in the repository root:

```bash
cd /Users/apple/ecom-testing/ecom-testing
```

If you are using an IDE, open the folder as a Maven project so it can detect `pom.xml`.

## 3. Run The Tests

The simplest command is:

```bash
mvn test
```

If your environment cannot write to the default Maven cache, use the local repository already used by this project:

```bash
mvn -Dmaven.repo.local=.m2repo test
```

That command will:

- compile the test sources
- start Chrome
- run the TestNG suite
- generate an HTML report
- save failure screenshots if a test fails

## 4. What Gets Tested

The suite currently includes:

- `tests.LoginTest`
- `tests.CheckoutTest`

These tests cover:

- login
- adding an item to the cart
- checkout
- order confirmation

## 5. Where To Find Results

After a run, check these locations:

- `reports/extent-report.html` for the HTML test report
- `screenshots/` for failure screenshots
- `target/surefire-reports/` for Maven and TestNG output

## 6. If You Want To Run One Test

```bash
mvn -Dmaven.repo.local=.m2repo -Dtest=tests.LoginTest test
mvn -Dmaven.repo.local=.m2repo -Dtest=tests.CheckoutTest test
```

## 7. Project Files That Matter Most

- `pom.xml`: Maven dependencies and Java version
- `src/test/resources/config.properties`: URL and credentials
- `src/test/resources/testng.xml`: suite definition
- `src/test/java/base/BaseTest.java`: browser setup and teardown
- `src/test/java/listeners/TestListener.java`: reporting and screenshots
- `src/test/java/pages/`: page objects for the app flow
- `src/test/java/tests/`: the actual test cases

## 8. Common Problems

### Maven cannot download dependencies

Use the local Maven repository:

```bash
mvn -Dmaven.repo.local=.m2repo test
```

### Chrome does not launch

Check that Chrome is installed and that the machine can start a normal desktop browser session.

### A test fails on checkout

Open the screenshot in `screenshots/` and the report in `reports/extent-report.html` to see exactly where the flow stopped.

## 9. What To Read Next

If you want a deeper understanding of the framework, read the main [README.md](/Users/apple/ecom-testing/ecom-testing/README.md) after this guide. It explains every class and utility in detail.
