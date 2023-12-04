package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.interfaces.OrderController;
import com.github.ratel.entity.Order;
import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.OrderResponse;
import com.github.ratel.services.OrderService;
import com.github.ratel.utils.transfer_object.OrderTransferObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "orderControllerAdminImpl")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<List<OrderResponse>> getAllByUser(Long userId) {
        List<Order> allByUser = this.orderService.findAllByUser(userId);
        return ResponseEntity.ok(
                allByUser.stream()
                        .map(OrderTransferObject::fromLazyOrder)
                        .collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> getById(Long id) {
        return ResponseEntity.ok(OrderTransferObject.fromOrder(
                this.orderService.findById(id)
        ));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest) {
        return null;
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> updateOrder(OrderRequest orderRequest) {
        return null;
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> deleteOrder(Long id) {
        this.orderService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Order deleted"));
    }

}
