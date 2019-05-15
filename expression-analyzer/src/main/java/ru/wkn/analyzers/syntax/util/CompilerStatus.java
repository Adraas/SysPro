package ru.wkn.analyzers.syntax.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompilerStatus {

    PERFORM_SUCCESSFULLY("Perform successfully"),
    PERFORM_UNSUCCESSFULLY("Perform unsuccessfully");

    private String compilerMessage;
}
