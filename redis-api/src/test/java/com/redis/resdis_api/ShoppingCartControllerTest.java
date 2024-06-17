package com.redis.resdis_api;

import com.redis.resdis_api.controller.ShoppingCartController;
import com.redis.resdis_api.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (ShoppingCartController.class)
public class ShoppingCartControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setUp()
    {
        // Set up any common preconditions here
    }

    @Test
    void addItemToCart() throws Exception
    {
        mockMvc.perform(post("/cart/add")
                .param("userId", "user1")
                .param("productId", "product1")
                .param("quantity", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item added to cart"));

        verify(shoppingCartService).addItemToCart("user1", "product1", 2);
    }

    @Test
    void removeItemFromCart() throws Exception
    {
        mockMvc.perform(delete("/cart/remove")
                .param("userId", "user1")
                .param("productId", "product1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item removed from cart"));

        verify(shoppingCartService).removeItemFromCart("user1", "product1");
    }

    @Test
    void getCartContents() throws Exception
    {
        Map<String, Integer> cartContents = new HashMap<>();
        cartContents.put("product1", 2);
        when(shoppingCartService.getCartContents(anyString())).thenReturn(cartContents);

        mockMvc.perform(get("/cart/items")
                .param("userId", "user1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"product1\":2}"));

        verify(shoppingCartService).getCartContents("user1");
    }

    @Test
    void clearCart() throws Exception
    {
        mockMvc.perform(delete("/cart/clear")
                .param("userId", "user1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cart cleared"));

        verify(shoppingCartService).clearCart("user1");
    }
}