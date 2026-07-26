package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.domain.repository.IUserRespository;
import com.app.namasteqr.utils.jwt.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final IUserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRespository
                .findByEmailAddress(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return UserPrincipal.fromEntity(user);
    }
}
