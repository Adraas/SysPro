package ru.wkn.filerw;

import ru.wkn.filerw.files.FileExtension;

public interface IFileRWFactory {

    EFileReader createFileReader(FileExtension fileExtension);

    EFileWriter createFileWriter(FileExtension fileExtension);
}
