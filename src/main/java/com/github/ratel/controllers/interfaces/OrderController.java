package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.OrderResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Authorization")
public interface OrderController {

    @GetMapping("order/find/all")
    ResponseEntity<Page<OrderResponse>> findAllByUser(
            @RequestParam long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection

    );

    @GetMapping("order/find/admin/all")
    ResponseEntity<Page<OrderResponse>> findAllByUserForAdmin(
            @RequestParam long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection

    );

    @GetMapping("order/find/id/{id}")
    ResponseEntity<OrderResponse> findById(@PathVariable() Long id);

    @GetMapping("order/find/admin/id/{id}")
    ResponseEntity<OrderResponse> findByIdForAdmin(@PathVariable() Long id);

    @PostMapping("order/create")
    ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest);

    @PutMapping("order/update/{id}/{status}")
    ResponseEntity<OrderResponse> updateOrder(@PathVariable("id") Long id, @PathVariable("status") String status);

    @DeleteMapping("order/delete/{id}")
    ResponseEntity<MessageResponse> deleteOrder(@PathVariable Long id);

}
