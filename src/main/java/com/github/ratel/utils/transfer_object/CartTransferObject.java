package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Cart;
import com.github.ratel.payload.dto.CartDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartTransferObject {

    public static CartDto fromCart(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setProducts(cart.getProducts());
        return cartDto;
    }

    public static Cart toCart(Cart cart, CartDto payload) {
        cart.setProducts(payload.getProducts());
        return cart;
    }

}
