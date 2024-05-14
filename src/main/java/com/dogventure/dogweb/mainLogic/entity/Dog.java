package com.dogventure.dogweb.mainLogic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dogName;

    private String species;

    private String gender;

    private String registrationNumber;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Dog(String dogName, String species, String gender, String registrationNumber, Image image) {
        this.dogName = dogName;
        this.species = species;
        this.gender = gender;
        this.registrationNumber = registrationNumber;
        this.image = image;
    }
}
