package ru.wkn.filerw.files;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum {@code FileExtension} represents extension of the real file.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
public enum FileExtension {

    /**
     * .csv extension.
     */
    CSV("csv"),

    /**
     * .txt extension (PlainText).
     */
    PLAIN_TEXT("txt"),

    /**
     * .bin extension.
     */
    BIN("bin");

    /**
     * Extension as {@code String} value.
     */
    @Getter
    private String fileExtension;
}
