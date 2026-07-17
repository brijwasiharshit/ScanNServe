package com.app.ScanNServe.utils.jwt;

import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.utils.enums.Role;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;
    private String emailAddress;
    private Role role;
    private Long restaurantId;
    private String password;

    public static UserPrincipal fromEntity(UserEntity user) {
        return UserPrincipal.builder()
                .id(user.getId())
                .restaurantId(
                        user.getRestaurant() != null
                                ? user.getRestaurant().getRestaurantId()
                                : null
                )
                .username(user.getUsername())
                .emailAddress(user.getEmailAddress())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }

    public static UserPrincipal fromJwtClaims(Claims claims) {
        return UserPrincipal.builder()
                .id(claims.get("userId", Long.class))
                .restaurantId(claims.get("restaurantId", Long.class))
                .username(claims.get("username", String.class))
                .emailAddress(claims.getSubject())
                .role(Role.valueOf(claims.get("role", String.class)))
                .password("")
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        authorities.addAll(
                role.getPermissions()
                        .stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.name()))
                        .collect(Collectors.toSet())
        );

        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password != null ? password : "";
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

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
        return true;
    }
}
