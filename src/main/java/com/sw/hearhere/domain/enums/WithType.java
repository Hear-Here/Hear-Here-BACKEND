package com.sw.hearhere.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Locale;

public enum WithType implements SearchType{
    ALONE("나 혼자"),
    FRIEND("친구"),
    COUPLE("연인"),
    FAMILY("가족"),
    PET("반려동물"),
    SOMEBODY("누군가")
    ;

    @Getter
    private String kr;

    private WithType(String kr){
        this.kr = kr;
    }

    @JsonCreator
    public static WithType from(String s){
        return WithType.valueOf(s.toUpperCase(Locale.ROOT));
    }
}
