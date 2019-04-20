package ru.wkn.filerw.writers;

import ru.wkn.entries.ICSVEntry;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.EFileWriter;
import ru.wkn.filerw.files.EFile;

import java.io.IOException;
import java.util.List;

public class CSVFileWriter extends EFileWriter {

    public CSVFileWriter(EFile<ICSVEntry> eFile, String charsetName) throws IOException {
        super(eFile, charsetName);
    }

    @Override
    public boolean write(IEntry entry) throws IOException {
        return false;
    }

    @Override
    public boolean write(List entry) throws IOException {
        return false;
    }

    @Override
    public boolean delete(IEntry entry) throws IOException {
        return false;
    }

    @Override
    public boolean delete(Long entryNumber) throws IOException {
        return false;
    }

    @Override
    public boolean deleteSome(List entries) throws IOException {
        return false;
    }

    @Override
    public boolean deleteSome(Long startEntry, Long endEntry) throws IOException {
        return false;
    }
}
