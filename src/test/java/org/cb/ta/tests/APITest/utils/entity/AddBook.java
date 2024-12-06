package org.cb.ta.tests.APITest.utils.entity;

import com.beust.ah.A;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;


@Getter
@Setter
public class AddBook {
    private String userId;
    private ArrayList<AccountBook> collectionOfIsbns;

    public AddBook(String userId) {
        this.userId = userId;
        collectionOfIsbns = new ArrayList<>();
    }

    public void addIsbn(String isbn) {
        AccountBook accountBook = new AccountBook(isbn);
        collectionOfIsbns.add(accountBook);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public class AccountBook {
        private String isbn;
    }

}
