package com.sw.hearhere.domain.entity;

import com.sw.hearhere.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private Long providerId;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickname;

    public void updateNickname(String updateNickname) {
        this.nickname = updateNickname;
    }
}