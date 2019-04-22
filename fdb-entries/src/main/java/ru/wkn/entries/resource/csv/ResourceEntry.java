package ru.wkn.entries.resource.csv;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.ParametersDelimiter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class ResourceEntry implements IEntry {

    private Long id;
    @Setter
    private String url;
    @Setter
    private AccessMode accessMode;
    @Setter
    private Date accessDate;

    public ResourceEntry(String url, AccessMode accessMode, Date accessDate) {
        this.url = url;
        this.accessMode = accessMode;
        this.accessDate = accessDate;
    }

    @Override
    public String singleLineRecording() {
        return url.concat(ParametersDelimiter.RESOURCE_CSV_DELIMITER.getParametersDelimiter())
                .concat(accessMode.getAccessMode()).concat(ParametersDelimiter
                        .RESOURCE_CSV_DELIMITER.getParametersDelimiter())
                .concat(accessDate.toString());
    }
}
