package com.github.ratel.controllers.interfaces;

import com.github.ratel.payload.request.CartRequest;
import com.github.ratel.payload.response.CartResponse;
import com.github.ratel.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@SecurityRequirement(name = "Authorization")
public interface CartController {

    @GetMapping("current-cart")
    ResponseEntity<CartResponse> findUserCartById(Principal principal);

    @GetMapping("/{cartId}")
    ResponseEntity<CartResponse> findUserCartById(@PathVariable long cartId);

    @PutMapping()
    ResponseEntity<CartResponse> updateUserCart(@RequestBody CartRequest cartRequest);

    @DeleteMapping("/user/{userId}")
    ResponseEntity<MessageResponse> deleteByUserId(@PathVariable long userId);

}
