package com.github.ratel.services.impl;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.OrderDetailsRepository;
import com.github.ratel.services.OrderDetailsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> findAllByOrderId(Long orderId) {
        if (Objects.isNull(orderId)) {
            throw new AppException("Invalid parameters value: orderId(%s)", orderId);
        }
        return this.orderDetailsRepository.findAllByOrderIdAndRemovedFalse(orderId);
    }

    @Override
    public OrderDetails findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.orderDetailsRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderDetails not found"));
    }

    @Override
    public Set<OrderDetails> saveOrderDetailsSet(Set<OrderDetails> orderDetailsSet, Order order) {
        if (ObjectUtils.anyNull(orderDetailsSet, order)) {
            throw new AppException("Invalid parameters value: orderDetailsSet or order");
        }
        return orderDetailsSet.stream()
                .map(od -> {
                    od.setOrder(order);
                    return this.create(od);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public OrderDetails create(OrderDetails orderDetails) {
        if (Objects.isNull(orderDetails)) {
            throw new AppException("Invalid parameters value: orderDetails(%s)", orderDetails);
        }
        return this.orderDetailsRepository.save(orderDetails);
    }

    @Override
    public OrderDetails update(OrderDetails orderDetails) {
        if (Objects.isNull(orderDetails)) {
            throw new AppException("Invalid parameters value: orderDetails(%s)", orderDetails);
        }
        return this.orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        this.orderDetailsRepository.deleteById(id);
    }

}
