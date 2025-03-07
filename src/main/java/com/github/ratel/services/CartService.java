package com.github.ratel.services;

import com.github.ratel.entity.Cart;
import com.github.ratel.payload.request.CartRequest;

import java.security.Principal;

public interface CartService {

    Cart findUserCartByUser(Principal principal);

    Cart findById(Long id);

    Cart create(Cart cart);

    Cart update(CartRequest cartRequest);

    void deleteCartByUserId(Long userId);

}
