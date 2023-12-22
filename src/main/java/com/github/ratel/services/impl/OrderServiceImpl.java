package com.github.ratel.services.impl;

import com.github.ratel.entity.Order;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.OrderRepository;
import com.github.ratel.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAllByUser(Long userId) {
        return this.orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public Order create(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public void delete(long id) {
        this.orderRepository.deleteById(id);
    }
}
