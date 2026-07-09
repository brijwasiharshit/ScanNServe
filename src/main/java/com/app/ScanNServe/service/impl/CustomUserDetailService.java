package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.domain.repository.IUserRespository;
import com.app.ScanNServe.utils.jwt.UserPrincipal;
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
