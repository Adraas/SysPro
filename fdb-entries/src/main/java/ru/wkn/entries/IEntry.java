package ru.wkn.entries;

/**
 * The interface {@code IEntry} represent entry entity for file-worker and DB-worker.
 *
 * @author Artem Pikalov
 */
public interface IEntry {

    /**
     * The method for the obtaining an ID value of this {@code IEntry} object.
     *
     * @return an ID value
     */
    Long getId();

    /**
     * The method for the setting an ID value to this {@code IEntry} object.
     *
     * @param id a new given ID value
     */
    void setId(Long id);

    /**
     * Method for the representation this entry as single {@code String} line.
     *
     * @return {@code String} line with main entry information
     */
    String singleLineRecording();
}
