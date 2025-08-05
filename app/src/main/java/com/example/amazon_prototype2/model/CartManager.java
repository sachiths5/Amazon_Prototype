// CartManager.java
package com.example.amazon_prototype2.model;

import java.util.ArrayList;

public class CartManager {
    int pos;

    public CartManager(int pos) {
        this.pos = pos;
    }

    private static final ArrayList<CategoryDataModel.product_details> cartList = new ArrayList<>();

    public static void addToCart(CategoryDataModel.product_details product) {
        cartList.add(product);
    }

    public static ArrayList<CategoryDataModel.product_details> getCartList() {
        return cartList;
    }

    public static void clearCart() {
        cartList.clear();
    }
    public static void removecart(CategoryDataModel.product_details pos){
        cartList.remove(pos);
    }
}
