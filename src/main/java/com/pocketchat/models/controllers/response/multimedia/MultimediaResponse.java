package com.pocketchat.models.controllers.response.multimedia;

import com.pocketchat.models.enums.multimedia_type.MultimediaType;

public class MultimediaResponse {
    private String id;

    private MultimediaType multimediaType;

    private String fileDirectory;

    // Size of the file in bytes.
    private Long fileSize;

    // Name of the file format. Picked from the full file name.
    private String fileExtension;

    // Content type from MultipartFile.getContentType.
    private String contentType;

    // Name of the file from the MultipartFile, typically comes from the frontend.
    private String fileName;

    MultimediaResponse(String id, MultimediaType multimediaType, String fileDirectory, Long fileSize, String fileExtension, String contentType, String fileName) {
        this.id = id;
        this.multimediaType = multimediaType;
        this.fileDirectory = fileDirectory;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public static MultimediaResponseBuilder builder() {
        return new MultimediaResponseBuilder();
    }

    public String getId() {
        return this.id;
    }

    public MultimediaType getMultimediaType() {
        return this.multimediaType;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setMultimediaType(MultimediaType multimediaType) {
        this.multimediaType = multimediaType;
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
        if (!(o instanceof MultimediaResponse)) return false;
        final MultimediaResponse other = (MultimediaResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$multimediaType = this.getMultimediaType();
        final Object other$multimediaType = other.getMultimediaType();
        if (this$multimediaType == null ? other$multimediaType != null : !this$multimediaType.equals(other$multimediaType))
            return false;
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
        return other instanceof MultimediaResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $multimediaType = this.getMultimediaType();
        result = result * PRIME + ($multimediaType == null ? 43 : $multimediaType.hashCode());
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
        return "MultimediaResponse(id=" + this.getId() + ", multimediaType=" + this.getMultimediaType() + ", fileDirectory=" + this.getFileDirectory() + ", fileSize=" + this.getFileSize() + ", fileExtension=" + this.getFileExtension() + ", contentType=" + this.getContentType() + ", fileName=" + this.getFileName() + ")";
    }

    public static class MultimediaResponseBuilder {
        private String id;
        private MultimediaType multimediaType;
        private String fileDirectory;
        private Long fileSize;
        private String fileExtension;
        private String contentType;
        private String fileName;

        MultimediaResponseBuilder() {
        }

        public MultimediaResponse.MultimediaResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public MultimediaResponse.MultimediaResponseBuilder multimediaType(MultimediaType multimediaType) {
            this.multimediaType = multimediaType;
            return this;
        }

        public MultimediaResponse.MultimediaResponseBuilder fileDirectory(String fileDirectory) {
            this.fileDirectory = fileDirectory;
            return this;
        }

        public MultimediaResponse.MultimediaResponseBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public MultimediaResponse.MultimediaResponseBuilder fileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        public MultimediaResponse.MultimediaResponseBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public MultimediaResponse.MultimediaResponseBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public MultimediaResponse build() {
            return new MultimediaResponse(id, multimediaType, fileDirectory, fileSize, fileExtension, contentType, fileName);
        }

        public String toString() {
            return "MultimediaResponse.MultimediaResponseBuilder(id=" + this.id + ", multimediaType=" + this.multimediaType + ", fileDirectory=" + this.fileDirectory + ", fileSize=" + this.fileSize + ", fileExtension=" + this.fileExtension + ", contentType=" + this.contentType + ", fileName=" + this.fileName + ")";
        }
    }
}
