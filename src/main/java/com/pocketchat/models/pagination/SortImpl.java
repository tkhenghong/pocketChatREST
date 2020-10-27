package com.pocketchat.models.pagination;

import java.util.List;

public class SortImpl {

    private List<OrderImpl> orders;

    public SortImpl() {
    }

    public List<OrderImpl> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderImpl> orders) {
        this.orders = orders;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SortImpl)) return false;
        final SortImpl other = (SortImpl) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$orders = this.getOrders();
        final Object other$orders = other.getOrders();
        if (this$orders == null ? other$orders != null : !this$orders.equals(other$orders)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SortImpl;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $orders = this.getOrders();
        result = result * PRIME + ($orders == null ? 43 : $orders.hashCode());
        return result;
    }

    public String toString() {
        return "SortImpl(orders=" + this.getOrders() + ")";
    }
}
