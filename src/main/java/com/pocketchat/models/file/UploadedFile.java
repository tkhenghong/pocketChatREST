package com.pocketchat.models.file;

// Use @Data and @SuperBuilder on all levels.
// https://www.baeldung.com/lombok-builder-inheritance

import com.pocketchat.server.configurations.auditing.Auditable;

/**
 * An object to hold reference of the File.
 * Inspired by WL's UploadedFile in Juno Rest Project.
 * NOTE: Do not use this file directly to act as reference.
 */
public class UploadedFile extends Auditable {

    private String fileDirectory;

    // Size of the file in bytes.
    private Long fileSize;

    // Name of the file format. Picked from the full file name.
    private String fileExtension;

    // Content type from MultipartFile.getContentType.
    private String contentType;

    // Name of the file from the MultipartFile, typically comes from the frontend.
    private String fileName;

    public UploadedFile() {
    }

    public UploadedFile(String fileDirectory, Long fileSize, String fileExtension, String contentType, String fileName) {
        this.fileDirectory = fileDirectory;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public static UploadedFileBuilder builder() {
        return new UploadedFileBuilder();
    }

    public String getFileDirectory() {
        return this.fileDirectory;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UploadedFile)) return false;
        final UploadedFile other = (UploadedFile) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$fileDirectory = this.getFileDirectory();
        final Object other$fileDirectory = other.getFileDirectory();
        if (this$fileDirectory == null ? other$fileDirectory != null : !this$fileDirectory.equals(other$fileDirectory))
            return false;
        final Object this$fileSize = this.getFileSize();
        final Object other$fileSize = other.getFileSize();
        if (this$fileSize == null ? other$fileSize != null : !this$fileSize.equals(other$fileSize)) return false;
        final Object this$fileExtension = this.getFileExtension();
        final Object other$fileExtension = other.getFileExtension();
        if (this$fileExtension == null ? other$fileExtension != null : !this$fileExtension.equals(other$fileExtension))
            return false;
        final Object this$contentType = this.getContentType();
        final Object other$contentType = other.getContentType();
        if (this$contentType == null ? other$contentType != null : !this$contentType.equals(other$contentType))
            return false;
        final Object this$fileName = this.getFileName();
        final Object other$fileName = other.getFileName();
        if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UploadedFile;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $fileDirectory = this.getFileDirectory();
        result = result * PRIME + ($fileDirectory == null ? 43 : $fileDirectory.hashCode());
        final Object $fileSize = this.getFileSize();
        result = result * PRIME + ($fileSize == null ? 43 : $fileSize.hashCode());
        final Object $fileExtension = this.getFileExtension();
        result = result * PRIME + ($fileExtension == null ? 43 : $fileExtension.hashCode());
        final Object $contentType = this.getContentType();
        result = result * PRIME + ($contentType == null ? 43 : $contentType.hashCode());
        final Object $fileName = this.getFileName();
        result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
        return result;
    }

    public String toString() {
        return "UploadedFile(fileDirectory=" + this.getFileDirectory() + ", fileSize=" + this.getFileSize() + ", fileExtension=" + this.getFileExtension() + ", contentType=" + this.getContentType() + ", fileName=" + this.getFileName() + ")";
    }

    public static class UploadedFileBuilder {
        private String fileDirectory;
        private Long fileSize;
        private String fileExtension;
        private String contentType;
        private String fileName;

        UploadedFileBuilder() {
        }

        public UploadedFile.UploadedFileBuilder fileDirectory(String fileDirectory) {
            this.fileDirectory = fileDirectory;
            return this;
        }

        public UploadedFile.UploadedFileBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public UploadedFile.UploadedFileBuilder fileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        public UploadedFile.UploadedFileBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public UploadedFile.UploadedFileBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public UploadedFile build() {
            return new UploadedFile(fileDirectory, fileSize, fileExtension, contentType, fileName);
        }

        public String toString() {
            return "UploadedFile.UploadedFileBuilder(fileDirectory=" + this.fileDirectory + ", fileSize=" + this.fileSize + ", fileExtension=" + this.fileExtension + ", contentType=" + this.contentType + ", fileName=" + this.fileName + ")";
        }
    }
}
