package com.redis.resdis_api.controller;

import com.redis.resdis_api.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping ("/cart")
public class ShoppingCartController
{
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping ("/add")
    public ResponseEntity<String> addItemToCart(@RequestParam String userId, @RequestParam String productId, @RequestParam int quantity)
    {
        shoppingCartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok("Item added to cart");
    }

    @DeleteMapping ("/remove")
    public ResponseEntity<String> removeItemFromCart(@RequestParam String userId, @RequestParam String productId)
    {
        shoppingCartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @GetMapping ("/items")
    public ResponseEntity<Map<String, Integer>> getCartContents(@RequestParam String userId)
    {
        Map<String, Integer> cartContents = shoppingCartService.getCartContents(userId);
        return ResponseEntity.ok(cartContents);
    }

    @DeleteMapping ("/clear")
    public ResponseEntity<String> clearCart(@RequestParam String userId)
    {
        shoppingCartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify()
    {
        return ResponseEntity.ok("Successful..");
    }
}
