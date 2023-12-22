package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;
import com.github.ratel.entity.enums.OrderStatus;
import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.OrderResponse;
import com.github.ratel.utils.EntityUtil;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class OrderTransferObj {

    public static void toOrder(Order order, OrderRequest payload) {
        order.setNote(EntityUtil.updateField(order.getNote(), payload.getNote()));
        order.setOrderStatus(EntityUtil.updateField(
                order.getOrderStatus(),
                OrderStatus.valueOf(payload.getOrderStatus())
        ));
    }

    public static OrderResponse fromLazyOrder(Order payload) {
        OrderResponse response = new OrderResponse();
        response.setId(payload.getId());
        response.setTotalAmount(payload.getTotalAmount().toString());
        response.setTotal(payload.getOrderedProducts().stream().mapToInt(OrderDetails::getQuantity).sum());
        response.setOrderedProducts(
                payload.getOrderedProducts().stream()
                        .map(OrderDetailsTransferObj::fromOrderDetails)
                        .collect(Collectors.toSet())
        );
        response.setNote(payload.getNote());
        response.setOrderStatus(payload.getOrderStatus().name());
        return response;
    }

    public static OrderResponse fromOrder(Order payload) {
        OrderResponse response = new OrderResponse();
        response.setId(payload.getId());
        response.setTotalAmount(payload.getTotalAmount().toString());
        response.setTotal(payload.getOrderedProducts().stream().mapToInt(OrderDetails::getQuantity).sum());
        response.setOrderedProducts(
                payload.getOrderedProducts().stream()
                        .map(OrderDetailsTransferObj::fromOrderDetails)
                        .collect(Collectors.toSet())
        );
        response.setUserResponse(UserTransferObj.fromLazyUser(payload.getUser()));
        response.setNote(payload.getNote());
        response.setOrderStatus(payload.getOrderStatus().name());
        response.setRemoved(payload.isRemoved());
        response.setLastModifiedDate(payload.getLastModifiedDate().toString());
        response.setCreatedDate(payload.getCreatedDate().toString());
        return response;
    }

    public static BigDecimal getTotalAmount(Set<OrderDetails> orderDetailsSet) {
        return BigDecimal.valueOf(orderDetailsSet.stream()
                .mapToDouble(orderDetail -> orderDetail.getProduct().getPrice().multiply(
                        new BigDecimal(orderDetail.getQuantity())
                ).doubleValue())
                .sum());
    }

}
