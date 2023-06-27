package com.sw.hearhere.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Locale;

public enum WeatherType {
    SUNNY("맑음"),
    NORMAL("보통"),
    CLOUDY("흐림"),
    RAINY("비"),
    WINDY("바람"),
    SNOWY("눈")
    ;
    @Getter
    private String kr;

    private WeatherType(String kr){
        this.kr = kr;
    }

    @JsonCreator
    public static WeatherType from(String s){
        return WeatherType.valueOf(s.toUpperCase(Locale.ROOT));
    }

}
