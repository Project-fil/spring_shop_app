package com.github.ratel.controllers.impl;

import com.github.ratel.controllers.interfaces.CartController;
import com.github.ratel.payload.request.CartRequest;
import com.github.ratel.payload.response.CartResponse;
import com.github.ratel.payload.response.MessageResponse;
import com.github.ratel.services.CartService;
import com.github.ratel.utils.transfer_object.CartTransferObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

import static com.github.ratel.utils.ApiPathConstants.API_PREFIX;
import static com.github.ratel.utils.ApiPathConstants.CART;

@RestController
@RequestMapping(API_PREFIX + CART)
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<CartResponse> findUserCartById(Principal principal) {
        return ResponseEntity.ok(CartTransferObject.fromCart(this.cartService.findUserCartByUser(principal)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<CartResponse> findUserCartById(long cartId) {
        return ResponseEntity.ok(CartTransferObject.fromCart(this.cartService.findById(cartId)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
    public ResponseEntity<CartResponse> updateUserCart(CartRequest cartRequest) {
        return ResponseEntity.ok(CartTransferObject.fromCart(this.cartService.update(cartRequest)));
    }

    @Override
    @CrossOrigin("*")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<MessageResponse> deleteByUserId(long userId) {
        this.cartService.deleteCartByUserId(userId);
        return ResponseEntity.ok(new MessageResponse(
                "Cart with id " + userId + " deleted",
                new Date()
        ));
    }

}

