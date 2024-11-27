package com.github.ratel.services.impl;

import com.github.ratel.entity.Cart;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.CartRepository;
import com.github.ratel.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    @Transactional(readOnly = true)
    public Cart findById(long id) {
        return this.cartRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    @Transactional
    public Cart create(Cart cart) { return this.cartRepository.save(cart); }

    @Override
    @Transactional
    public Cart update(Cart cart) {
        return this.cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCartByUserId(long userId) {
        this.cartRepository.deleteCartByUserId(userId);
    }
}
