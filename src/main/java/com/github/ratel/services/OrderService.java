package com.github.ratel.services;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.enums.OrderStatus;
import com.github.ratel.payload.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<Order> findAllByUser(Long userId, Pageable pageable);

    Page<Order> findAllByOrderStatus(OrderStatus status, Pageable pageable);

    Page<Order> findAllByUserAndOrderStatus(Long userId, OrderStatus status, Pageable pageable);

    Page<Order> findAllForAdmin(Long userId, Pageable pageable);

    Order findById(Long id);

    Order findByIdForAdmin(Long id);

    Order create(OrderRequest orderRequest);

    Order update(Long id, OrderStatus status);

    void delete(Long id);

}
