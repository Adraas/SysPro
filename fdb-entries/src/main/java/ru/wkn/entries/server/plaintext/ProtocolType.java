package ru.wkn.entries.server.plaintext;

import lombok.Getter;
import ru.wkn.entries.exceptions.EntryException;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum {@code ProtocolType} contains typical protocol types.
 *
 * @author Alexey Konev
 */
public enum  ProtocolType {

    /**
     * The transmission control protocol.
     */
    TCP("tcp"),

    /**
     * The user datagram protocol.
     */
    UDP("udp");

    /**
     * The protocol type as {@code String} value.
     */
    @Getter
    private String protocolType;

    /**
     * The map contains all protocol types as {@code String} objects.
     */
    private static Map<String, ProtocolType> protocolTypesMap;

    static {
        protocolTypesMap = new HashMap<>();
        for (ProtocolType protocolType : ProtocolType.values()) {
            protocolTypesMap.put(protocolType.getProtocolType(), protocolType);
        }
    }

    /**
     * Initializes a newly created {@code ProtocolType} object.
     *
     * @param protocolType {@link #protocolType}
     */
    ProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    /**
     * The method to receive {@code ProtocolType} enum value by {@code String} value.
     *
     * @param protocolType {@link #protocolType}
     * @return {@code ProtocolType} enum value by key
     */
    public static ProtocolType getInstance(String protocolType) throws EntryException {
        ProtocolType result = protocolTypesMap.get(protocolType.toLowerCase());
        if (result == null) {
            throw new EntryException("unknown protocol type");
        }
        return result;
    }
}
