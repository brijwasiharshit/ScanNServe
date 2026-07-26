package com.app.namasteqr.transformer;

import com.app.namasteqr.domain.entity.RestaurantEntity;
import com.app.namasteqr.domain.entity.RestaurantMenuItemEntity;
import com.app.namasteqr.domain.entity.RestaurantTableEntity;
import com.app.namasteqr.dto.response.CustomerMenuItemDTO;
import com.app.namasteqr.dto.response.CustomerMenuResponseDTO;
import com.app.namasteqr.dto.response.RestaurantDTO;
import com.app.namasteqr.dto.response.TableDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerMenuTransfomer {
    public CustomerMenuResponseDTO toDto(
            RestaurantEntity restaurant,
            RestaurantTableEntity table,
            List<RestaurantMenuItemEntity> menuItems
    ) {

        return CustomerMenuResponseDTO.builder()

                .restaurant(

                        RestaurantDTO.builder()
                                .name(restaurant.getName())
                                .logo(restaurant.getLogo())
                                .theme(restaurant.getThemeColor())
                                .description(restaurant.getDescription())
                                .address(restaurant.getAddress())
                                .phoneNumber(restaurant.getPhoneNumber())
                                .build()
                )

                .table(

                        TableDTO.builder()
                                .tableNumber(table.getTableNumber())
                                .build()
                )

                .menu(

                        menuItems.stream()
                                .map(item ->

                                        CustomerMenuItemDTO.builder()
                                                .itemId(item.getFoodItem().getItemId())
                                                .categoryId(item.getFoodItem().getCategory().getCategoryId())
                                                .categoryName(item.getFoodItem().getCategory().getName())
                                                .name(item.getFoodItem().getName())
                                                .foodType(item.getFoodItem().getFoodType())
                                                .price(item.getPrice())
                                                .image(
                                                        item.getCustomImage() != null
                                                                ? item.getCustomImage()
                                                                : item.getFoodItem().getDefaultImage()
                                                )
                                                .build()

                                )
                                .toList()

                )

                .build();
    }
}
