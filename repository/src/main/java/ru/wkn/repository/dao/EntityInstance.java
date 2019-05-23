package ru.wkn.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum {@code EntityInstance} contains datasource names for the repository.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
@Getter
public enum EntityInstance {

    /**
     * The {@code network_resource} datasource.
     */
    NETWORK_RESOURCE("network_resource"),

    /**
     * The {@code network_server} datasource.
     */
    NETWORK_SERVER("network_server"),

    /**
     * The {@code network_access} datasource.
     */
    NETWORK_ACCESS("network_access");

    /**
     * The {@code String} representation of the datasource name.
     */
    private String entityInstance;
}
