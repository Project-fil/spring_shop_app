package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.interfaces.OrderController;
import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;
import com.github.ratel.entity.Product;
import com.github.ratel.entity.enums.OrderStatus;
import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.payload.response.OrderResponse;
import com.github.ratel.services.OrderDetailsService;
import com.github.ratel.services.OrderService;
import com.github.ratel.services.ProductService;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.transfer_object.OrderDetailsTransferObj;
import com.github.ratel.utils.transfer_object.OrderTransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/app/shop/")
@RestController(value = "orderControllerAdminImpl")
public class OrderControllerImpl implements OrderController {

    private final UserService userService;

    private final OrderService orderService;

    private final ProductService productService;

    private final OrderDetailsService orderDetailsService;


    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<List<OrderResponse>> getAllByUser(Long userId) {
        List<Order> allByUser = this.orderService.findAllByUser(userId);
        return ResponseEntity.ok(
                allByUser.stream()
                        .map(OrderTransferObj::fromLazyOrder)
                        .collect(Collectors.toList())
        );
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> getById(Long id) {
        return ResponseEntity.ok(OrderTransferObj.fromOrder(
                this.orderService.findById(id)
        ));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest) {
        // TODO: 05.12.2023 check and change product quantity and delete ordered product from cart
        Order order = new Order();
        order.setUser(this.userService.findById(orderRequest.getId()));
        order.setNote(orderRequest.getNote());
        order.setOrderStatus(OrderStatus.UNCONFIRMED);
        Set<OrderDetails> orderDetailsSet = OrderDetailsTransferObj.toSetOrderDetails(
                this.productService.findListForIds(new ArrayList<>(orderRequest.getProducts().keySet())),
                orderRequest.getProducts()
        );
        order.setTotalAmount(OrderTransferObj.getTotalAmount(orderDetailsSet));
        Order finalOrder = this.orderService.create(order);
        orderDetailsSet = this.orderDetailsService.saveOrderDetailsSet(orderDetailsSet, finalOrder);
        finalOrder.setOrderedProducts(orderDetailsSet);
        return ResponseEntity.ok(OrderTransferObj.fromLazyOrder(finalOrder));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> updateOrder(OrderRequest orderRequest) {
        // TODO: 05.12.2023 check and change product quantity when OrderStatus is REJECTED
        Order order = this.orderService.findById(orderRequest.getId());
        if (orderRequest.getProducts() != null) {
            Set<OrderDetails> orderDetailsSet = OrderDetailsTransferObj.toSetOrderDetails(
                    this.productService.findListForIds(new ArrayList<>(orderRequest.getProducts().keySet())),
                    orderRequest.getProducts()
            );
            orderDetailsSet = this.orderDetailsService.saveOrderDetailsSet(orderDetailsSet, order);
            order.setOrderedProducts(orderDetailsSet);
        }
        OrderTransferObj.toOrder(order, orderRequest);
        return ResponseEntity.ok(OrderTransferObj.fromLazyOrder(this.orderService.update(order)));
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
