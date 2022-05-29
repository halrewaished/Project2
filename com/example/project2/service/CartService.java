package com.example.project2.service;

import com.example.project2.model.Cart;
import com.example.project2.model.Product;
import com.example.project2.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ArrayList<Cart> carts = new ArrayList<>();

    public ArrayList<Cart> getCarts(){

        return carts;
    }

    public boolean addProductToCart(User user,Product product) {
        for ( Cart cart : carts) {
            if(!(cart.getProducts().equals(product.getId()) && cart.getUserId().equals(user.getId()))){
                carts.add(cart);
                return true;
                }
            }
       return false;
    }

    public boolean removeProductFromCart(User user,Product product) {
        for ( Cart cart : carts) {
            if(cart.getProducts().equals(product.getId()) && cart.getUserId().equals(user.getId())){
                return carts.remove(cart);
            }
        }
        return false;
    }
}