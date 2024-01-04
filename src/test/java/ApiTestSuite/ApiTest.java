package ApiTestSuite;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ApiHelper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class ApiTest {

    ApiHelper helper = new ApiHelper();
    List<Integer> commentIdToDelete = new ArrayList<>();
    int articleIdToDelete;
    @AfterClass
    public void tearDown(){
    commentIdToDelete.forEach(x->{
        helper.deleteComment(x);
    });
    if (articleIdToDelete!=0){

    helper.deleteArticle(articleIdToDelete);
    }
    }

    @Test
    public void getAllArticles() {
        Response response = given().log().all().contentType(ContentType.JSON).
                when().get("/articles").then().log().all().
                assertThat().statusCode(200).body("articles[0].author",equalTo("cooljmessy")).extract().response();


    }
    @Test(priority = 1,dataProvider = "comments")
    public void postComment(int id, String comment, String userName) {
        Response response = helper.addComment(id,userName,comment);
        commentIdToDelete.add(response.body().jsonPath().get("id"));


    }

    @DataProvider(name = "comments")
    public Object[][] getData(){
        return new Object[][] {{35,"first comment","tickle122"},{35,"second comment","grumpy19"},{35,"third one","jessjelly"}};
    }

    @Test(priority = 2)
    public void patchComment() {
        Response response = helper.patchComment(2,commentIdToDelete.get(0));
        response.then().assertThat().statusCode(200);

    }

    @Test
    public void postArticle() throws IOException {
    Response response = helper.postArticle();
    articleIdToDelete = response.body().jsonPath().get("article[0].article_id");
    }



}
