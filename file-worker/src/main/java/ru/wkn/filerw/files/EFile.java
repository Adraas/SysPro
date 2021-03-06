package ru.wkn.filerw.files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.IEntry;

import java.util.List;

/**
 * The class {@code EFile} represents the concrete real file.
 *
 * @param <T> extends {@code IEntry} type
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

    /**
     * The method for the copying values from new {@code EFile} object to this object without reference updating
     * of this object.
     *
     * @param eFile the new {@code EFile} object for the copying values from it
     * @return this {@code EFile} objects after updating
     */
    public EFile<T> copyFrom(EFile<T> eFile) {
        setPath(eFile.getPath());
        setEntries(eFile.getEntries());
        setFileExtension(eFile.getFileExtension());
        return this;
    }
}
