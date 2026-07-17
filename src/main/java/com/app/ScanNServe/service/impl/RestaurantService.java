package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.domain.repository.IRestaurantRepository;
import com.app.ScanNServe.domain.repository.IUserRespository;
import com.app.ScanNServe.dto.request.RestaurantRequestDTO;
import com.app.ScanNServe.dto.response.RestaurantResponseDTO;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.exception.ResourseAlreadyExistsException;
import com.app.ScanNServe.service.IRestaurantService;
import com.app.ScanNServe.transformer.RestaurantTransformer;
import com.app.ScanNServe.utils.jwt.UserPrincipal;
import com.app.ScanNServe.utils.security.SecurityContextUtil;
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
