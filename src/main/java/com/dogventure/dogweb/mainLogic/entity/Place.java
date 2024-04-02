package com.dogventure.dogweb.mainLogic.entity;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double x;

    private Double y;

    private String image;

    private String detailContent;

    private DogSize dogSize;

    private PlaceType placeType;

    private Double rate;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

}
