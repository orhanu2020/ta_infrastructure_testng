package org.cb.ta.tests.APITest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.cb.ta.tests.APITest.utils.Helpers;
import org.openqa.selenium.remote.http.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAPITest {
    public RestAPITest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(groups = {"api", "smoke", "regression"})
    public void API001_listCommentsTest() {
        // -------------------------------------------------------------------------------
        // check status code
        Response response = given().log().all().when().get("/comments");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        // or
        response.then().assertThat().statusCode(200);

        //----------------------------------------------------------------------------------
        long timeValue =  response.then().contentType(ContentType.JSON).extract().time();
        System.out.println("Service time: " + timeValue);

        System.out.println(
                """
                Headers:
                --------
                """ + response.then().extract().headers().toString());

        // get json path from https://jsonpath.com/
        // we remove head element ($.) from json path as if RestAssured puts it by default
        String body = response.then().extract().body().path("[0].body");
        System.out.println(body);
    }

    @Test(groups = {"api", "smoke", "regression"})
    public void API002_getCommentTest() {
        Response response = given()
                .when()
                .get("/comments/1");

        response.then().assertThat().statusCode(200);
    }

    @Test(groups = {"api", "smoke", "regression"})
    public void API003_addCommentTest() {
        Response response = given()
                .body("{\"id\": 2,\"body\": \"some comment 2\",\"postId\": 1}")
                .when()
                .post("/comments");

        response.then().assertThat().statusCode(201);
        String body = response.then().extract().path("body");
        System.out.println(body);
    }

    @Test(groups = {"api", "smoke", "regression"})
    public void API004_updateWholeCommentTest() {
        Response response = given()
                .body("""
                        {
                            "id": 1,
                            "body": "some comment 1",
                            "postId": 1
                        }
                        """)
                .when()
                .put("/comments/1");

        response.then().assertThat().statusCode(200);
        String body = response.then().extract().path("body");
        System.out.println(body);
    }
}
