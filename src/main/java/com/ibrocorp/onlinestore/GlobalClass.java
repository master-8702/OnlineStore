package com.ibrocorp.onlinestore;

import java.util.ArrayList;

public final class GlobalClass {

    public static int cartItemCounter=0;
    public static ArrayList<MainModel> cartLists=new ArrayList<>();
    public MainModel mod;

    public static ArrayList<MainModel> getCartLists() {
        return cartLists;
    }

    public static void setModel(MainModel m){
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
