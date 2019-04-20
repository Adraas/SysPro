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
    public boolean append(T entry) {
        return getEFile().getEntries().add(entry);
    }

    @Override
    public boolean append(List<T> entries) {
        return getEFile().getEntries().addAll(entries);
    }

    @Override
    public boolean delete(T entry) {
        return getEFile().getEntries().remove(entry);
    }

    @Override
    public boolean delete(int entryNumber) {
        return delete(getEFile().getEntries().get(entryNumber));
    }

    @Override
    public boolean deleteSome(List<T> entries) {
        boolean isDeleted = true;
        int i = 0;
        while (i < entries.size() && isDeleted) {
            isDeleted = delete(i);
        }
        return isDeleted;
    }

    @Override
    public boolean deleteSome(int startEntry, int endEntry) {
        boolean isDeleted = true;
        for (int i = startEntry; i <= endEntry && isDeleted; i++) {
            isDeleted = delete(i);
        }
        return isDeleted;
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
