package ru.wkn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.EFileReader;
import ru.wkn.filerw.EFileWriter;

/**
 * The class {@code FileRWFacade} represents the facade design pattern for the file worker.
 *
 * @param <T> extends {@code IEntry} type and represents entry from one of the files
 * @author Artem Pikalov
 */
@Getter
@AllArgsConstructor
public class FileRWFacade<T extends IEntry> {

    /**
     * The file reader for the specify prepared {@code EFile} objects.
     */
    private EFileReader<T> fileReader;

    /**
     * The file writer for the specify prepared {@code EFile} objects.
     */
    private EFileWriter<T> fileWriter;
}
