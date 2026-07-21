package com.app.ScanNServe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourlySalesDTO {
    private Integer hour;
    private Long orders;
    private BigDecimal sales;
}
