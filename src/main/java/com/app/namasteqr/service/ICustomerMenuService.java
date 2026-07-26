package com.app.namasteqr.service;

import com.app.namasteqr.dto.response.CustomerMenuResponseDTO;

public interface ICustomerMenuService {
    CustomerMenuResponseDTO getMenu(
            String tableToken
    );
}
