package ru.wkn.filerw.files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.IEntry;

import java.util.List;

/**
 * Class {@code EFile} represents the concrete real file.
 *
 * @param <T> extends {@link IEntry} type
 * @author Artem Pikalov
 */
@AllArgsConstructor
@Getter
@Setter
public class EFile<T extends IEntry> {

    /**
     * Path of the real file.
     */
    private String path;

    /**
     * Collection of the entries of the real file as {@code T} types.
     */
    private List<T> entries;

    /**
     * Extension of the real file as enum value.
     */
    private FileExtension fileExtension;
}
