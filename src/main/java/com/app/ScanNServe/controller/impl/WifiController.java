package com.app.ScanNServe.controller.impl;

import com.app.ScanNServe.controller.IWifiController;
import com.app.ScanNServe.dto.request.WifiRequestDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import com.app.ScanNServe.service.IWifiService;
import com.app.ScanNServe.utils.api.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wifi")
@AllArgsConstructor
public class WifiController implements IWifiController {

    private final IWifiService wifiService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<StandardResponse<WifiResponseDTO>> createWifi(@RequestBody WifiRequestDTO wifiRequestDTO) {
        System.out.println("Inside create Wifi by Super Admin!");
        WifiResponseDTO wifiResponseDTO = wifiService.createWifi(wifiRequestDTO);

        StandardResponse<WifiResponseDTO> response =
                StandardResponse.<WifiResponseDTO>builder()
                        .data(wifiResponseDTO)
                        .success(true)
                        .message("Wifi created successfully")
                        .errors(null)
                        .httpStatus(HttpStatus.CREATED)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/details")
    @Override
    public ResponseEntity<StandardResponse<WifiResponseDTO>> getWifiDetails(@RequestBody WifiRequestDTO wifiRequestDTO) {

        WifiResponseDTO wifiResponseDTO = wifiService.getWifiDetails(wifiRequestDTO);

        StandardResponse<WifiResponseDTO> response =
                StandardResponse.<WifiResponseDTO>builder()
                        .data(wifiResponseDTO)
                        .success(true)
                        .message("Wifi details fetched successfully")
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/by-property")
    @Override
    public ResponseEntity<StandardResponse<List<WifiResponseDTO>>> getWifiByProperty(
            @RequestBody WifiRequestDTO wifiRequestDTO
    ) {
        List<WifiResponseDTO> wifiResponseDTOS = wifiService.getWifiByProperty(wifiRequestDTO);

        StandardResponse<List<WifiResponseDTO>> response =
                StandardResponse.<List<WifiResponseDTO>>builder()
                        .data(wifiResponseDTOS)
                        .success(true)
                        .message("Wifi list fetched successfully")
                        .errors(null)
                        .httpStatus(HttpStatus.OK)
                        .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

