package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.RestaurantTableEntity;
import com.app.ScanNServe.domain.repository.IRestaurantRepository;
import com.app.ScanNServe.domain.repository.IRestaurantTableRepository;
import com.app.ScanNServe.dto.request.RestaurantTableRequestDTO;
import com.app.ScanNServe.dto.response.RestaurantTableResponseDTO;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.exception.ResourseAlreadyExistsException;
import com.app.ScanNServe.service.IRestaurantService;
import com.app.ScanNServe.service.IRestaurantTableService;
import com.app.ScanNServe.transformer.RestaurantTableTransformer;
import com.app.ScanNServe.utils.jwt.UserPrincipal;
import com.app.ScanNServe.utils.security.SecurityContextUtil;
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
