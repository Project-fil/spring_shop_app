package com.github.ratel.services.impl;

import com.github.ratel.entity.Cart;
import com.github.ratel.entity.Product;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.payload.request.CartRequest;
import com.github.ratel.repositories.CartRepository;
import com.github.ratel.services.CartService;
import com.github.ratel.services.ProductService;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.transfer_object.CartTransferObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    @Transactional(readOnly = true)
    public Cart findUserCartByUser(Principal principal) {
        return this.userService.getCurrentUser(principal).getCart();
    }

    @Override
    @Transactional(readOnly = true)
    public Cart findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.cartRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    @Transactional
    public Cart create(Cart cart) {
        if (Objects.isNull(cart)) {
            throw new AppException("Invalid parameters value: cart(%s)", cart);
        }
        return this.cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart update(CartRequest cartRequest) {
        if (Objects.isNull(cartRequest)) {
            throw new AppException("Invalid parameters value: cartRequest(%s)", cartRequest);
        }
        Cart cart = this.findById(cartRequest.getId());
        Map<Product, Integer> cartMap = CartTransferObject.convertListProductToMap(
                this.productService.findListForIds(new ArrayList<>(cartRequest.getProducts().keySet())),
                cartRequest.getProducts()
        );
        switch (cartRequest.getUpdateStatus()) {
            case ADD:
                cart = CartTransferObject.addToCart(cart, cartMap);
                break;
            case UPDATE_ALL:
                cart.setProducts(cartMap);
                break;
            default:
                throw new AppException("UpdateStatus can not be null!");
        }
        return this.cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCartByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            throw new AppException("Invalid parameters value: userId(%s)", userId);
        }
        this.cartRepository.deleteCartByUserId(userId);
    }

}
