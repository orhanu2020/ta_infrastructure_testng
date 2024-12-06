package org.cb.ta.tests.APITest.v1;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.cb.ta.tests.APITest.utils.ConfigurationManager;
import org.cb.ta.tests.APITest.utils.entity.Account;
import org.cb.ta.tests.APITest.utils.entity.AddBook;
import org.cb.ta.tests.APITest.utils.entity.DeleteBook;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertNotNull;

public class AccountAPITest {

    ConfigurationManager configurationManager;

    public AccountAPITest() {
        configurationManager = ConfigurationManager.getInstance();
    }

    @Test(groups = { "api"})
    public void API0001_login() {
        String responseString = given()
                .header("Content-Type", "application/json; charset=utf-8")
                .log()
                .all()
                .body(configurationManager.getAccount())
                .when()
                .post(configurationManager.getLoginResource())
                .then()
                .log()
                .all()
                .extract()
                .response()
                .asString();

        JsonPath jsonPath = new JsonPath(responseString);
        String userId = jsonPath.getString("userId");
        assertNotNull(userId);
        String token = jsonPath.getString("token");
        assertNotNull(token);

        configurationManager.getAccount().setUserId(userId);
        configurationManager.getAccount().setToken(token);
    }

    @Test(groups = { "api"}, dependsOnMethods = {"API0001_login"})
    public void API0010_deleteAllAccountBooks() {
        given()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", "Bearer " + configurationManager.getAccount().getToken())
                .queryParam("UserId", configurationManager.getAccount().getUserId())
                .log()
                .all()
                .when()
                .delete(configurationManager.getDeleteAllBooksFromAccountResource())
                .then()
                .log()
                .all()
                .extract()
                .response()
                .asString();
    }

    @Test(groups = { "api"}, dependsOnMethods = {"API0010_deleteAllAccountBooks"})
    public void API0020_addBookToAccount() {
        API0001_login();
        AddBook addBook = new AddBook(configurationManager.getAccount().getUserId());
        addBook.addIsbn("9781449337711");
        String responseString = given()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", "Bearer " + configurationManager.getAccount().getToken())
                .log()
                .all()
                .body(addBook)
                .when()
                .post(configurationManager.getAddBookToAccountResource())
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response()
                .asString();

        // you can check the code in result json
    }

    @Test(groups = { "api"}, dependsOnMethods = {"API0020_addBookToAccount"})
    public void API0030_deleteBookFromAccount() {
        API0001_login();
        DeleteBook deleteBook = new DeleteBook(configurationManager.getAccount().getUserId(), "9781449337711");
        String responseString = given()
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Authorization", "Bearer " + configurationManager.getAccount().getToken())
                .log()
                .all()
                .body(deleteBook)
                .when()
                .delete(configurationManager.getDeleteBookFromAccountResource())
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .response()
                .asString();
    }
}
