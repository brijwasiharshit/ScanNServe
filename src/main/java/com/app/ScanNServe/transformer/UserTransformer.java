package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.UserEntity;
import com.app.ScanNServe.dto.request.UserRequestDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserTransformer {
    public UserEntity toEntity(UserRequestDTO d, String hashedPassword) {
        UserEntity e = new UserEntity();
        e.setName(d.getName());
        e.setRole(d.getRole());
        e.setPropertyIdFk(d.getPropertyIdFk());
        e.setAddress(d.getAddress());
        e.setEmailAddress(d.getEmailAddress());
        e.setContactNumber(d.getContactNumber());
        e.setHashedPassword(hashedPassword);
    return e;
    }
    public UserRequestDTO toDto(UserEntity e){
        UserRequestDTO d = new UserRequestDTO();
        d.setName(e.getName());
        d.setRole(e.getRole());
        d.setPropertyIdFk(e.getPropertyIdFk());
        d.setAddress(e.getAddress());
        d.setEmailAddress(e.getEmailAddress());
        d.setContactNumber(e.getContactNumber());
        return d;
    }
}
