package com.pocketchat.models.pagination;

import org.springframework.data.domain.Sort;

public class OrderImpl {

    private Sort.Direction direction;

    private String property;

    private boolean ignoreCase;

    private Sort.NullHandling nullHandling;

    public OrderImpl() {
    }

    public Sort.Direction getDirection() {
        return this.direction;
    }

    public String getProperty() {
        return this.property;
    }

    public boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    public Sort.NullHandling getNullHandling() {
        return this.nullHandling;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void setNullHandling(Sort.NullHandling nullHandling) {
        this.nullHandling = nullHandling;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderImpl)) return false;
        final OrderImpl other = (OrderImpl) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$direction = this.getDirection();
        final Object other$direction = other.getDirection();
        if (this$direction == null ? other$direction != null : !this$direction.equals(other$direction)) return false;
        final Object this$property = this.getProperty();
        final Object other$property = other.getProperty();
        if (this$property == null ? other$property != null : !this$property.equals(other$property)) return false;
        if (this.isIgnoreCase() != other.isIgnoreCase()) return false;
        final Object this$nullHandling = this.getNullHandling();
        final Object other$nullHandling = other.getNullHandling();
        if (this$nullHandling == null ? other$nullHandling != null : !this$nullHandling.equals(other$nullHandling))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrderImpl;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $direction = this.getDirection();
        result = result * PRIME + ($direction == null ? 43 : $direction.hashCode());
        final Object $property = this.getProperty();
        result = result * PRIME + ($property == null ? 43 : $property.hashCode());
        result = result * PRIME + (this.isIgnoreCase() ? 79 : 97);
        final Object $nullHandling = this.getNullHandling();
        result = result * PRIME + ($nullHandling == null ? 43 : $nullHandling.hashCode());
        return result;
    }

    public String toString() {
        return "OrderImpl(direction=" + this.getDirection() + ", property=" + this.getProperty() + ", ignoreCase=" + this.isIgnoreCase() + ", nullHandling=" + this.getNullHandling() + ")";
    }
}
