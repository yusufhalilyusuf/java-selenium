package utils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiHelper {
     SessionFilter session = new SessionFilter();
    public ApiHelper() {
        baseURI = "https://nc-news-pcfh.onrender.com/api";
    }
    RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON).log(LogDetail.ALL).setBaseUri("https://nc-news-pcfh.onrender.com/api").build();
    ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();


    public Response addComment(int id, String username, String comment) {
        Map<String, Object> bodyObj = new HashMap<>();
        bodyObj.put("body", comment);
        bodyObj.put("username", username);
//        return given().log().all().contentType(ContentType.JSON).body(bodyObj).when().post("articles/" + id + "/comments").then().assertThat().statusCode(201).extract().response();
        return given().spec(req).body(bodyObj).when().post("articles/" + id + "/comments").then().spec(res).extract().response();
    }

    public void deleteComment(int id) {
        given().contentType(ContentType.JSON).when().delete("/comments/" + id).then().assertThat().statusCode(204);
    }

    public Response patchComment(int vote, int id) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("inc_votes", vote);
        return given().contentType(ContentType.JSON).body(obj).patch("/comments/" + id).then().assertThat().statusCode(200).extract().response();

    }

    public Response postArticle() throws IOException {
        return given().contentType(ContentType.JSON).body(Files.readAllBytes(Paths.get("src/test/resources/article.json"))).when().post("articles").then()
                .assertThat().statusCode(201).extract().response();
    }

    public Response deleteArticle(int id) {
        return given().contentType(ContentType.JSON).when().delete("/articles/" + id).then().assertThat().statusCode(204).extract().response();
    }

    public String getCookie() {
        Response response = null;
        try {
            return given().contentType(ContentType.JSON).when().body(Files.readAllBytes(Paths.get("src/test/resources/jiraCreds.json"))).filter(session).when().post("http://localhost:8000/rest/auth/1/session")
                    .then().assertThat().statusCode(200)
                    .extract().response().getCookie("JSESSIONID");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public Response createJiraIssue(){

        if(session==null){
            getCookie();
        }
        try {
            return given().log().all().contentType(ContentType.JSON).body(new String(Files.readAllBytes(Paths.get("src/test/resources/jiraBug.json")))).filter(session).when().post("http://localhost:8000/rest/api/2/issue")
                    .then().log().all().assertThat().statusCode(201).extract().response();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response deleteJiraIssue(int id){
        return given().log().all().contentType(ContentType.JSON).filter(session).when().delete("http://localhost:8000/rest/api/2/issue/"+id)
                .then().log().all().assertThat().statusCode(204).extract().response();
    }

    public Response addJiraComment(String body,String type, String value,int id){
        Map<String, Object> bodyObj = new HashMap<>();
        bodyObj.put("body",body);
        bodyObj.put("visibility",new HashMap<String,String>(){{put("type",type);put("value",value);}});
        return given().pathParam("key",id).contentType(ContentType.JSON).body(bodyObj).filter(session).when().post("http://localhost:8000/rest/api/2/issue/{key}/comment")
                .then().statusCode(201).and().extract().response();
    }

    public Response addAttachment(int id){
        return given().log().all().header("X-Atlassian-Token","no-check").filter(session).pathParam("key",id).contentType(ContentType.MULTIPART)
                .multiPart("file",new File("src/test/resources/jiraPic.jpg"))
                .when().post("http://localhost:8000/rest/api/2/issue/{key}/attachments").then().log().all().statusCode(200).extract().response();

    }

    public Response getIssue(int id,String field){
        return given().relaxedHTTPSValidation().filter(session).pathParam("key",id).queryParam("fields",field).log().all().when().get("http://localhost:8000/rest/api/2/issue/{key}").then().log().all().statusCode(200).extract().response();

    }

    public String getOAuthToken(){
        return   given().log().all().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type","client_credentials")
                .formParams("scope","trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").body().jsonPath().getString("access_token");

    }

    public Response getCourseDetails(){
        return given().log().all().queryParam("access_token",getOAuthToken()).when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails");
    }

}
