package org.cb.ta.tests.APITest.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class DeleteBook {
    private String userId;
    private String isbn;
}
