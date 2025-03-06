package com.github.ratel.controllers.interfaces;

import com.github.ratel.entity.enums.OrderStatus;
import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.OrderResponse;
import com.github.ratel.payload.response.OrderStatisticResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Authorization")
public interface OrderController {

    @GetMapping()
    ResponseEntity<Page<OrderResponse>> findAllByUser(
            @RequestParam long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection

    );

    @GetMapping("/admin/all")
    ResponseEntity<Page<OrderResponse>> findAllByUserForAdmin(
            @RequestParam long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection

    );

    @GetMapping("/status")
    ResponseEntity<Page<OrderStatisticResponse>> findOrdersByStatus(
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("/status/user")
    ResponseEntity<Page<OrderStatisticResponse>> findUserByOrderStatus(
            @RequestParam long userId,
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    );

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable() Long id);

    @GetMapping("/admin/orderId/{id}")
    ResponseEntity<OrderResponse> findByIdForAdmin(@PathVariable() Long id);

    @PostMapping()
    ResponseEntity<OrderResponse> create(@RequestBody OrderRequest orderRequest);

    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> updateStatus(@PathVariable("id") Long id, @RequestBody OrderStatus status);

    @DeleteMapping()
    ResponseEntity<MessageResponse> deleteOrder(@PathVariable Long id);

}
