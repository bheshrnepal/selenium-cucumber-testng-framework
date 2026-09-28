# Selenium + Cucumber + TestNG Framework (Page Object Model)

UI test automation framework for the [SauceDemo](https://www.saucedemo.com/) practice store, built with:

- **Selenium 4** (Java 16+): browser automation. Selenium Manager downloads the right driver automatically.
- **Cucumber 7**: BDD scenarios written in Gherkin (`.feature` files)
- **TestNG 7**: test runner, assertions, data providers and parallel execution
- **Page Object Model (POM)**: each page's locators and actions live in their own class
- **Maven**: build and dependency management
- **GitHub Actions**: runs the tests in headless Chrome on every push

## Project structure

```
src/main/java/com/saucedemo/
├── pages/                  # Page Object Model
│   ├── BasePage.java        # shared waits and actions (click, type, getText)
│   ├── LoginPage.java
│   ├── InventoryPage.java   # products list, sorting, add/remove, cart badge, logout
│   ├── CartPage.java
│   └── CheckoutPage.java    # your info → overview → complete
└── utils/
    ├── ConfigReader.java    # reads config.properties (overridable with -D flags)
    └── DriverFactory.java   # ThreadLocal WebDriver (Chrome / Firefox / Edge, headless option)

src/test/java/com/saucedemo/
├── hooks/Hooks.java         # opens a browser before each scenario, screenshot on failure, then closes it
├── runners/TestRunner.java  # Cucumber + TestNG runner (supports parallel scenarios)
├── stepdefinitions/         # Gherkin steps → page object calls
│   ├── LoginSteps.java
│   ├── ProductSteps.java
│   └── CheckoutSteps.java
└── tests/                   # the same pages used from plain TestNG (no Cucumber)
    ├── BaseTest.java
    ├── LoginTest.java
    └── CheckoutTest.java

src/test/resources/
├── config.properties        # URL, browser, headless, timeouts, test users
└── features/
    ├── login.feature
    ├── products.feature
    └── checkout.feature

testng.xml                   # suite for Cucumber scenarios (default)
testng-pom.xml               # suite for plain TestNG tests
```

## What's covered

| Feature | Scenarios |
|---|---|
| Login | valid login, locked-out user, wrong password, missing username, missing password, logout |
| Products | add one product, add several and remove one, sort by name A-Z / Z-A, sort by price low-high / high-low |
| Checkout | full order end to end (total = item total + tax), required first name / last name / postal code |

## Requirements

- Java 16 or newer (`java -version`)
- Maven 3.8+ (`mvn -v`)
- Google Chrome (or Firefox / Edge)

## How to run

```bash
# Run all Cucumber scenarios (default suite: testng.xml)
mvn test

# Run only scenarios with a tag
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@checkout and not @negative"

# Run headless (no browser window), or in another browser
mvn test -Dheadless=true
mvn test -Dbrowser=firefox

# Run the plain TestNG + POM tests instead of Cucumber
mvn test -Dsuite=testng-pom.xml
```

From IntelliJ you can also right-click `TestRunner.java`, a `.feature` file, or `testng.xml` and choose **Run**.

### Running scenarios in parallel

Open `testng.xml` and change `data-provider-thread-count="1"` to, for example, `"3"`.
This is safe because `DriverFactory` gives every thread its own browser (`ThreadLocal`).

## Reports

After a run, open:

- `target/cucumber-reports/cucumber.html`: Cucumber HTML report, with screenshots attached to failed scenarios
- `target/cucumber-reports/cucumber.json`: JSON output (for Jenkins or other report plugins)
- `target/cucumber-reports/failed-scenarios.txt`: list of failed scenarios to re-run
- `target/surefire-reports/`: TestNG reports

## Adding a new test

1. **Page object:** add locators and methods for the page in `pages/`.
2. **Feature file:** describe the behavior in Gherkin in `features/`.
3. **Step definitions:** connect each Gherkin line to page-object calls in `stepdefinitions/`.
4. Run it with a tag: `mvn test -Dcucumber.filter.tags="@yourTag"`.

## Design notes

- **No `Thread.sleep()`.** Every action in `BasePage` uses an explicit wait (`WebDriverWait`).
- **Fresh browser per scenario.** Scenarios never depend on each other.
- **Config over code.** Change URL, browser, or timeouts in `config.properties` or with `-Dkey=value`.
- **Locators live only in page objects.** Step definitions and tests never touch `By` or `WebElement`.
