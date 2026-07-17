package com.app.ScanNServe.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TableDTO {

    private String tableNumber;
}