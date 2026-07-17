package com.app.ScanNServe.utils.validations;

import com.app.ScanNServe.domain.entity.RestaurantEntity;
import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.exception.ResourceNotFoundException;
import com.app.ScanNServe.exception.SubscriptionExpiredException;
import com.app.ScanNServe.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionValidator {

    public void validate(UserEntity user) {

        if (user.getRole() == Role.SUPER_ADMIN) {
            return;
        }

        RestaurantEntity restaurant = user.getRestaurant();

        if (restaurant == null) {
            throw new ResourceNotFoundException(
                    "Restaurant not assigned."
            );
        }

        if (!restaurant.getIsActive()) {
            throw new SubscriptionExpiredException(
                    "Restaurant is inactive."
            );
        }

        if (restaurant.getSubscriptionExpiry()
                .isBefore(LocalDateTime.now())) {

            throw new SubscriptionExpiredException(
                    "Restaurant subscription has expired."
            );
        }
    }
}