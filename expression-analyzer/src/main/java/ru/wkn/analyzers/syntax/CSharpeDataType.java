package ru.wkn.analyzers.syntax;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CSharpeDataType {

    BYTE("byte"),
    SHORT("short"),
    INTEGER("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    DECIMAL("decimal"),
    CHARACTER("char"),
    BOOLEAN("boolean"),
    STRING("string"),
    SBYTE("sbyte"),
    USHORT("ushort"),
    UINTEGER("uint"),
    ULONG("ulong"),
    COMPOSITE_DATA_TYPE("composite_data_type");

    private String dataType;
}
