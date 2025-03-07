package com.github.ratel.services.impl;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;
import com.github.ratel.entity.Product;
import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.OrderStatus;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.repositories.OrderRepository;
import com.github.ratel.services.OrderDetailsService;
import com.github.ratel.services.OrderService;
import com.github.ratel.services.ProductService;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.transfer_object.OrderDetailsTransferObj;
import com.github.ratel.utils.transfer_object.OrderTransferObj;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderDetailsService orderDetailsService;

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllByUser(Long userId, Pageable pageable) {
        if (ObjectUtils.anyNull(userId, pageable)) {
            throw new AppException("Invalid parameters value: userId(%s), pageable(%s)", userId, pageable);
        }
        return this.orderRepository.findAllByUserIdAndRemovedFalse(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllByOrderStatus(OrderStatus status, Pageable pageable) {
        if (ObjectUtils.anyNull(status, pageable)) {
            throw new AppException("Invalid parameters value: status(%s), pageable(%s)", status, pageable);
        }
        return this.orderRepository.findAllByOrderStatusAndRemovedFalse(status, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllByUserAndOrderStatus(Long userId, OrderStatus status, Pageable pageable) {
        if (ObjectUtils.anyNull(userId, status, pageable)) {
            throw new AppException(
                    "Invalid parameters value: userId(%s), status(%s), pageable(%s)", userId, status, pageable);
        }
        return this.orderRepository.findAllByUserIdAndOrderStatusAndRemovedFalse(userId, status, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllForAdmin(Long userId, Pageable pageable) {
        if (ObjectUtils.anyNull(userId, pageable)) {
            throw new AppException("Invalid parameters value: userId(%s), pageable(%s)", userId, pageable);
        }
        return this.orderRepository.findAllByUserIdAndRemovedTrue(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.orderRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Order findByIdForAdmin(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.orderRepository.findByIdForAdmin(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    @Transactional
    public Order create(OrderRequest orderRequest) {
        if (Objects.isNull(orderRequest)) {
            throw new AppException("Invalid parameters value: orderRequest(%s)", orderRequest);
        }
        Order order = new Order();
        List<Product> productList = this.productService.findListForIds(new ArrayList<>(orderRequest.getProducts().keySet()));
        OrderTransferObj.ifExistProductQuantity(productList, orderRequest.getProducts());
        User user = this.userService.findById(orderRequest.getUserId());
        productList.forEach(product -> user.getCart().getProducts().remove(product));
        order.setUser(this.userService.updateUser(user));
        order.setNote(orderRequest.getNote());
        order.setOrderStatus(OrderStatus.UNCONFIRMED);
        Set<OrderDetails> orderDetailsSet = OrderDetailsTransferObj.toSetOrderDetails(
                productList,
                orderRequest.getProducts()
        );
        order.setTotalAmount(OrderTransferObj.getTotalAmount(orderDetailsSet));
        productList.forEach(this.productService::update);
        Order finalOrder = this.orderRepository.save(order);
        orderDetailsSet = this.orderDetailsService.saveOrderDetailsSet(orderDetailsSet, finalOrder);
        finalOrder.setOrderedProducts(orderDetailsSet);
        return finalOrder;
    }

    @Override
    @Transactional
    public Order update(Long id, OrderStatus status) {
        if (ObjectUtils.anyNull(id, status)) {
            throw new AppException("Invalid parameters value: id(%s), status(%s)", id, status);
        }
        Order order = this.findById(id);
        if (order.getOrderStatus().equals(OrderStatus.SENT) || order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
            throw new AppException("The parcel has already been sent");
        }
        if (status.equals(OrderStatus.REJECTED)) {
            List<Long> productsIds = order.getOrderedProducts().stream()
                    .map(OrderDetails::getProduct)
                    .map(Product::getId)
                    .collect(Collectors.toList());
            List<Product> productList = this.productService.findListForIds(productsIds);
            order.getOrderedProducts().forEach(orderDetails -> productList.forEach(product -> {
                if (product.getId().equals(orderDetails.getProduct().getId())) {
                    product.setQuantity(product.getQuantity() + orderDetails.getQuantity());
                }
            }));
            productList.forEach(this.productService::update);
        }
        order.setOrderStatus(status);
        return this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        this.orderRepository.deleteById(id);
    }

}
