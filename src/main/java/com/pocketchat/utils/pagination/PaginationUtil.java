package com.pocketchat.utils.pagination;

import com.pocketchat.models.pagination.OrderImpl;
import com.pocketchat.models.pagination.PageableImpl;
import com.pocketchat.models.pagination.SortImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationUtil {

    PaginationUtil() {
    }

    public Pageable convertPageableImplToPageable(PageableImpl pageable) {
        Sort sort = convertSortImplToSort(pageable.getSort());

        return PageRequest.of(pageable.getPage(), pageable.getSize(), sort);
    }

    public Sort convertSortImplToSort(SortImpl sort) {
        List<Sort.Order> orders = convertOrderImplToOrder(sort.getOrders());

        return Sort.by(orders);

    }

    List<Sort.Order> convertOrderImplToOrder(List<OrderImpl> orderList) {
        List<Sort.Order> orders = new ArrayList<>();

        if (!ObjectUtils.isEmpty(orderList)) {
            orderList.forEach(order -> orders.add(new Sort.Order(order.getDirection(), order.getProperty())));
        }

        return orders;
    }
}
