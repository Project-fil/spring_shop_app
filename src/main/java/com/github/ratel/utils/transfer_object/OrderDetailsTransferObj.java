package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.OrderDetails;
import com.github.ratel.entity.Product;
import com.github.ratel.payload.response.OrderDetailsResponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class OrderDetailsTransferObj {

    public static OrderDetailsResponse fromOrderDetails(OrderDetails data) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setId(data.getId());
        response.setQuantity(data.getQuantity());
        response.setProduct(ProductTransferObj.fromLazyProduct(data.getProduct()));
        return response;
    }


    public static Set<OrderDetails> toSetOrderDetails(
            List<Product> productList,
            Map<Long, Integer> requestMap
    ) {
        return productList.stream()
                .map(product -> {
                            OrderDetails od = new OrderDetails();
                            od.setProduct(product);
                            od.setQuantity(requestMap.get(product.getId()));
                            return od;
                        }
                )
                .collect(Collectors.toSet());
    }

}
