package ApiTestSuite;

import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ApiHelper;
import utils.RahulPojo;

import java.util.ArrayList;
import java.util.List;

public class JiraTests {
    ApiHelper helper = new ApiHelper();
    List<Integer> idsToDelete = new ArrayList<Integer>();

    @BeforeClass
    public void setup(){
//        RestAssured.baseURI ="http://localhost:8000/rest/api/2/issue/";
        helper.getCookie();
    }
    @AfterClass
    public void clanUp(){
        idsToDelete.forEach(x-> {helper.deleteJiraIssue(x);});
    }


    @Test
    public void createIssue(){
        Response response = helper.createJiraIssue();
        idsToDelete.add(response.jsonPath().getInt("id"));
    }

    @Test(dependsOnMethods = "createIssue")
    public void addComment(){
        Response response = helper.addJiraComment("this is insane","role","Administrators",idsToDelete.get(0));
    }
    @Test(dependsOnMethods = "addComment")
    public void addAttachment(){
        Response response = helper.addAttachment(idsToDelete.get(0));
    }
    @Test()
    public void getIssue(){
        Response response = helper.getIssue(idsToDelete.get(0),"comment");
    }

    @Test
    public void getCourseDetailsWithToken(){
        RahulPojo obj = helper.getCourseDetails().as(RahulPojo.class);
        System.out.println(obj.getCourses().getWebAutomation().get(0).getCourseTitle());

    }
}
