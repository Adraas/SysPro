package ru.wkn.filerw.files;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum {@code FileExtension} contains extensions for the real files.
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
