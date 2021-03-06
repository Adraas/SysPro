package ru.wkn.entries.resource.csv;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.ParametersDelimiter;

import java.io.Serializable;
import java.util.Date;

/**
 * The class {@code ResourceEntry} represents network resource information.
 *
 * @see IEntry
 * @author Artem Pikalov
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class ResourceEntry implements IEntry, Serializable {

    /**
     * Network resource ID.
     */
    private Long id;

    /**
     * Network resource URL.
     */
    private String url;

    /**
     * Network resource access mode.
     */
    private AccessMode accessMode;

    /**
     * Network resource access date.
     */
    private Date accessDate;

    /**
     * Initializes a newly created {@code ResourceEntry} object.
     *
     * @param url {@link #url}
     * @param accessMode {@link #accessMode}
     * @param accessDate {@link #accessDate}
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
