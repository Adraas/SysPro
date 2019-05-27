package ru.wkn.filerw.files;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum {@code FileExtension} contains extensions for the real files.
 *
 * @author Artem Pikalov
 */
public enum FileExtension {

    /**
     * The .csv extension.
     */
    CSV("csv"),

    /**
     * The .txt extension (PlainText).
     */
    PLAIN_TEXT("txt"),

    /**
     * The .bin extension.
     */
    BIN("bin");

    /**
     * The extension as {@code String} value.
     */
    @Getter
    private String fileExtension;

    /**
     * The map contains all file extensions as {@code String} objects.
     */
    private static Map<String, FileExtension> extensionsMap;

    static {
        extensionsMap = new HashMap<>();
        for (FileExtension fileExtension : FileExtension.values()) {
            extensionsMap.put(fileExtension.fileExtension, fileExtension);
        }
    }

    /**
     * Initializes a newly created {@code FileExtension} object.
     *
     * @param fileExtension {@link #fileExtension}
     */
    FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * The method to receive {@code FileExtension} enum value by {@code String} value.
     *
     * @param fileExtension {@link #fileExtension}
     * @return {@code FileExtension} enum value by key
     */
    public static FileExtension getInstance(String fileExtension) {
        return extensionsMap.get(fileExtension);
    }
}
