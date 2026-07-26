package com.app.namasteqr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportResponseDTO {
    private Long ordersThisMonth;
    private BigDecimal totalSalesAllTime;
    private BigDecimal totalSalesThisMonth;
    private Long ordersThisWeek;
    private List<DailySalesDTO> salesLast7Days;
    private List<HourlySalesDTO> peakSalesHours;
}
