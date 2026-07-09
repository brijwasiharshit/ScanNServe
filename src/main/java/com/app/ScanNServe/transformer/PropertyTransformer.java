package com.app.ScanNServe.transformer;

import com.app.ScanNServe.domain.entity.PropertyEntity;
import com.app.ScanNServe.dto.request.PropertyRequestDTO;
import com.app.ScanNServe.dto.response.PropertyResponseDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PropertyTransformer {

    public PropertyEntity toEntity(PropertyRequestDTO d, Long adminId) {
        PropertyEntity e = new PropertyEntity();
        e.setName(d.getName());
        e.setDesc(d.getDescription());
        e.setAddress(d.getAddress());
        e.setLogoLink(d.getLogoLink());
        e.setAdminFk(adminId);
        return e;
    }

    public PropertyResponseDTO toDto(PropertyEntity e) {
        PropertyResponseDTO d = new PropertyResponseDTO();
        d.setId(e.getId());
        d.setName(e.getName());
        d.setDescription(e.getDesc());
        d.setAddress(e.getAddress());
        d.setLogoLink(e.getLogoLink());
        return d;
    }
}

