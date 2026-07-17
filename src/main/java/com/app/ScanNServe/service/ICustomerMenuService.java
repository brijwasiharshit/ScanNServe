package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.response.CustomerMenuResponseDTO;

public interface ICustomerMenuService {
    CustomerMenuResponseDTO getMenu(
            String tableToken
    );
}
