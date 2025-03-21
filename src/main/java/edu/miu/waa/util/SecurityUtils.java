package edu.miu.waa.util;

import edu.miu.waa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private static UserService userService;

    @Autowired
    public SecurityUtils(UserService userService) {
        SecurityUtils.userService = userService;
    }
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return userService.findByUsername(authentication.getName()).getId();
        }
        return 0L;
    }
}