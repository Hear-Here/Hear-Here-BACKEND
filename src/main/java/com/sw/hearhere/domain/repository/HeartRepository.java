package com.sw.hearhere.domain.repository;


import com.sw.hearhere.domain.entity.Heart;
import com.sw.hearhere.domain.entity.Post;
import com.sw.hearhere.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart,Long> {

    Heart findByPostAndUser(Post post, User user);


    List<Heart> findAllByUser(User user);

    boolean existsByPostAndUser(Post post, User user);
}
