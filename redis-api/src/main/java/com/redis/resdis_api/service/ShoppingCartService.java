package com.redis.resdis_api.service;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ShoppingCartService
{
    private static final String CART_PREFIX = "cart:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Integer> hashOperations;
    private static final Logger LOG = Logger.getLogger(ShoppingCartService.class.getName());

    @Autowired
    public ShoppingCartService(RedisTemplate<String, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void addItemToCart(String userId, String productId, int quantity)
    {
        try
        {
            String cartKey = CART_PREFIX + userId;
            hashOperations.put(cartKey, productId, quantity);
        }
        catch (RedisConnectionFailureException ex)
        {
            handleConnectionFailureException(ex);
        }
    }

    public void removeItemFromCart(String userId, String productId)
    {
        String cartKey = CART_PREFIX + userId;
        try
        {
            hashOperations.delete(cartKey, productId);
        }
        catch (RedisConnectionFailureException ex)
        {
            handleConnectionFailureException(ex);
        }
    }

    public Map<String, Integer> getCartContents(String userId)
    {
        String cartKey = CART_PREFIX + userId;
        try
        {
            return hashOperations.entries(cartKey);
        }
        catch (RedisConnectionFailureException ex)
        {
            handleConnectionFailureException(ex);
            return new HashMap<>();
        }
    }

    public void clearCart(String userId)
    {
        String cartKey = CART_PREFIX + userId;
        try
        {
            redisTemplate.delete(cartKey);
        }
        catch (RedisConnectionFailureException ex)
        {
            handleConnectionFailureException(ex);
        }
    }

    private void handleConnectionFailureException(Exception exception)
    {

        LOG.log(Level.SEVERE, "Redis server is down", exception);
        throw new RedisConnectionFailureException(exception.getMessage(), exception.getCause());
    }
}
