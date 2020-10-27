package com.pocketchat.models.pagination;

public class PageableImpl {

    private Integer page;

    private Integer size;

    private SortImpl sort;

    public PageableImpl() {
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getSize() {
        return this.size;
    }

    public SortImpl getSort() {
        return this.sort;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSort(SortImpl sort) {
        this.sort = sort;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PageableImpl)) return false;
        final PageableImpl other = (PageableImpl) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$page = this.getPage();
        final Object other$page = other.getPage();
        if (this$page == null ? other$page != null : !this$page.equals(other$page)) return false;
        final Object this$size = this.getSize();
        final Object other$size = other.getSize();
        if (this$size == null ? other$size != null : !this$size.equals(other$size)) return false;
        final Object this$sort = this.getSort();
        final Object other$sort = other.getSort();
        if (this$sort == null ? other$sort != null : !this$sort.equals(other$sort)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageableImpl;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $page = this.getPage();
        result = result * PRIME + ($page == null ? 43 : $page.hashCode());
        final Object $size = this.getSize();
        result = result * PRIME + ($size == null ? 43 : $size.hashCode());
        final Object $sort = this.getSort();
        result = result * PRIME + ($sort == null ? 43 : $sort.hashCode());
        return result;
    }

    public String toString() {
        return "PageableImpl(page=" + this.getPage() + ", size=" + this.getSize() + ", sort=" + this.getSort() + ")";
    }
}
