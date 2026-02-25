package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.request.WifiRequestDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;

public interface IWifiService {

    WifiResponseDTO createWifi(WifiRequestDTO wifiRequestDTO);

    WifiResponseDTO getWifiDetails(WifiRequestDTO wifiRequestDTO);
}

