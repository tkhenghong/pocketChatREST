package com.pocketchat.db.models.multimedia;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "multimedia")
public class Multimedia {
    // Image, Video, GIFs, Sticker, Recording, links
    @Id
    private String id;

    private String fileDirectory;

    // In Kb
    private Long fileSize;

    private String fileExtension;

    private String fileName;

    Multimedia(String id, String fileDirectory, Long fileSize, String fileExtension, String fileName) {
        this.id = id;
        this.fileDirectory = fileDirectory;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
        this.fileName = fileName;
    }

    public static MultimediaBuilder builder() {
        return new MultimediaBuilder();
    }

    public String getId() {
        return this.id;
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

    public String getFileName() {
        return this.fileName;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Multimedia)) return false;
        final Multimedia other = (Multimedia) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
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
        final Object this$fileName = this.getFileName();
        final Object other$fileName = other.getFileName();
        if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Multimedia;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $fileDirectory = this.getFileDirectory();
        result = result * PRIME + ($fileDirectory == null ? 43 : $fileDirectory.hashCode());
        final Object $fileSize = this.getFileSize();
        result = result * PRIME + ($fileSize == null ? 43 : $fileSize.hashCode());
        final Object $fileExtension = this.getFileExtension();
        result = result * PRIME + ($fileExtension == null ? 43 : $fileExtension.hashCode());
        final Object $fileName = this.getFileName();
        result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
        return result;
    }

    public String toString() {
        return "Multimedia(id=" + this.getId() + ", fileDirectory=" + this.getFileDirectory() + ", fileSize=" + this.getFileSize() + ", fileExtension=" + this.getFileExtension() + ", fileName=" + this.getFileName() + ")";
    }

    public static class MultimediaBuilder {
        private String id;
        private String fileDirectory;
        private Long fileSize;
        private String fileExtension;
        private String fileName;

        MultimediaBuilder() {
        }

        public Multimedia.MultimediaBuilder id(String id) {
            this.id = id;
            return this;
        }

        public Multimedia.MultimediaBuilder fileDirectory(String fileDirectory) {
            this.fileDirectory = fileDirectory;
            return this;
        }

        public Multimedia.MultimediaBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Multimedia.MultimediaBuilder fileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        public Multimedia.MultimediaBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Multimedia build() {
            return new Multimedia(id, fileDirectory, fileSize, fileExtension, fileName);
        }

        public String toString() {
            return "Multimedia.MultimediaBuilder(id=" + this.id + ", fileDirectory=" + this.fileDirectory + ", fileSize=" + this.fileSize + ", fileExtension=" + this.fileExtension + ", fileName=" + this.fileName + ")";
        }
    }
}
