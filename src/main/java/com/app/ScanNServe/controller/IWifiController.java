package com.app.ScanNServe.controller;

import com.app.ScanNServe.dto.request.WifiRequestDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IWifiController {

    ResponseEntity<StandardResponse<WifiResponseDTO>> createWifi(
            @RequestBody WifiRequestDTO wifiRequestDTO
    );

    ResponseEntity<StandardResponse<WifiResponseDTO>> getWifiDetails(
            @RequestBody WifiRequestDTO wifiRequestDTO
    );

    ResponseEntity<StandardResponse<List<WifiResponseDTO>>> getWifiByProperty(
            @RequestBody WifiRequestDTO wifiRequestDTO
    );
}

