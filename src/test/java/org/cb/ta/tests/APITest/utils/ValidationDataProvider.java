package org.cb.ta.tests.APITest.utils;

import org.cb.ta.tests.APITest.utils.entity.Book;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValidationDataProvider {
    public static Map<String, Book> booksAPIDataProvider() {
        Map<String, Book> validData = new HashMap<>();
        String isbn = "9781449325862";
        String title = "Git Pocket Guide";
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        validData.put(isbn, book);
        return validData;
    }
}
