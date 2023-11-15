package com.github.ratel.services.impl;

import com.github.ratel.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class OrderServiceImpl {

    private final OrderRepository orderRepository;


    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }
}
