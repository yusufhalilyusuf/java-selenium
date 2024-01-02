package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.sql.Timestamp;

import static config.DriverFactory.cleanupDriver;
import static config.DriverFactory.getDriver;

public class Hooks {
    @Before
    public void setup() {
        getDriver();
    }

    @After
    public void tearDown() {
        cleanupDriver();
    }

    @AfterStep
    public void captureImage(Scenario scenario) {
        if (scenario.isFailed()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //long
            String timeMilisecond = Long.toString(timestamp.getTime());
            byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", timeMilisecond);
        }
    }
}
