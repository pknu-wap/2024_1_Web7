package com.dogventure.dogweb.mainLogic.entity;

import com.dogventure.dogweb.constant.DogSize;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Double rate;
}
