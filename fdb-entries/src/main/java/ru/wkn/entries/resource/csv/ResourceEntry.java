package ru.wkn.entries.resource.csv;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.ParametersDelimiter;

import java.sql.Date;

/**
 * Class {@code ResourceEntry} represents network resource information.
 *
 * @see IEntry
 * @author Artem Pikalov
 */
@NoArgsConstructor
@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class ResourceEntry implements IEntry {

    /**
     * Network resource ID.
     */
    private Long id;

    /**
     * Network resource URL.
     */
    @Setter
    private String url;

    /**
     * Network resource access mode.
     */
    @Setter
    private AccessMode accessMode;

    /**
     * Network resource access date.
     */
    @Setter
    private Date accessDate;

    /**
     * Initializes a newly created {@code ResourceEntry} object.
     *
     * @param url - {@link ResourceEntry#url}
     * @param accessMode - {@link ResourceEntry#accessMode}
     * @param accessDate - {@link ResourceEntry#accessDate}
     */
    public ResourceEntry(String url, AccessMode accessMode, Date accessDate) {
        this.url = url;
        this.accessMode = accessMode;
        this.accessDate = accessDate;
    }

    /**
     * @see IEntry#singleLineRecording()
     */
    @Override
    public String singleLineRecording() {
        return url.concat(ParametersDelimiter.RESOURCE_CSV_DELIMITER.getParametersDelimiter())
                .concat(accessMode.getAccessMode()).concat(ParametersDelimiter
                        .RESOURCE_CSV_DELIMITER.getParametersDelimiter())
                .concat(accessDate.toString());
    }
}
