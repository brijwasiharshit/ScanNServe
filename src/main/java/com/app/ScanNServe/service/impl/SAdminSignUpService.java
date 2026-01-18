package com.app.ScanNServe.service.impl;

import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.dto.SignUpRequestDTO;
import com.app.ScanNServe.dto.SignUpResponseDTO;
import com.app.ScanNServe.service.ISignUpService;
import com.app.ScanNServe.domain.repository.ISignUpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SAdminSignUpService implements ISignUpService {
    final ISignUpRepository repository;
    final SignUpTransformer transformer;
     public SignUpResponseDTO signUp(SignUpRequestDTO dto) {
                UserEntity user = transformer.toEntity(dto);
         UserEntity e = repository.save(user);
         return transformer.toDTO(e);
     }

}
