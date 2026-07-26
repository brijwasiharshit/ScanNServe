package com.app.namasteqr.service.impl;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.RestaurantTableEntity;
import com.app.namasteqr.domain.repository.IRestaurantRepository;
import com.app.namasteqr.domain.repository.IRestaurantTableRepository;
import com.app.namasteqr.dto.request.RestaurantTableRequestDTO;
import com.app.namasteqr.dto.response.RestaurantTableResponseDTO;
import com.app.namasteqr.exception.ResourceNotFoundException;
import com.app.namasteqr.exception.ResourseAlreadyExistsException;
import com.app.namasteqr.service.IRestaurantService;
import com.app.namasteqr.service.IRestaurantTableService;
import com.app.namasteqr.transformer.RestaurantTableTransformer;
import com.app.namasteqr.utils.jwt.UserPrincipal;
import com.app.namasteqr.utils.security.SecurityContextUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantTableService implements IRestaurantTableService {

    private final SecurityContextUtil securityContextUtil;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantTableRepository restaurantTableRepository;
    private final RestaurantTableTransformer restaurantTableTransformer;
    @Override
    @Transactional
    public RestaurantTableResponseDTO createTable(
            RestaurantTableRequestDTO requestDTO
    ) {



        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();

        RestaurantEntity restaurant =
                restaurantRepository
                        .findByRestaurantIdAndIsDeletedFalse(
                                activeUser.getRestaurantId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Restaurant not found."
                                ));

        String normalizedTableNumber =
                requestDTO.getTableNumber()
                        .trim()
                        .replaceAll("\\s+", " ");

        if (restaurantTableRepository
                .existsByRestaurantAndTableNumberIgnoreCaseAndIsDeletedFalse(
                        restaurant,
                        normalizedTableNumber
                )) {

            throw new ResourseAlreadyExistsException(
                    "Table number already exists."
            );
        }

        RestaurantTableEntity entity =
                restaurantTableTransformer.toEntity(
                        restaurant,
                        normalizedTableNumber
                );

        RestaurantTableEntity saved =
                restaurantTableRepository.save(entity);

        return restaurantTableTransformer.toDto(saved);
    }

    @Override
    @Transactional
    public List<RestaurantTableResponseDTO> getTables() {

        UserPrincipal activeUser =
                securityContextUtil.fetchActiveUserDetails();

        RestaurantEntity restaurant =
                restaurantRepository
                        .findByRestaurantIdAndIsDeletedFalse(
                                activeUser.getRestaurantId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Restaurant not found."
                                ));

        List<RestaurantTableEntity> tables =
                restaurantTableRepository
                        .findAllByRestaurantAndIsDeletedFalseOrderByTableNumberAsc(
                                restaurant
                        );

        return tables.stream()
                .map(restaurantTableTransformer::toDto)
                .toList();
    }
}
