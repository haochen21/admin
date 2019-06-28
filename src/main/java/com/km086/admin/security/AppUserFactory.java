package com.km086.admin.security;

import com.km086.admin.model.security.Profile;
import com.km086.admin.model.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class AppUserFactory {

    public static final String ROLE_ADMIN = "ADMIN";

    public static final String ROLE_USER = "USER";

    private static Map<Profile, List<String>> authorities = new HashMap<>();

    static {
        authorities.put(Profile.ADMIN, Arrays.asList(ROLE_ADMIN,ROLE_USER));
        authorities.put(Profile.AGENT, Arrays.asList(ROLE_USER));
    }

    public static AppUser create(User user) {
        return new AppUser(
                user.getId(),
                user.getLoginName(),
                user.getPassword(),
                user.getProfile(),
                mapToGrantedAuthorities(user)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : authorities.get(user.getProfile())) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return grantedAuthorities;
    }
}
