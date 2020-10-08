package com.pocketchat.db.models.multimedia;

import com.pocketchat.models.enums.multimedia_type.MultimediaType;
import com.pocketchat.models.file.UploadedFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "multimedia")
public class Multimedia extends UploadedFile {
    @Id
    private String id;

    private MultimediaType multimediaType;

    protected Multimedia(MultimediaBuilder<?, ?> b) {
        super(b);
        this.id = b.id;
        this.multimediaType = b.multimediaType;
    }

    public static MultimediaBuilder<?, ?> builder() {
        return new MultimediaBuilderImpl();
    }

    public String getId() {
        return this.id;
    }

    public MultimediaType getMultimediaType() {
        return this.multimediaType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMultimediaType(MultimediaType multimediaType) {
        this.multimediaType = multimediaType;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Multimedia)) return false;
        final Multimedia other = (Multimedia) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$multimediaType = this.getMultimediaType();
        final Object other$multimediaType = other.getMultimediaType();
        if (this$multimediaType == null ? other$multimediaType != null : !this$multimediaType.equals(other$multimediaType))
            return false;
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
        final Object $multimediaType = this.getMultimediaType();
        result = result * PRIME + ($multimediaType == null ? 43 : $multimediaType.hashCode());
        return result;
    }

    public String toString() {
        return "Multimedia(id=" + this.getId() + ", multimediaType=" + this.getMultimediaType() + ")";
    }

    public static abstract class MultimediaBuilder<C extends Multimedia, B extends Multimedia.MultimediaBuilder<C, B>> extends UploadedFileBuilder<C, B> {
        private String id;
        private MultimediaType multimediaType;

        public B id(String id) {
            this.id = id;
            return self();
        }

        public B multimediaType(MultimediaType multimediaType) {
            this.multimediaType = multimediaType;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "Multimedia.MultimediaBuilder(super=" + super.toString() + ", id=" + this.id + ", multimediaType=" + this.multimediaType + ")";
        }
    }

    private static final class MultimediaBuilderImpl extends MultimediaBuilder<Multimedia, MultimediaBuilderImpl> {
        private MultimediaBuilderImpl() {
        }

        protected Multimedia.MultimediaBuilderImpl self() {
            return this;
        }

        public Multimedia build() {
            return new Multimedia(this);
        }
    }
}
