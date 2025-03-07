package com.github.ratel.services;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;

import java.util.List;
import java.util.Set;

public interface OrderDetailsService {

    List<OrderDetails> findAllByOrderId(Long orderId);

    OrderDetails findById(Long id);

    Set<OrderDetails> saveOrderDetailsSet(Set<OrderDetails> orderDetailsSet, Order order);

    OrderDetails create(OrderDetails orderDetails);

    OrderDetails update(OrderDetails orderDetails);

    void deleteById(Long id);

}
