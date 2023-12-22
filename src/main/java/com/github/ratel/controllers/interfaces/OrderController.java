package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.OrderResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Authorization")
public interface OrderController {

    @GetMapping("order/get/all/{userId}")
    ResponseEntity<List<OrderResponse>> getAllByUser(@PathVariable Long userId);

    @GetMapping("order/get/id/{id}")
    ResponseEntity<OrderResponse> getById(@PathVariable("id") Long id);

    @PostMapping("order/create")
    ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest);

    @PutMapping("order/update")
    ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest);

    @DeleteMapping("order/delete/{id}")
    ResponseEntity<MessageResponse> deleteOrder(@PathVariable Long id);

}
