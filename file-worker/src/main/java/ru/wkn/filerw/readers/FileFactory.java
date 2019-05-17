package ru.wkn.filerw.readers;

import ru.wkn.entries.IEntry;
import ru.wkn.entries.IEntryFactory;
import ru.wkn.entries.ParametersDelimiter;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.filerw.files.EFile;
import ru.wkn.filerw.files.FileExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileFactory<T extends IEntry> implements IFileFactory<T> {

    @Override
    public EFile<T> createEFile(String path, String charsetName, EntriesDelimiter entriesDelimiter,
                                IEntryFactory entryFactory, ParametersDelimiter parametersDelimiter)
            throws IOException, EntryException {
        String[] extensionGroups = path.split(".");
        FileExtension fileExtension = FileExtension.valueOf(extensionGroups[extensionGroups.length - 1]
                .toLowerCase());
        List<T> entries = new ArrayList<>();

        Path aPath = Paths.get(path);
        if (Files.exists(aPath)) {
            BufferedReader bufferedReader = Files.newBufferedReader(aPath, Charset.forName(charsetName));

            boolean fileIsReadyToRead = true;
            while (fileIsReadyToRead) {
                String parametersLine = "";
                while (!parametersLine.contains(entriesDelimiter.getEntryDelimiter())) {
                    char[] buffer = new char[1];
                    int charactersNumber = bufferedReader.read(buffer);
                    if (charactersNumber != -1) {
                        parametersLine = parametersLine.concat(new String(buffer));
                    } else {
                        fileIsReadyToRead = false;
                    }
                }
                entries.add((T) entryFactory.createEntry(parametersLine, parametersDelimiter));
            }
            bufferedReader.close();
        } else {
            Files.createFile(aPath);
        }
        return new EFile<>(path, entries, fileExtension);
    }
}
