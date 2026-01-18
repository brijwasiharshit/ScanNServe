package com.app.ScanNServe.dto;

import lombok.Data;

import java.util.List;

@Data
public class SignUpRequestDTO {
PersonalDetailsDTO personalDetailsDTO;
List<PropertyDetailsDTO> propertyDetailsDTO;
}
