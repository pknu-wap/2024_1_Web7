package com.dogventure.dogweb.mainLogic.entity;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
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

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    private LocalTime startTime;

    private LocalTime endTime;

    private String address;

    private String phoneNumber;

    private String detailContent;

    private DogSize dogSize;

    private PlaceType placeType;

    private Double rate;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

//    public Place(String name, Double x, Double y, Image image, String detailContent, DogSize dogSize, PlaceType placeType, Double rate) {
//        this.name = name;
//        this.x = x;
//        this.y = y;
//        this.image = image;
//        this.detailContent = detailContent;
//        this.dogSize = dogSize;
//        this.placeType = placeType;
//        this.rate = rate;
//    }
}
