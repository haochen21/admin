package com.km086.admin.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.km086.admin.model.security.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUser implements UserDetails {

    private final Long id;

    private final String username;

    private final String password;

    private final Profile profile;

    private final Collection<? extends GrantedAuthority> authorities;

    public AppUser(Long id, String username, String password, Profile profile, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
