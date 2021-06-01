package com.pocketchat.db.models.multimedia;

import com.pocketchat.models.file.UploadedFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "multimedia")
public class Multimedia extends UploadedFile {
    @Id
    private String id;

    Multimedia(String id, String fileDirectory, Long fileSize, String fileExtension,
               String contentType, String fileName) {
        super(fileDirectory, fileSize, fileExtension, contentType, fileName);
        this.id = id;
    }

    public Multimedia() {
    }

    public static MultimediaBuilder multimediaBuilder() {
        return new MultimediaBuilder();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Multimedia)) return false;
        final Multimedia other = (Multimedia) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
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
        return result;
    }

    public String toString() {
        return "Multimedia(id=" + this.getId() + ")";
    }

    public static class MultimediaBuilder {
        private String id;
        private String fileDirectory;
        private Long fileSize;
        private String fileExtension;
        private String contentType;
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

        public Multimedia.MultimediaBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Multimedia.MultimediaBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Multimedia build() {
            return new Multimedia(id, fileDirectory, fileSize, fileExtension, contentType, fileName);
        }

        public String toString() {
            return "Multimedia.MultimediaBuilder(id=" + this.id + ", fileDirectory=" + this.fileDirectory + ", fileSize=" + this.fileSize + ", fileExtension=" + this.fileExtension + ", contentType=" + this.contentType + ", fileName=" + this.fileName + ")";
        }
    }
}
