package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.interfaces.OrderController;
import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.OrderResponse;
import com.github.ratel.services.OrderService;
import com.github.ratel.utils.EntityUtil;
import com.github.ratel.utils.transfer_object.OrderTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "orderControllerAdminImpl")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;


    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<Page<OrderResponse>> findAllByUser(
            long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        PageRequest pageRequest = EntityUtil.getPageRequest(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(this.orderService.findAllByUser(userId, pageRequest)
                .map(OrderTransferObj::fromLazyOrder));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<OrderResponse>> findAllByUserForAdmin(
            long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        PageRequest pageRequest = EntityUtil.getPageRequest(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(this.orderService.findAllForAdmin(userId, pageRequest)
                .map(OrderTransferObj::fromLazyOrder));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok(OrderTransferObj.fromOrder(
                this.orderService.findById(id)
        ));
    }

    @Override
    @CrossOrigin("*")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<OrderResponse> findByIdForAdmin(Long id) {
        return ResponseEntity.ok(OrderTransferObj.fromOrder(
                this.orderService.findByIdForAdmin(id)
        ));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest) {
        return ResponseEntity.ok(OrderTransferObj.fromLazyOrder(this.orderService.create(orderRequest)));
    }

    //     TODO change orderStatus from string on enum
    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> updateOrder(Long id, String status) {
        return ResponseEntity.ok(OrderTransferObj.fromLazyOrder(this.orderService.update(id, status)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<MessageResponse> deleteOrder(Long id) {
        this.orderService.delete(id);
        return ResponseEntity.ok(new MessageResponse(
                "Order deleted",
                new Date()
        ));
    }

}
