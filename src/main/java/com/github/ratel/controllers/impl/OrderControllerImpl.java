package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.interfaces.OrderController;
import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;
import com.github.ratel.entity.Product;
import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.OrderStatus;
import com.github.ratel.exceptions.AppException;
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
import java.util.Date;
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
        Order order = new Order();
        List<Product> productList = this.productService.findListForIds(new ArrayList<>(orderRequest.getProducts().keySet()));
        OrderTransferObj.ifExistProductQuantity(productList, orderRequest.getProducts());
        User user = this.userService.findById(orderRequest.getUserId());
        productList.forEach(product -> {
            user.getCart().getProducts().remove(product);
        });
        order.setUser(this.userService.updateUser(user));
        order.setNote(orderRequest.getNote());
        order.setOrderStatus(OrderStatus.UNCONFIRMED);
        Set<OrderDetails> orderDetailsSet = OrderDetailsTransferObj.toSetOrderDetails(
                productList,
                orderRequest.getProducts()
        );
        order.setTotalAmount(OrderTransferObj.getTotalAmount(orderDetailsSet));
        productList.forEach(this.productService::update);
        Order finalOrder = this.orderService.create(order);
        orderDetailsSet = this.orderDetailsService.saveOrderDetailsSet(orderDetailsSet, finalOrder);
        finalOrder.setOrderedProducts(orderDetailsSet);
        return ResponseEntity.ok(OrderTransferObj.fromLazyOrder(finalOrder));
    }

    @Override
    @Transactional
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<OrderResponse> updateOrder(Long id, String status) {
        Order order = this.orderService.findById(id);
        if (status != null) {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            if (orderStatus.equals(OrderStatus.REJECTED)) {
                if (order.getOrderStatus().equals(OrderStatus.SENT)
                        || order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                    throw new AppException("The parcel has already been sent");
                } else {
                    List<Long> productsIds = order.getOrderedProducts().stream()
                            .map(OrderDetails::getProduct)
                            .map(Product::getId)
                            .collect(Collectors.toList());
                    List<Product> productList = this.productService.findListForIds(productsIds);
                    order.getOrderedProducts().forEach(orderDetails -> {
                        productList.forEach(product -> {
                            if (product.getId().equals(orderDetails.getProduct().getId())) {
                                product.setQuantity(product.getQuantity() + orderDetails.getQuantity());
                            }
                        });
                    });
                    productList.forEach(this.productService::update);
                }
            }
            order.setOrderStatus(orderStatus);
        }
        return ResponseEntity.ok(OrderTransferObj.fromLazyOrder(this.orderService.update(order)));
    }

    @Override
    @Transactional
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
