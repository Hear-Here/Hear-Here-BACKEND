package com.sw.hearhere.domain.repository;


import com.sw.hearhere.domain.entity.Post;
import com.sw.hearhere.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findAllByUser(User user);
}
