package com.ibrocorp.onlinestore;

import android.widget.TextView;

import java.util.ArrayList;

public final class GlobalClass {

    public static int cartItemCounter=0;
    public static ArrayList<Product> cartLists=new ArrayList<>();
    public static TextView notificationCounter;

    public static ArrayList<Product> getCartLists() {
        return cartLists;
    }

    public static void setModel(Product m){
        cartLists.add(m);
    }

    public static int getCartItemCounter() {
        return cartItemCounter;
    }

    public static void setCartItemCounter(int cartItemCounter) {
        GlobalClass.cartItemCounter = cartItemCounter;
    }

    public static void deleteModel(int index){
        cartLists.remove(index);
        cartItemCounter--;

    }

    public static void clearCartItemCounter() {
        cartItemCounter=0;
    }
}
