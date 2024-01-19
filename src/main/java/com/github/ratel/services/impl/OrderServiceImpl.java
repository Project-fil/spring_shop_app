package com.github.ratel.services.impl;

import com.github.ratel.entity.Order;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.OrderRepository;
import com.github.ratel.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<Order> findAllByUser(Long userId, Pageable pageable) {
        return this.orderRepository.findAllByUserIdAndRemovedFalse(userId, pageable);
    }

    @Override
    public Page<Order> findAllForAdmin(Long userId, Pageable pageable) {
        return this.orderRepository.findAllByUserIdAndRemovedTrue(userId, pageable);
    }

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public Order findByIdForAdmin(Long id) {
        return this.orderRepository.findByIdForAdmin(id)
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
