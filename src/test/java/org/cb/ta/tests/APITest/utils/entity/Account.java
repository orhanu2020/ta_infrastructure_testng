package org.cb.ta.tests.APITest.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Account {
    private String userId;
    private String userName;
    private String password;
    private String token;
}
