package ru.wkn.filerw.files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.types.IEntry;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EFile<T extends IEntry> {

    private String path;
    private List<T> entries;
    private FileExtension fileExtension;
}
