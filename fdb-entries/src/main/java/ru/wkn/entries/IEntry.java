package ru.wkn.entries;

/**
 * The interface {@code IEntry} represent entry entity for file-worker and DB-worker.
 *
 * @author Artem Pikalov
 */
public interface IEntry {

    /**
     * Method for the representation this entry as single {@code String} line.
     *
     * @return {@code String} line with main entry information
     */
    String singleLineRecording();
}
