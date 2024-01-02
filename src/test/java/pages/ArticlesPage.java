package pages;

import config.Globals;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticlesPage extends Base{

    @FindBy(xpath = "//div[@class='article']")
    List<WebElement> articles;

    @FindBy(xpath = "/html/body/div/main//p[6]")
    public List<WebElement> datesInArticles;
    public ArticlesPage() {
        super();
    }
    public void navigateArticlesPage(){
        navigateToUrl(Globals.HOMEPAGE+"/articles");

    }
    public int articleCount (){
        return  articles.size();
    }
    public void validateArticlesSorted(){
        List<String> dates = new ArrayList<>();
        datesInArticles.forEach(x->{
            dates.add(x.getText());
        });
        List<String> clone = new ArrayList<>(dates);
        Collections.sort(clone,Collections.reverseOrder());
        Assert.assertEquals(dates, clone);
    }


}
