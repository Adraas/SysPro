package ru.wkn.filerw.writers;

import ru.wkn.entries.IEntry;
import ru.wkn.filerw.EFileWriter;
import ru.wkn.filerw.files.EFile;
import ru.wkn.filerw.files.FileExtension;
import ru.wkn.filerw.readers.EntriesDelimiter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class FileWriter<T extends IEntry> extends EFileWriter<T> {

    public FileWriter(EFile<T> eFile, String charsetName) {
        super(eFile, charsetName);
    }

    @Override
    public boolean write(IEntry entry) {
        return false;
    }

    @Override
    public boolean write(List entry) {
        return false;
    }

    @Override
    public boolean delete(IEntry entry) {
        return false;
    }

    @Override
    public boolean delete(Long entryNumber) {
        return false;
    }

    @Override
    public boolean deleteSome(List entries) {
        return false;
    }

    @Override
    public boolean deleteSome(Long startEntry, Long endEntry) {
        return false;
    }

    @Override
    public boolean saveFile() throws IOException {
        EFile<T> eFile = getEFile();
        EntriesDelimiter entriesDelimiter = getEntriesDelimiter(eFile);
        Path path = Paths.get(eFile.getPath());
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path, Charset.forName(getCharsetName()));
        List<T> entries = eFile.getEntries();
        for (IEntry entry : entries) {
            String currentLine = entry.singleLineRecording()
                    .concat(Objects.requireNonNull(entriesDelimiter).getEntryDelimiter());
            bufferedWriter.write(currentLine);
        }
        bufferedWriter.close();
        return false;
    }

    private EntriesDelimiter getEntriesDelimiter(EFile<T> eFile) {
        return eFile.getFileExtension().equals(FileExtension.CSV)
                ? EntriesDelimiter.CSV_DELIMITER
                : eFile.getFileExtension().equals(FileExtension.PLAIN_TEXT)
                ? EntriesDelimiter.PLAIN_TEXT_DELIMITER
                : null;
    }
}
