package com.saucedemo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runs all Cucumber feature files through TestNG.
 *
 * Run a subset by tag:   mvn test -Dcucumber.filter.tags="@smoke"
 * Reports are written to target/cucumber-reports/
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.saucedemo.stepdefinitions", "com.saucedemo.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/cucumber-reports/failed-scenarios.txt"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * parallel = true lets scenarios run at the same time.
     * How many run together is set by data-provider-thread-count in testng.xml.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
