package ru.wkn.analyzers.syntax;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataType {

    BYTE("byte"),
    SHORT("short"),
    INTEGER("integer"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    CHARACTER("character"),
    BOOLEAN("boolean"),
    STRING("string"),
    COMPOSITE_DATA_TYPE("composite_data_type");

    private String dataType;
}
