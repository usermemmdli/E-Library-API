package com.example.E_Library_API.enums;

import lombok.Getter;

@Getter
public enum Genre {
    FANTASY("Fantasktika"),
    HORROR("Qorxu"),
    HISTORICAL("Tarixi"),
    DETECTIVE("Detektiv"),
    ROMANCE("Roman"),
    THRILLER("Triller"),
    DRAMA("Dram"),
    SCIENCE_FICTION("Elmi-qurğu");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }
}
