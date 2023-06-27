package com.sw.hearhere.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Locale;


public enum GenreType {
    BALLAD("발라드"),
    DANCE("댄스"),
    HIPHOP("랩/힙합"),
    RB("R&B"),
    INDIE("인디"),
    POP("팝"),
    BAND("밴드"),
    TROT("트로트");

    @Getter
    private String kr;

    private GenreType(String kr){
        this.kr = kr;
    }

    @JsonCreator
    public static GenreType from(String s){
        return GenreType.valueOf(s.toUpperCase(Locale.ROOT));
    }
}
