package ru.wkn.entries;

/**
 * Interface {@code IEntry} represent entry entity for file-worker and DB-worker.
 *
 * @author Artem Pikalov
 */
public interface IEntry {

    /**
     * Method for the representation {@code this} entry as single {@code String} line.
     *
     * @return {@code String} line with main information
     */
    String singleLineRecording();
}
