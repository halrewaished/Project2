package com.example.project2.service;

import com.example.project2.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ArrayList<Product> products = new ArrayList<>();

    private final UserService userService;

    private final CartService cartService;

    private final MerchantStockService merchantStockService;

    private final PurchaseHistoryService purchaseHistoryService;

    private final CommentService commentService;

    public ArrayList<Product> getProducts(){

        return products;
    }

    public boolean isAddProduct(Product product){
        for (int i = 0; i < products.size() ; i++) {
            if(products.get(i).getId().equals(product.getId())){
                return false;
            }
        }
        products.add(product);
        return true;
    }

    public boolean isUpdateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(product.getId())){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }

    public boolean isDeleteProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(product.getId())){
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    public Integer addToCart(String userId, String productId){

        User currentUser = userService.getUsers(userId);
        Product currentProduct = getProducts(productId);

        if(currentUser == null){
            return -1;
        }
        if(currentProduct == null){
            return 0;
        }

        cartService.addProductToCart(currentUser,currentProduct);

        return 1;

    }

    public Integer removeFromCart(String userId, String productId){

        User currentUser = userService.getUsers(userId);
        Product currentProduct = getProducts(productId);

        if(currentUser == null){
            return -1;
        }
        if(currentProduct == null){
            return 0;
        }
        cartService.removeProductFromCart(currentUser,currentProduct);
        return 1;

    }

    public Integer buyProductDirectly(String userId, String productId, String merchantID){
        User currentUser = userService.getUsers(userId);
        Product currentProduct = getProducts(productId);
        MerchantStock currentMerchantStock = merchantStockService.getMerchantStock(merchantID);

        if(currentMerchantStock.getStock() == null){
            return -1;
        }
        if(currentUser.getBalance() < currentProduct.getPrice()){
            return 0;
        }
        if(!(currentMerchantStock.getProductId().equals(productId))){
            return 1;
        }

        Integer newStock = currentMerchantStock.getStock() -1;
        Integer newBalance = currentUser.getBalance() - currentProduct.getPrice();

        currentMerchantStock.setStock(newStock);
        currentUser.setBalance(newBalance);

        purchaseHistoryService.addPurchaseHistory(currentUser.getId(),currentProduct.getId(),currentProduct.getPrice());
        return 2;
    }

    public Product getProducts(String productId){
        for (Product product : products) {
            if(product.getId().equals(productId)){
                return product;
        }
        }
        return null;
    }

    public Integer getComments(String productId, String userId){
        User currentUser = userService.getUsers(userId);
        Product currentProduct = getProducts(productId);

        if(currentUser == null ){
            return -1;
        }
        if(currentProduct == null){
            return 0;
        }

        commentService.getComments(userId);
        return 1;
    }

    }
