package ru.wkn.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityInstance {

    NETWORK_RESOURCE("network_resource"),
    NETWORK_SERVER("network_server"),
    NETWORK_ACCESS("network_access");

    private String entityInstance;
}
