package com.app.namasteqr.utils.enums;

import java.util.Set;

public enum Role {

    SUPER_ADMIN(Set.of(
            Permissions.UPDATE_PROPERTY,
            Permissions.DELETE_PROPERTY,
            Permissions.CREATE_PROPERTY,
            Permissions.CREATE_ADMIN,
            Permissions.UPDATE_ADMIN,
            Permissions.DELETE_ADMIN,
            Permissions.CREATE_ITEM,
            Permissions.UPDATE_ITEM,
            Permissions.DELETE_ITEM,
            Permissions.CREATE_CATEGORY,
            Permissions.UPDATE_CATEGORY,
            Permissions.DELETE_CATEGORY,
            Permissions.CREATE_WIFI
    )),

    ADMIN(Set.of(
            Permissions.CREATE_PROPERTY,
            Permissions.UPDATE_PROPERTY,
            Permissions.DELETE_PROPERTY,
            Permissions.ADD_ITEM,
            Permissions.ADD_CATEGORY
    )),

    USER(Set.of(
    ));

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
