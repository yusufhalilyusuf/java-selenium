package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = {"classpath:features"}, glue = {"stepDefinitions"}, monochrome = true, dryRun = false, tags = "not @ignore", plugin = {
        "pretty", "html:target/cucumber", "json:target/cucumber.json","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
})

public class CukesRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
