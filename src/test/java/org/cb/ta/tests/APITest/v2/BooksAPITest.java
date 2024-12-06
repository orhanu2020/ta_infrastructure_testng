package org.cb.ta.tests.APITest.v2;


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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class BooksAPITest {
    ConfigurationManager configurationManager;
    {
        configurationManager = ConfigurationManager.getInstance();
    }

    public BooksAPITest() {}

    @Test(groups = { "api"})
    public void BooksAPI_001_v2_getBooksValidateOnlyStatus() throws IOException {
        // validate only status
        ValidatableResponse validatableResponse = Helpers.generateRequest(
                configurationManager.getGetBooksResource(),
                HttpMethod.GET, true)
                .assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(groups = { "api"})
    public void BooksAPI_002_v2_getBooksValidateOnlyHeader() throws IOException {
        // validate only status
        ValidatableResponse validatableResponse = Helpers.generateRequest(
                configurationManager.getGetBooksResource(),
                HttpMethod.GET, true);

        validatableResponse
                .headers("Server", equalTo("nginx/1.17.10 (Ubuntu)"));
    }

    @Test(groups = { "api"})
    public void BooksAPI_003_v2_getBooksWithLogging() throws IOException {
        Helpers.generateRequest(
                configurationManager.getGetBooksResource(),
                HttpMethod.GET, true);
    }

    @Test(groups = { "api"})
    public void BooksAPI_004_v2_getBooksValidateResult() throws IOException {
        //one way: extract response string
        ValidatableResponse validatableResponse =
                Helpers.generateRequest(
                        configurationManager.getGetBooksResource(),
                        HttpMethod.GET, true);

        Response response = validatableResponse.extract().response();
        // validate result
        ArrayList<Book> bookList2 =
                (ArrayList<Book>) Helpers.getDataFromPath(response, "books");
        assertEquals(bookList2.size(), 8);
    }

}
