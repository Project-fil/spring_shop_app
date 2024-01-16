package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.interfaces.CartController;
import com.github.ratel.entity.Cart;
import com.github.ratel.entity.Product;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.payload.request.CartRequest;
import com.github.ratel.payload.response.CartResponse;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.services.CartService;
import com.github.ratel.services.ProductService;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.transfer_object.CartTransferObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/shop/")
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    private final UserService userService;

    private final ProductService productService;

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<CartResponse> getUserCartById(Principal principal) {
        return ResponseEntity.ok(CartTransferObject.fromCart(this.userService.getCurrentUser(principal).getCart()));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<CartResponse> getUserCartById(long cartId) {
        return ResponseEntity.ok(CartTransferObject.fromCart(this.cartService.findById(cartId)));
    }

    @Override
    @CrossOrigin
    @Transactional
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<CartResponse> updateUserCart(CartRequest cartRequest) {
        Cart cart = this.cartService.findById(cartRequest.getId());
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
        return ResponseEntity.ok(CartTransferObject.fromCart(this.cartService.update(cart)));
    }

    @Override
    public ResponseEntity<MessageResponse> deleteByUserId(long userId) {
        this.cartService.deleteCartByUserId(userId);
        return ResponseEntity.ok(new MessageResponse(
                "Cart with id " + userId + " deleted",
                new Date()
        ));
    }

}

