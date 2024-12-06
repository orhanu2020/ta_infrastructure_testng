package org.cb.ta.tests.APITest.v1;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.cb.ta.tests.APITest.utils.ConfigurationManager;
import org.cb.ta.tests.APITest.utils.Helpers;
import org.cb.ta.tests.APITest.utils.entity.Book;
import org.cb.ta.tests.APITest.utils.entity.Books;
import org.openqa.selenium.remote.http.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.*;

public class BooksAPITest {
    ConfigurationManager configurationManager;
    {
        configurationManager = ConfigurationManager.getInstance();
    }

    public BooksAPITest() {}

    @Test(groups = { "api"})
    public void BooksAPI_001_v1_getBooksValidateOnlyStatus() {
        // validate only status
        given()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK);

        // alternatively
        Response response = given()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .extract().response();
        assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }

    @Test(groups = { "api"})
    public void BooksAPI_002_v1_getBooksValidateOnlyHeader() {
        // validate only status
        given()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .headers("Server", equalTo("nginx/1.17.10 (Ubuntu)"));

        // alternatively
        Response response = given()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .extract().response();
        assertEquals(response.header("Server"), "nginx/1.17.10 (Ubuntu)");
    }

    @Test(groups = { "api"})
    public void BooksAPI_003_v1_getBooksWithLogging() {
        Response response = given()
                .log()
                .all()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .log()
                .all()
                .extract()
                .response();
    }

    @Test(groups = { "api"})
    public void BooksAPI_004_v1_getBooksValidateResultWithJSonPath() throws IOException {
        //one way: extract response string
        String responseJson = given()
                .log()
                .all()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .log()
                .all()
                .extract()
                .response().asString();
        JsonPath jsonPath = new JsonPath(responseJson);
        List<Book> bookList = jsonPath.get("books");
        assertEquals(bookList.size(), 8);

        //another way: extract response size
        String responseJson2 = given()
                .log()
                .all()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .log()
                .all()
                .extract()
                .response().asString();
        JsonPath jsonPath2 = new JsonPath(responseJson2);
        int bookSize = jsonPath2.get("books.size()");
        assertEquals(bookSize, 8);

        // another way
        Response response = given()
                .log()
                .all()
                .when()
                .get(configurationManager.getGetBooksResource())
                .then()
                .log()
                .all()
                .extract()
                .response();


        // validate result
        List<Book> bookList2 =
                (List<Book>) Helpers.getDataFromPath(response, "books");
        assertEquals(bookList2.size(), 8);
    }

}
