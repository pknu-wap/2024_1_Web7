package com.dogventure.dogweb.mainLogic.entity;

import com.dogventure.dogweb.constant.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String oauth2Key;

    @Column(unique = true)
    private String email;

    private String password;

    private UserType authority;

    private String description;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany
    @JoinColumn(name = "place_id")
    private List<Place> bookmarkPlaces = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

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
