package ru.wkn.filerw;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.files.EFile;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public abstract class EFileWriter<T extends IEntry> {

    private EFile<T> eFile;
    private String charsetName;

    public EFileWriter(EFile<T> eFile, String charsetName) {
        this.eFile = eFile;
        this.charsetName = charsetName;
    }

    public abstract boolean append(T entry);

    public abstract boolean append(List<T> entries);

    public abstract boolean delete(T entry);

    public abstract boolean delete(int entryNumber);

    public abstract boolean deleteSome(List<T> entries);

    public abstract boolean deleteSome(int startEntry, int endEntry);

    public abstract boolean saveFile() throws IOException;
}
