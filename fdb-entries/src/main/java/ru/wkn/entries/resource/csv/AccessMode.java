package ru.wkn.entries.resource.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AccessMode {

    PUBLIC("public"), PRIVATE("private");

    @Getter
    private String accessMode;
}
