package com.seanglay.authservice.security.service;

import com.seanglay.authservice.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

    private UUID id;
    private String email;
    private String password;
    private boolean verified;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal build(User user) {
        return UserPrincipal.builder().id(user.getId())
                .email(user.getEmail()).password(user.getPassword()).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    } // login by email

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return verified;
    } // tie to isVerified
}