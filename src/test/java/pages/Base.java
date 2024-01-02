package pages;

import config.DriverFactory;
import config.Globals;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ApiHelper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Base extends Globals {
    protected ApiHelper helper;
    protected WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(Globals.DEFAULT_EXPLICIT_TIMEOUT));


    public Base() {
        PageFactory.initElements(getDriver(), this);
        helper = new ApiHelper();
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    @FindBy(css = "a[class='active']")
    WebElement homeButton;

    @FindBy(xpath = "//a[@href='/articles']")
    WebElement articlesButton;
    @FindBy(xpath = "//a[@href='/articles/topics']")
    WebElement topicsButton;
    @FindBy(css = "html > body > div > main > div:nth-of-type(1) > a:nth-of-type(4)")
    WebElement usersButton;
    @FindBy(css = "img[class='avt']")
    WebElement userImage;

    @FindBy(xpath = "//span")
    WebElement loader;


    //-------------------------methods---------------------

    public void navigateToUrl(String path){
        getDriver().get(path);
    }

    public void navigateHomePage(){
        navigateToUrl(Globals.HOMEPAGE);
    }

    public void clickArticles(){
       waitAndClickButton(articlesButton);
    }

    public void clickTopics(){
        waitAndClickButton(topicsButton);
    }

    public void clickUsers(){
        waitAndClickButton(usersButton);
    }

    public void jsTypeIntoField(WebElement element, String textToType) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].value=arguments[1]", element, textToType);
    }
    public String generateRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public String generateRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public void typeIntoField(WebElement element, String textToType) {

        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(textToType);
    }

    public void waitAndClickButton(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    public void waitForElementVisibility(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    public void waitForAlertAndValidateText(String text) {
        wait.until(ExpectedConditions.alertIsPresent());
        String alertMessage = getDriver().switchTo().alert().getText();
        Assert.assertEquals(alertMessage, text);
    }
//    public void validateTextExistOnThePage(String text){
//        System.out.println(getDriver().findElement(By.tagName("body")).toString());
//        getDriver().getPageSource().contains(text);
//    }

    public void waitForLoaderToDisappear(){
        wait.until(ExpectedConditions.invisibilityOf(loader));

    }





}
