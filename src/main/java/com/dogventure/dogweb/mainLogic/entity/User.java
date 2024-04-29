package com.dogventure.dogweb.mainLogic.entity;

import com.dogventure.dogweb.constant.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String oauth2Key;

    private String email;

    private String password;

    private UserType authority;

    public User(String username, String userId, String password, UserType authority) {
        this.username = username;
        this.email = userId;
        this.password = password;
        this.authority = authority;
    }

    // Oauth2 용도
    public User(String username, String oauth2Key, UserType authority, String email) {
        this.username = username;
        this.oauth2Key = oauth2Key;
        this.authority = authority;
        this.email = email;
    }
}
