package com.sw.hearhere.security;

import com.sw.hearhere.domain.entity.User;
import com.sw.hearhere.domain.repository.UserRepository;
import com.sw.hearhere.response.BaseException;
import com.sw.hearhere.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("CustomUserDetailService loadUserByUsername username : "+ username);
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_FOUND_USER));
        return new CustomUserDetails(user);
    }

}
