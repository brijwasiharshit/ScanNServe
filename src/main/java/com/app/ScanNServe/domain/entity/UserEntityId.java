package com.app.ScanNServe.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityId implements Serializable {

    private String username;
    private String emailAddress;
}
