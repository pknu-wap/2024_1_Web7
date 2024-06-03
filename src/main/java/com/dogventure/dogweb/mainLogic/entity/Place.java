package com.dogventure.dogweb.mainLogic.entity;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double x;

    private Double y;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "place_id")
    private List<Image> images;

    private LocalTime startTime;

    private LocalTime endTime;

    private String address;

    private String phoneNumber;

    @Lob
    private String detailContent;

    private DogSize dogSize;

    private PlaceType placeType;

    @Setter
    private Double rate;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

    public Place(String name, Double x, Double y, List<Image> images, LocalTime startTime, LocalTime endTime, String address, String phoneNumber, String detailContent, DogSize dogSize, PlaceType placeType, Double rate) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.images = images;
        this.detailContent = detailContent;
        this.dogSize = dogSize;
        this.placeType = placeType;
        this.rate = rate;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}
