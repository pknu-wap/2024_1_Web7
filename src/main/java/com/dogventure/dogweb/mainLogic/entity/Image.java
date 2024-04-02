package com.dogventure.dogweb.mainLogic.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private byte[] data;

    public Image(String filename, byte[] data) {
        this.filename = filename;
        this.data = data;
    }
}
