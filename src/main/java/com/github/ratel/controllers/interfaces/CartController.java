package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.CartRequest;
import com.github.ratel.payload.response.CartResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@SecurityRequirement(name = "Authorization")
public interface CartController {

    @GetMapping("cart")
    ResponseEntity<CartResponse> getUserCartById(Principal principal);

    @GetMapping("cart/{cartId}")
    ResponseEntity<CartResponse> getUserCartById(@PathVariable long cartId);

    @PutMapping(value = "cart/update/")
    ResponseEntity<CartResponse> updateUserCart(@RequestBody CartRequest cartRequest);

}
