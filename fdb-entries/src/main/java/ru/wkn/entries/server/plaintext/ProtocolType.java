package ru.wkn.entries.server.plaintext;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  ProtocolType {

    TCP("tcp"), UPD("udp");

    @Getter
    private String protocolType;
}
