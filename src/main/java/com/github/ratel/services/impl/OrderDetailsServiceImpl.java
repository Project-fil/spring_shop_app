package com.github.ratel.services.impl;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.OrderDetailsRepository;
import com.github.ratel.services.OrderDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> findAllByOrderId(long orderId) {
        return this.orderDetailsRepository.findAllByOrderId(orderId);
    }

    @Override
    public OrderDetails findById(long id) {
        return this.orderDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderDetails not found"));
    }

    @Override
    public Set<OrderDetails> saveOrderDetailsSet(Set<OrderDetails> orderDetailsSet, Order order) {
        return orderDetailsSet.stream()
                .map(od -> {
                    od.setOrder(order);
                    return this.create(od);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public OrderDetails create(OrderDetails orderDetails) {
        return this.orderDetailsRepository.save(orderDetails);
    }

    @Override
    public OrderDetails update(OrderDetails orderDetails) {
        return this.orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteById(long id) {
        this.orderDetailsRepository.deleteById(id);
    }
}
