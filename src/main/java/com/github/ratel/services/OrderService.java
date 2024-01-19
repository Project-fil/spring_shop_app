package com.github.ratel.services;

import com.github.ratel.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Page<Order> findAllByUser(Long userId, Pageable pageable);

    Page<Order> findAllForAdmin(Long userId, Pageable pageable);

    Order findById(Long id);

    Order findByIdForAdmin(Long id);

    Order create(Order order);

    Order update(Order order);

    void delete(long id);

}
