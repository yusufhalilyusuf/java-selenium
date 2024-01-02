package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class DriverFactory {
    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (webDriverThreadLocal.get() == null) {
            webDriverThreadLocal.set(createDriver());
        }
        return webDriverThreadLocal.get();
    }

    private static WebDriver createDriver() {
        WebDriver driver = null;
        switch (getBrowserType()) {
            case "chrome":
                //        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/java/drivers/chromedriver");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);

                break;
            case "firefox":
                //        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/main/java/drivers/chromedriver");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions);

                break;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Globals.DEFAULT_EXPLICIT_TIMEOUT));
        driver.manage().window().maximize();
        return driver;
    }

    private static String getBrowserType() {
        String browserType = System.getProperty("browserType");
        try {
            if (browserType == null || browserType.isEmpty()) {
                Properties properties = new Properties();
                FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/config/config.properties");
                properties.load(file);
                browserType = properties.getProperty("browserType").toLowerCase().trim();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return browserType;
    }

    public static void cleanupDriver(){
        webDriverThreadLocal.get().quit();
        webDriverThreadLocal.remove();
    }
}
