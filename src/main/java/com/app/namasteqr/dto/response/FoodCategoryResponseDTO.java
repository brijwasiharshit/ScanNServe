package com.app.namasteqr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodCategoryResponseDTO {

    private Long categoryId;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
