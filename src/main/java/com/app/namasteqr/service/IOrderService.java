package com.app.namasteqr.service;

import com.app.namasteqr.dto.request.OrderItemRequestDTO;
import com.app.namasteqr.dto.response.OrderResponseDTO;

import com.app.namasteqr.dto.response.SalesReportResponseDTO;

import java.util.List;

public interface IOrderService {
    OrderResponseDTO placeOrder(String tableToken, List<OrderItemRequestDTO> items);
    SalesReportResponseDTO getSalesReport();
}
