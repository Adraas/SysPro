package ru.wkn.filerw;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.files.EFile;

import java.util.List;

@Getter
@Setter
public abstract class EFileReader<T extends IEntry> {

    private EFile<T> eFile;

    public EFileReader(EFile<T> eFile) {
        this.eFile = eFile;
    }

    public abstract IEntry read(int entryNumber);

    public abstract List<T> readSome(int startEntry, int endEntry);

    public abstract int readFileSize();

    public abstract boolean contains(T entry);
}
