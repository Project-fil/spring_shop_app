package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Order;
import com.github.ratel.payload.request.OrderRequest;
import com.github.ratel.payload.response.OrderResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderTransferObject {

    public static Order toOrder(OrderRequest payload) {
        Order order = new Order();
        /// TODO: 04.12.2023 create this method
        return order;
    }

    public static OrderResponse fromLazyOrder(Order order) {
        OrderResponse response = new OrderResponse();
        /// TODO: 04.12.2023 create this method
        return response;
    }

    public static OrderResponse fromOrder(Order order) {
        OrderResponse response = new OrderResponse();
        /// TODO: 04.12.2023 create this method
        return response;
    }

}
