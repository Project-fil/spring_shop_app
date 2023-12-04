package com.github.ratel.services;

import com.github.ratel.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllByUser(Long userId);

    Order findById(Long id);

    Order create(Order order);

    Order update(Order order);

    void delete(long id);

}
