package ru.wkn.filerw;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.types.IEntry;
import ru.wkn.filerw.files.EFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Setter
public abstract class EFileReader<T extends IEntry> {

    private EFile<T> eFile;
    private String charsetName;
    private BufferedReader bufferedReader;

    public EFileReader(EFile<T> eFile, String charsetName) throws IOException {
        this.eFile = eFile;
        this.charsetName = charsetName;
        bufferedReader = Files.newBufferedReader(Paths.get(eFile.getPath()), Charset.forName(charsetName));
    }

    public abstract IEntry read(Long entryNumber) throws IOException;

    public abstract List<IEntry> readSome(Long startEntry, Long endEntry) throws IOException;

    public abstract Long readFileSize() throws IOException;
}
