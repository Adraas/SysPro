package ru.wkn.entries.resource.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum {@code AccessMode} contains access mode types for network resource.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
public enum AccessMode {

    /**
     * Public access mode.
     */
    PUBLIC("public"),

    /**
     * Private access mode.
     */
    PRIVATE("private");

    /**
     * Access mode as {@code String} line.
     */
    @Getter
    private String accessMode;
}
