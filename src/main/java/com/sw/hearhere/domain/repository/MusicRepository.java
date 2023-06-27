package com.sw.hearhere.domain.repository;

import com.sw.hearhere.domain.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    public boolean existsByTitleAndArtist(String title, String artist);
    public Optional<Music> findByTitleAndArtist(String title, String artist);
}
