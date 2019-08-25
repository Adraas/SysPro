package ru.wkn.entries.resource.csv;

import lombok.Getter;
import ru.wkn.entries.exceptions.EntryException;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum {@code AccessMode} contains access mode types for network resource.
 *
 * @author Artem Pikalov
 */
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
     * Access mode as {@code String} value.
     */
    @Getter
    private String accessMode;

    /**
     * The map contains all access mode types as {@code String} objects.
     */
    private static Map<String, AccessMode> accessModesMap;

    static {
        accessModesMap = new HashMap<>();
        for (AccessMode accessMode : AccessMode.values()) {
            accessModesMap.put(accessMode.getAccessMode(), accessMode);
        }
    }

    /**
     * Initializes a newly created {@code AccessMode} object.
     *
     * @param accessMode {@link #accessMode}
     */
    AccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    /**
     * The method to receive {@code AccessMode} enum value by {@code String} value.
     *
     * @param accessMode {@link #accessMode}
     * @return {@code ProtocolType} enum value by key
     */
    public static AccessMode getInstance(String accessMode) throws EntryException {
        AccessMode result = accessModesMap.get(accessMode.toLowerCase());
        if (result == null) {
            throw new EntryException("unknown access mode");
        }
        return result;
    }
}
