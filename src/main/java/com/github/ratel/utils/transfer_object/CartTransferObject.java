package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Cart;
import com.github.ratel.entity.Product;
import com.github.ratel.payload.response.CartResponse;
import com.github.ratel.payload.response.ProductResponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@UtilityClass
public class CartTransferObject {

    public static CartResponse fromCart(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setProducts(mapToProductResponse(cart.getProducts()));
        return cartResponse;
    }

    public static Cart toCart(Cart cart, Map<Product, Integer> payload) {
        cart.setProducts(payload);
        return cart;
    }

    public static Map<Product, Integer> convertListProductToMap(
            List<Product> productList,
            Map<Long, Integer> requestMap
    ) {
        return productList.stream()
                .collect(Collectors.toMap(product -> product, product -> requestMap.get(product.getId())));
    }

    public static Cart addToCart(Cart cart, Map<Product, Integer> payload) {
        payload.keySet().forEach(key -> {
            if (cart.getProducts().containsKey(key)) {
                AtomicInteger i = new AtomicInteger(cart.getProducts().get(key));
                cart.getProducts().replace(key, i.addAndGet(payload.get(key)));
            } else {
                cart.getProducts().put(key, payload.get(key));
            }
        });
        return cart;
    }

    private static Map<ProductResponse, Integer> mapToProductResponse(Map<Product, Integer> payload) {
        return payload.keySet().stream()
                .collect(Collectors.toMap(
                        ProductTransferObj::fromProduct,
                        payload::get
                ));
    }

}
