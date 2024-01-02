package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.testng.Assert.*;
import pages.ArticlesPage;
import pages.Base;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticlesPageStepDefs extends Base {

    private ArticlesPage articlesPage ;

    public ArticlesPageStepDefs(ArticlesPage articlesPage) {
        this.articlesPage = articlesPage;
    }

    @Given("the user is on articles page")
    public void the_user_is_on_home_page()  {

    articlesPage.navigateArticlesPage();
        waitForLoaderToDisappear();
        assertEquals(getDriver().getCurrentUrl(),"https://fe-news.netlify.app/articles");
    }

    @Then("the following should be presented")
    public void the_following_should_be_presented(List<String> list) {
        String[] expected = {"HOME","USER","TOPICS","ARTICLES"};
        list.forEach(item->{
            assertTrue(Arrays.asList(expected).contains(item));
        });
    }
    @Then("all articles are shown on the page")
    public void all_articels_are_shown_on_the_page() {
        assertEquals(articlesPage.articleCount(), 37);
    }

    @Then("^sorted by (Date|Votes|Comment Count) as default$")
    public void sorted_by_date_as_default(String sort) {
        articlesPage.validateArticlesSorted();
        System.out.println(sort);


    }
    @Then("^ordered by (Descending|Ascending) as default$")
    public void ordered_by_descending_as_default(String order) {

    }
}
