package ru.wkn.filerw.writers;

import ru.wkn.entries.types.IEntry;
import ru.wkn.entries.types.IPlainTextEntry;
import ru.wkn.filerw.EFileWriter;
import ru.wkn.filerw.files.EFile;

import java.io.IOException;
import java.util.List;

public class PlainTextFileWriter extends EFileWriter {

    public PlainTextFileWriter(EFile<IPlainTextEntry> eFile, String charsetName) throws IOException {
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
