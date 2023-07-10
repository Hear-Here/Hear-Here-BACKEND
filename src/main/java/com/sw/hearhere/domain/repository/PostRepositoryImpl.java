package com.sw.hearhere.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sw.hearhere.domain.dto.FilterDto;
import com.sw.hearhere.domain.entity.Post;
import com.sw.hearhere.domain.entity.QPost;
import com.sw.hearhere.domain.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QPost post = QPost.post;

    @Override
    public List<Post> findAllPostIn500mWithFilter(Double latitude, Double longitude, Map<String, String> filterMap) {
        FilterDto filterDto = setFilterDto(filterMap);
        return queryFactory.selectFrom(post)
                .where(
                    inRange500m(latitude, longitude)
                        , findAllByGenreType(filterDto.getGenreTypeList())
                        , findAllByWeatherType(filterDto.getWeatherTypeList())
                        , findAllByWithType(filterDto.getWithTypeList())
                        , findAllByEmotionType(filterDto.getEmotionTypeList())
                )
                .orderBy(post.id.desc())
                .fetch();
    }

    private FilterDto setFilterDto(Map<String, String> filterMap){
        FilterDto filterDto = new FilterDto();
        if(filterMap.get("genreType")!=null){
            List<GenreType> genreTypeList = new ArrayList<>();
            for (String s : filterMap.get("genreType").split(",")) {
                genreTypeList.add(GenreType.from(s));
            }
            filterDto.setGenreTypeList(genreTypeList);
        }
        if(filterMap.get("withType")!=null){
            List<WithType> withTypeList = new ArrayList<>();
            for (String s : filterMap.get("withType").split(",")) {
                withTypeList.add(WithType.from(s));
            }
            filterDto.setWithTypeList(withTypeList);
        }
        if(filterMap.get("emotionType")!=null){
            List<EmotionType> emotionTypeList = new ArrayList<>();
            for (String s : filterMap.get("emotionType").split(",")) {
                emotionTypeList.add(EmotionType.from(s));
            }
            filterDto.setEmotionTypeList(emotionTypeList);
        }
        if(filterMap.get("weatherType")!=null){
            List<WeatherType> weatherTypeList = new ArrayList<>();
            for (String s : filterMap.get("weatherType").split(",")) {
                weatherTypeList.add(WeatherType.from(s));
            }
            filterDto.setWeatherTypeList(weatherTypeList);
        }
        return filterDto;
    }

    //mvp에서는 500m 고정
    private BooleanExpression inRange500m(Double latitude, Double longitude){
        if(latitude==null || longitude==null){
            return null;
        }
        return post.latitude.between(latitude-0.0045, latitude+0.0045)
                .and(post.longitude.between(longitude-0.0045, longitude+0.0045));
    }

    private BooleanExpression findAllByGenreType(List<GenreType> genreTypeList){
        if(genreTypeList == null){
            return null;
        }
        return post.genreType.in(genreTypeList);
    }

    private BooleanExpression findAllByEmotionType(List<EmotionType> emotionTypeList){
        if(emotionTypeList == null){
            return null;
        }
        return post.emotionType.in(emotionTypeList);
    }

    private BooleanExpression findAllByWeatherType(List<WeatherType> weatherTypeList){
        if(weatherTypeList == null){
            return null;
        }
        return post.weatherType.in(weatherTypeList);
    }

    private BooleanExpression findAllByWithType(List<WithType> withTypeList){
        if(withTypeList == null){
            return null;
        }
        return post.withType.in(withTypeList);
    }
}
