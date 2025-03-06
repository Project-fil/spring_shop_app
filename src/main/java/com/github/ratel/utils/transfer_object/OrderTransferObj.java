package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.OrderDetails;
import com.github.ratel.entity.Product;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.payload.response.OrderResponse;
import com.github.ratel.payload.response.OrderStatisticResponse;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class OrderTransferObj {

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
        response.setUserResponse(UserTransferObj.fromLazyUserWithAddress(payload.getUser()));
        response.setNote(payload.getNote());
        response.setOrderStatus(payload.getOrderStatus().name());
        response.setRemoved(payload.isRemoved());
        response.setLastModifiedDate(payload.getLastModifiedDate().toString());
        response.setCreatedDate(payload.getCreatedDate().toString());
        return response;
    }

    public static OrderStatisticResponse fromOrderToStatistic(Order payload) {
        OrderStatisticResponse response = new OrderStatisticResponse();
        response.setId(payload.getId());
        response.setUserId(payload.getUser().getId());
        response.setTotalAmount(payload.getTotalAmount().toString());
        response.setTotal(payload.getOrderedProducts().stream().mapToInt(OrderDetails::getQuantity).sum());
        response.setNote(payload.getNote());
        response.setOrderStatus(payload.getOrderStatus().toString());
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

    public static void ifExistProductQuantity(List<Product> productList, Map<Long, Integer> productsMap) {
        productList.forEach(product -> {
            if (productsMap.containsKey(product.getId())) {
                if ((product.getQuantity() - productsMap.get(product.getId())) < 0) {
                    throw new AppException("Insufficient amount of product with id - " + product.getId());
                }
                product.setQuantity(product.getQuantity() - productsMap.get(product.getId()));
            }
        });
    }

}
