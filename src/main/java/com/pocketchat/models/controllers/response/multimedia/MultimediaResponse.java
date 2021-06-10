package com.pocketchat.models.controllers.response.multimedia;

import java.time.LocalDateTime;

public class MultimediaResponse {
    private String id;

    private String fileDirectory;

    private Long fileSize;

    private String fileExtension;

    private String contentType;

    private String fileName;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    private Long version;

    MultimediaResponse(String id, String fileDirectory, Long fileSize, String fileExtension, String contentType, String fileName, String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, Long version) {
        this.id = id;
        this.fileDirectory = fileDirectory;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
        this.contentType = contentType;
        this.fileName = fileName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
    }

    public static MultimediaResponseBuilder builder() {
        return new MultimediaResponseBuilder();
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

    public String getContentType() {
        return this.contentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Long getVersion() {
        return this.version;
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

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MultimediaResponse)) return false;
        final MultimediaResponse other = (MultimediaResponse) o;
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
        final Object this$contentType = this.getContentType();
        final Object other$contentType = other.getContentType();
        if (this$contentType == null ? other$contentType != null : !this$contentType.equals(other$contentType))
            return false;
        final Object this$fileName = this.getFileName();
        final Object other$fileName = other.getFileName();
        if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) return false;
        final Object this$createdBy = this.getCreatedBy();
        final Object other$createdBy = other.getCreatedBy();
        if (this$createdBy == null ? other$createdBy != null : !this$createdBy.equals(other$createdBy)) return false;
        final Object this$createdDate = this.getCreatedDate();
        final Object other$createdDate = other.getCreatedDate();
        if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate))
            return false;
        final Object this$lastModifiedBy = this.getLastModifiedBy();
        final Object other$lastModifiedBy = other.getLastModifiedBy();
        if (this$lastModifiedBy == null ? other$lastModifiedBy != null : !this$lastModifiedBy.equals(other$lastModifiedBy))
            return false;
        final Object this$lastModifiedDate = this.getLastModifiedDate();
        final Object other$lastModifiedDate = other.getLastModifiedDate();
        if (this$lastModifiedDate == null ? other$lastModifiedDate != null : !this$lastModifiedDate.equals(other$lastModifiedDate))
            return false;
        final Object this$version = this.getVersion();
        final Object other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
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
        final Object $createdBy = this.getCreatedBy();
        result = result * PRIME + ($createdBy == null ? 43 : $createdBy.hashCode());
        final Object $createdDate = this.getCreatedDate();
        result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
        final Object $lastModifiedBy = this.getLastModifiedBy();
        result = result * PRIME + ($lastModifiedBy == null ? 43 : $lastModifiedBy.hashCode());
        final Object $lastModifiedDate = this.getLastModifiedDate();
        result = result * PRIME + ($lastModifiedDate == null ? 43 : $lastModifiedDate.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        return result;
    }

    public String toString() {
        return "MultimediaResponse(id=" + this.getId() + ", fileDirectory=" + this.getFileDirectory() + ", fileSize=" + this.getFileSize() + ", fileExtension=" + this.getFileExtension() + ", contentType=" + this.getContentType() + ", fileName=" + this.getFileName() + ", createdBy=" + this.getCreatedBy() + ", createdDate=" + this.getCreatedDate() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", lastModifiedDate=" + this.getLastModifiedDate() + ", version=" + this.getVersion() + ")";
    }

    public static class MultimediaResponseBuilder {
        private String id;
        private String fileDirectory;
        private Long fileSize;
        private String fileExtension;
        private String contentType;
        private String fileName;
        private String createdBy;
        private LocalDateTime createdDate;
        private String lastModifiedBy;
        private LocalDateTime lastModifiedDate;
        private Long version;

        MultimediaResponseBuilder() {
        }

        public MultimediaResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public MultimediaResponseBuilder fileDirectory(String fileDirectory) {
            this.fileDirectory = fileDirectory;
            return this;
        }

        public MultimediaResponseBuilder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public MultimediaResponseBuilder fileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
            return this;
        }

        public MultimediaResponseBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public MultimediaResponseBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public MultimediaResponseBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public MultimediaResponseBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public MultimediaResponseBuilder lastModifiedBy(String lastModifiedBy) {
            this.lastModifiedBy = lastModifiedBy;
            return this;
        }

        public MultimediaResponseBuilder lastModifiedDate(LocalDateTime lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public MultimediaResponseBuilder version(Long version) {
            this.version = version;
            return this;
        }

        public MultimediaResponse build() {
            return new MultimediaResponse(id, fileDirectory, fileSize, fileExtension, contentType, fileName, createdBy, createdDate, lastModifiedBy, lastModifiedDate, version);
        }

        public String toString() {
            return "MultimediaResponse.MultimediaResponseBuilder(id=" + this.id + ", fileDirectory=" + this.fileDirectory + ", fileSize=" + this.fileSize + ", fileExtension=" + this.fileExtension + ", contentType=" + this.contentType + ", fileName=" + this.fileName + ", createdBy=" + this.createdBy + ", createdDate=" + this.createdDate + ", lastModifiedBy=" + this.lastModifiedBy + ", lastModifiedDate=" + this.lastModifiedDate + ", version=" + this.version + ")";
        }
    }
}
