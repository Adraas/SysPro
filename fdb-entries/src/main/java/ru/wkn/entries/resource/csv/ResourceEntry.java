package ru.wkn.entries.resource.csv;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.wkn.entries.types.ICSVEntry;

import java.sql.Date;

@NoArgsConstructor
@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class ResourceEntry implements ICSVEntry {

    private Long id;
    @Setter
    private String url;
    @Setter
    private AccessMode accessMode;
    @Setter
    private Date date;

    public ResourceEntry(String url, AccessMode accessMode, Date date) {
        this.url = url;
        this.accessMode = accessMode;
        this.date = date;
    }
}
