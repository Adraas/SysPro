package ru.wkn.filerw.files;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FileExtension {

    CSV("csv"),
    PLAIN_TEXT("txt"),
    BIN("bin");

    @Getter
    private String fileExtension;
}
