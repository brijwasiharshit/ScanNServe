package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.domain.repository.IRestaurantRepository;
import com.app.namasteqr.domain.repository.IUserRespository;
import com.app.namasteqr.dto.request.AdminRequestDTO;
import com.app.namasteqr.dto.request.UserRequestDTO;
import com.app.namasteqr.dto.response.AdminResponseDTO;
import com.app.namasteqr.dto.response.UserResponseDTO;
import com.app.namasteqr.exception.ResourceNotFoundException;
import com.app.namasteqr.exception.ResourseAlreadyExistsException;
import com.app.namasteqr.service.IUserService;
import com.app.namasteqr.transformer.UserTransformer;
import com.app.namasteqr.utils.enums.Role;
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

    @Override
    public java.util.List<AdminResponseDTO> getAllAdmins() {
        return userRepository.findAllByRole(Role.ADMIN)
                .stream()
                .map(userTransformer::toDto)
                .collect(java.util.stream.Collectors.toList());
    }
}
