package org.cb.ta.tests.APITest.v3;


import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.cb.ta.tests.APITest.utils.ConfigurationManager;
import org.cb.ta.tests.APITest.utils.Helpers;
import org.cb.ta.tests.APITest.utils.ValidationDataProvider;
import org.cb.ta.tests.APITest.utils.entity.Book;
import org.cb.ta.tests.APITest.utils.entity.Books;
import org.openqa.selenium.remote.http.HttpMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class BooksAPITest {
    ConfigurationManager configurationManager;
    {
        configurationManager = ConfigurationManager.getInstance();
    }

    public BooksAPITest() {}


    @Test(groups = { "api"}, dataProvider = "booksAPIConsistencyTest")
    public void BooksAPI_001_v3_getBooksValidate() throws IOException {
        ValidatableResponse validatableResponse =
                Helpers.generateRequest(
                        configurationManager.getGetBooksResource(),
                        HttpMethod.GET, true);

        validatableResponse
                .assertThat().statusCode(HttpStatus.SC_OK)
                .headers("Server", equalTo("nginx/1.17.10 (Ubuntu)"));

        Response response = validatableResponse.extract().response();
        Books books = response.as(Books.class);

        assertEquals(books.getBooks().size(), 8);

        //Validate book data
        // check titles
        Map<String, Book> expectedData = ValidationDataProvider.booksAPIDataProvider();
        for(Book expectedBook : expectedData.values()) {
            List<Book> actualBookData = books.getBooks().stream()
                    .filter((Book book) -> book.getIsbn().equals(expectedBook.getIsbn()))
                    .collect(Collectors.toList());
            assertNotNull(actualBookData);
            assertEquals(actualBookData.size(), 1);

            Book actualBook = actualBookData.get(0);
            assertEquals(actualBook.getTitle(), expectedBook.getTitle());
        }
    }

    @DataProvider(name = "booksAPIConsistencyTest")
    public Object[][] booksAPIConsistencyTest() {
        return new Object [][]{{},{},{},{},{}};
    }
}
