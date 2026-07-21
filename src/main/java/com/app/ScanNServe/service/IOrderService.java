package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.OrderItemRequestDTO;
import com.app.ScanNServe.dto.response.OrderResponseDTO;

import com.app.ScanNServe.dto.response.SalesReportResponseDTO;

import java.util.List;

public interface IOrderService {
    OrderResponseDTO placeOrder(String tableToken, List<OrderItemRequestDTO> items);
    SalesReportResponseDTO getSalesReport();
}
