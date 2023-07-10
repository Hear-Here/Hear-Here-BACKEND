package com.sw.hearhere.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum EmotionType implements SearchType{
    SMILE,
    SOSO,
    SAD,
    FUNNY,
    HEART,
    ANGRY;

    @JsonCreator
    public static EmotionType from(String s){
        return EmotionType.valueOf(s.toUpperCase(Locale.ROOT));
    }
}
