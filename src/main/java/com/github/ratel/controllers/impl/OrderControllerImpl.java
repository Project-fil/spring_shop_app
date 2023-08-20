package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.interfaces.OrderController;
import com.github.ratel.payload.request.CreateOrderRequest;
import com.github.ratel.payload.request.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "orderControllerAdminImpl")
public class OrderControllerImpl implements OrderController {


    @Override
    public ResponseEntity<Object> getById(Long orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getByUser(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> createOrder(CreateOrderRequest createOrderRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateOrder(UpdateOrderRequest updateOrderRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteOrder(Long id) {
        return null;
    }
}
