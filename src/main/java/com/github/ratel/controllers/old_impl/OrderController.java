//package com.github.ratel.controllers.impl;
//
//import com.github.ratel.controllers.ApiSecurityHeader;
//import com.github.ratel.dto.OrderDto;
//import com.github.ratel.entity.Order;
//import com.github.ratel.services.impl.OrderServiceImpl;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/order")
//@AllArgsConstructor
//public class OrderController implements ApiSecurityHeader {
//
//    private final OrderServiceImpl orderServiceImpl;
//
//    @GetMapping
//    public List<Order> findAllOrders() {
//        return orderServiceImpl.findAllOrders();
//    }
//
//    @GetMapping("/{orderId}")
//    @SecurityRequirement(name = "Authorization")
//    public Order findOrderById(@PathVariable long orderId) {
//        return orderServiceImpl.findOrderById(orderId).orElseThrow(() -> new RuntimeException("Not found order!"));
//    }
//
//    @PostMapping
//    @SecurityRequirement(name = "Authorization")
//    public long createOrder(@RequestBody OrderDto orderDto) {
//        return orderServiceImpl.createOrder(orderDto);
//    }
//
//    @PutMapping("/{orderId}")
//    @SecurityRequirement(name = "Authorization")
//    public Order changeOrderInfo(@PathVariable long orderId, @RequestBody OrderDto orderDto) {
//        return orderServiceImpl.changeOrderInfo(orderId, orderDto);
//    }
//
//    @DeleteMapping("/{orderId}")
//    @SecurityRequirement(name = "Authorization")
//    public void deleteOrder(@PathVariable long orderId) {
//        orderServiceImpl.deleteOrder(orderId);
//    }
//}
