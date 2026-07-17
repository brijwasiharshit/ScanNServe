package com.app.ScanNServe.controller;


import com.app.ScanNServe.dto.response.CustomerMenuResponseDTO;
import com.app.ScanNServe.dto.response.FoodItemResponseDTO;
import com.app.ScanNServe.dto.response.WifiResponseDTO;
import com.app.ScanNServe.utils.api.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserController {

    ResponseEntity<StandardResponse<CustomerMenuResponseDTO>> getMenu(
            @PathVariable String tableToken
    );
}
