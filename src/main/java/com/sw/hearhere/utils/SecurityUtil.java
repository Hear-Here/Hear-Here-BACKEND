package com.sw.hearhere.utils;

import com.sw.hearhere.security.CustomUserDetails;
import com.sw.hearhere.security.UserPrincipalDto;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private SecurityUtil() {}

    public static UserPrincipalDto getLoginUserPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return toDto(principal);
        } else {
            return null;
        }
    }

    public static Authentication getLoginUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Long getLoginUserId() {
        UserPrincipalDto loginUser = getLoginUserPrincipal();
        return loginUser != null ? loginUser.getId() : null;
    }

    //현재 로그인한 사용자가 userId가 아니면 AuthorizationServiceException 을 던진다.
    public static void checkUser(Long userId) throws AuthorizationServiceException {
        if (!userId.equals(getLoginUserId())) {
            throw new AuthorizationServiceException("UserId: " + userId + " 는 해당 영역에 접근할 수 없습니다.");
        }
    }

    private static UserPrincipalDto toDto(Object principal) {
        CustomUserDetails user = (CustomUserDetails) principal;

        return UserPrincipalDto.from(user);

    }

}
