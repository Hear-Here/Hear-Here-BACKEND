package com.sw.hearhere.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_id")
    private Long id;

    private Long songId;

    private String title;

    private String artist;

    private String cover;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @Builder
    public Music(String title, String artist, String cover, Long songId) {
        this.title = title;
        this.artist = artist;
        this.cover = cover;
        this.songId = songId;
    }
}
