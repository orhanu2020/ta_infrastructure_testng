package org.cb.ta.tests.APITest.utils;

import io.restassured.RestAssured;
import lombok.Getter;
import org.cb.ta.tests.APITest.utils.entity.Account;

@Getter
public class ConfigurationManager {
    private static ConfigurationManager instance;

    public static synchronized ConfigurationManager getInstance() {
        if(instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    private String getBooksResource;
    private String loginResource;
    private String addBookToAccountResource;
    private String deleteBookFromAccountResource;
    private String deleteAllBooksFromAccountResource;

    private Account account;

    private ConfigurationManager() {
        load();
    }

    private void load() {
        RestAssured.baseURI = "https://demoqa.com";
        getBooksResource = "/BookStore/v1/Books";
        loginResource = "Account/v1/Login";
        addBookToAccountResource = "/BookStore/v1/Books";
        deleteBookFromAccountResource = "/BookStore/v1/Book";
        deleteAllBooksFromAccountResource = "/BookStore/v1/Books";

        account = new Account(""
                , "coding_book"
                , "CodingBook!1"
                , "");
    }
}
