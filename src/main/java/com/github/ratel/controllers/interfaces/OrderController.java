package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.CreateOrderRequest;
import com.github.ratel.payload.request.UpdateOrderRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Authorization")
public interface OrderController {

    @GetMapping("order/get/id/{orderId}")
    ResponseEntity<Object> getById(@PathVariable("orderId") Long orderId);

    @GetMapping("order/get/user/{userId}")
    ResponseEntity<Object> getByUser(@PathVariable("userId") Long userId);

    @PostMapping("order/creste")
    ResponseEntity<Object> createOrder(@RequestBody CreateOrderRequest createOrderRequest);

    @PutMapping("order/update")
    ResponseEntity<Object> updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest);

    @DeleteMapping("order/delete/{id}")
    ResponseEntity<Object> deleteOrder(@PathVariable Long id);

}
