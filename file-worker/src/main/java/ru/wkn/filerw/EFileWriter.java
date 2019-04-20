package ru.wkn.filerw;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.files.EFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Setter
public abstract class EFileWriter<T extends IEntry> {

    private EFile<T> eFile;
    private String charsetName;
    private BufferedWriter bufferedWriter;

    public EFileWriter(EFile<T> eFile, String charsetName) throws IOException {
        this.eFile = eFile;
        this.charsetName = charsetName;
        bufferedWriter = Files.newBufferedWriter(Paths.get(eFile.getPath()), Charset.forName(charsetName));
    }

    public abstract boolean write(T entry) throws IOException;

    public abstract boolean write(List<T> entry) throws IOException;

    public abstract boolean delete(T entry) throws IOException;

    public abstract boolean delete(Long entryNumber) throws IOException;

    public abstract boolean deleteSome(List<T> entries) throws IOException;

    public abstract boolean deleteSome(Long startEntry, Long endEntry) throws IOException;
}
