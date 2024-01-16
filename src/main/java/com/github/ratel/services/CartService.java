package com.github.ratel.services;

import com.github.ratel.entity.Cart;

public interface CartService {

    Cart findById(long id);

    Cart create(Cart cart);

    Cart update(Cart cart);

    void deleteCartByUserId(long userId);

}
