package com.app.ScanNServe.service;

import com.app.ScanNServe.dto.SignUpRequestDTO;
import com.app.ScanNServe.dto.SignUpResponseDTO;

public interface ISignUpService {
    SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO);
}
