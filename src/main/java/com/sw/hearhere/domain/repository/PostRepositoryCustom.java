package com.sw.hearhere.domain.repository;

import com.sw.hearhere.domain.entity.Post;

import java.util.List;
import java.util.Map;

public interface PostRepositoryCustom {
    List<Post> findAllPostIn500mWithFilter(Double latitude, Double longitude, Map<String, String> filterMap);
}
