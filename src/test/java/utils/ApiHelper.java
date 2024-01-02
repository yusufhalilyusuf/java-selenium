package utils;

import com.google.gson.JsonObject;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiHelper {

    public ApiHelper() {
        baseURI = "https://nc-news-pcfh.onrender.com/api";
    }
    public ArticlePojo getArticles(){
        return given().queryParam("limit","all").contentType(ContentType.JSON)
                .when().get("/articles").body().as(ArticlePojo.class);

    }

    public Response addComment(int id, String username, String comment){
        Map<String,Object> bodyObj = new HashMap<>();
        bodyObj.put("body",comment);
        bodyObj.put("username",username);
        return given().log().all().contentType(ContentType.JSON).body(bodyObj).when().post("articles/"+id+"/comments").then().assertThat().statusCode(201).extract().response();

    }

    public void deleteComment(int id){
        given().contentType(ContentType.JSON).when().delete("/comments/"+id).then().assertThat().statusCode(204);
    }

    public Response patchComment(int vote, int id){
        Map<String,Object> obj = new HashMap<>();
        obj.put("inc_votes",vote);
        return given().contentType(ContentType.JSON).body(obj).patch("/comments/"+id).then().assertThat().statusCode(200).extract().response();

    }

    public Response postArticle() throws IOException {
        return given().contentType(ContentType.JSON).body(Files.readAllBytes(Paths.get("src/test/resources/article.json"))).when().post("articles").then()
                .assertThat().statusCode(201).extract().response();
    }

    public Response deleteArticle(int id){
        return given().contentType(ContentType.JSON).when().delete("/articles/"+id).then().assertThat().statusCode(204).extract().response();
    }

    public String cookie (){
        given().contentType(ContentType.JSON).when().get("http://localhost:8000/jira/rest/auth/1/session")
    }

}
