package ru.wkn.analyzers.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompilerStatus {

    COMPILE_SUCCESS("success");

    private String compilerMessage;
}
