package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.domain.repository.IRestaurantRepository;
import com.app.ScanNServe.domain.repository.IUserRespository;
import com.app.ScanNServe.dto.request.AdminRequestDTO;
import com.app.ScanNServe.dto.request.UserRequestDTO;
import com.app.ScanNServe.dto.response.AdminResponseDTO;
import com.app.ScanNServe.dto.response.UserResponseDTO;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.exception.ResourseAlreadyExistsException;
import com.app.ScanNServe.service.IUserService;
import com.app.ScanNServe.transformer.UserTransformer;
import com.app.ScanNServe.utils.enums.Role;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService  implements IUserService {

    private final IRestaurantRepository restaurantRepository;
    private final IUserRespository userRepository;
    private final UserTransformer userTransformer;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public AdminResponseDTO createAdmin(
            AdminRequestDTO requestDTO
    ) {

        if (userRepository.findByEmailAddress(
                requestDTO.getEmailAddress()
        ).isPresent()) {

            throw new ResourseAlreadyExistsException(
                    "Email already registered."
            );
        }

        RestaurantEntity restaurant =
                restaurantRepository.findById(requestDTO.getRestaurantId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Restaurant not found."
                                ));

        UserEntity user =
                userTransformer.toEntity(
                        requestDTO,
                        restaurant
                );

        user.setRole(Role.ADMIN);
        user.setHashedPassword(
                passwordEncoder.encode(
                        requestDTO.getPassword()
                )
        );

        UserEntity saved =
                userRepository.save(user);

        return userTransformer.toDto(saved);
    }
}
