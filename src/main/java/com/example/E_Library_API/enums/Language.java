package com.example.E_Library_API.enums;

import lombok.Getter;

@Getter
public enum Language {
    AZE("azərbaycanca"), ENG("ingiliscə"),
    SP("ispanca"), TUR("türkcə"),
    RUS("rusca"), FR("fransızca");

    private final String languageName;

    Language(String languageName) {
        this.languageName = languageName;
    }
}
