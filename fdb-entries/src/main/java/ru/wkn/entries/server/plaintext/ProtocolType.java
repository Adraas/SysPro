package ru.wkn.entries.server.plaintext;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum with typical protocol types.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
public enum  ProtocolType {

    /**
     * Transmission Control Protocol.
     */
    TCP("tcp"),

    /**
     * User Datagram Protocol
     */
    UPD("udp");

    /**
     * Protocol type as {@code String} line;
     */
    @Getter
    private String protocolType;
}
