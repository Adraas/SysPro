package ru.wkn.filerw.files;

public enum FileExtension {

    CSV("csv"), PLAIN_TEXT("txt");

    private String fileExtension;

    FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
