package com.sw.hearhere.domain.dto;

import com.sw.hearhere.domain.enums.EmotionType;
import com.sw.hearhere.domain.enums.GenreType;
import com.sw.hearhere.domain.enums.WeatherType;
import com.sw.hearhere.domain.enums.WithType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FilterDto {
    List<GenreType> genreTypeList;
    List<WeatherType> weatherTypeList;
    List<WithType> withTypeList;
    List<EmotionType> emotionTypeList;
}
