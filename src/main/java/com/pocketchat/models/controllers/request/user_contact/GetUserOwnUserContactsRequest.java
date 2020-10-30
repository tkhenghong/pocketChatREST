package com.pocketchat.models.controllers.request.user_contact;

import com.pocketchat.models.pagination.PageableImpl;

public class GetUserOwnUserContactsRequest {
    private String searchTerm;
    private PageableImpl pageable;

    public GetUserOwnUserContactsRequest() {
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    public PageableImpl getPageable() {
        return this.pageable;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setPageable(PageableImpl pageable) {
        this.pageable = pageable;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GetUserOwnUserContactsRequest))
            return false;
        final GetUserOwnUserContactsRequest other = (GetUserOwnUserContactsRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$searchTerm = this.getSearchTerm();
        final Object other$searchTerm = other.getSearchTerm();
        if (this$searchTerm == null ? other$searchTerm != null : !this$searchTerm.equals(other$searchTerm))
            return false;
        final Object this$pageable = this.getPageable();
        final Object other$pageable = other.getPageable();
        if (this$pageable == null ? other$pageable != null : !this$pageable.equals(other$pageable)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GetUserOwnUserContactsRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $searchTerm = this.getSearchTerm();
        result = result * PRIME + ($searchTerm == null ? 43 : $searchTerm.hashCode());
        final Object $pageable = this.getPageable();
        result = result * PRIME + ($pageable == null ? 43 : $pageable.hashCode());
        return result;
    }

    public String toString() {
        return "GetUserOwnUserContectRequest(searchTerm=" + this.getSearchTerm() + ", pageable=" + this.getPageable() + ")";
    }
}
