package com.pocketchat.models.controllers.request.multimedia;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GetMultimediaListRequest {

    @NotNull
    @Min(1)
    private List<String> multimediaList;

    public GetMultimediaListRequest() {
    }

    public @NotNull @Min(1) List<String> getMultimediaList() {
        return this.multimediaList;
    }

    public void setMultimediaList(@NotNull @Min(1) List<String> multimediaList) {
        this.multimediaList = multimediaList;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GetMultimediaListRequest)) return false;
        final GetMultimediaListRequest other = (GetMultimediaListRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$multimediaList = this.getMultimediaList();
        final Object other$multimediaList = other.getMultimediaList();
        if (this$multimediaList == null ? other$multimediaList != null : !this$multimediaList.equals(other$multimediaList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GetMultimediaListRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $multimediaList = this.getMultimediaList();
        result = result * PRIME + ($multimediaList == null ? 43 : $multimediaList.hashCode());
        return result;
    }

    public String toString() {
        return "GetMultimediaListRequest(multimediaList=" + this.getMultimediaList() + ")";
    }
}
