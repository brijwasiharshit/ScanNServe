package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.UserEntity;
import com.app.namasteqr.domain.repository.IRestaurantRepository;
import com.app.namasteqr.domain.repository.IUserRespository;
import com.app.namasteqr.dto.request.RestaurantRequestDTO;
import com.app.namasteqr.dto.response.RestaurantResponseDTO;
import com.app.namasteqr.exception.ResourceNotFoundException;
import com.app.namasteqr.exception.ResourseAlreadyExistsException;
import com.app.namasteqr.service.IRestaurantService;
import com.app.namasteqr.transformer.RestaurantTransformer;
import com.app.namasteqr.utils.jwt.UserPrincipal;
import com.app.namasteqr.utils.security.SecurityContextUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

   private final RestaurantTransformer restaurantTransformer;
   private final IRestaurantRepository restaurantRepository;
    private final SecurityContextUtil securityContextUtil;
    private final IUserRespository userRepository;

    @Override
    @Transactional
    public RestaurantResponseDTO createRestaurant(
            RestaurantRequestDTO requestDTO
    ) {

        String normalizedName = requestDTO.getName()
                .trim()
                .replaceAll("\\s+", " ");

        if (restaurantRepository.existsByNameIgnoreCaseAndIsDeletedFalse(
                normalizedName
        )) {

            throw new ResourseAlreadyExistsException(
                    "Restaurant already exists."
            );
        }

        RestaurantEntity entity =
                restaurantTransformer.toEntity(
                        requestDTO,
                        normalizedName
                );

        RestaurantEntity saved =
                restaurantRepository.save(entity);

        return restaurantTransformer.toDto(saved);
    }

    @Override
    @Transactional
    public RestaurantResponseDTO getRestaurant() {

        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();

        UserEntity user = userRepository
                .findById(activeUser.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        RestaurantEntity restaurant = restaurantRepository
                .findByRestaurantIdAndIsDeletedFalse(
                        user.getRestaurant().getRestaurantId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found."));

        return restaurantTransformer.toDto(restaurant);
    }
}
