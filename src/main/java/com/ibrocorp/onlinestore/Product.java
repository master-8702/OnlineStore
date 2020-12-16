package com.ibrocorp.onlinestore;
// This is the Main model for our app .. the product class
//here we decide the characteristics (attributes) of our object or our product

// here the attributes of the Product (MainModel) class
public class Product {
    int id;
    String nom;
    Double price;
    String currency;
    String imageUrl;

    //here a constructor to instanciate our products initial value
    public Product(int id, String nom, Double price, String currency, String imageUrl){
        this.id=id;
        this.nom=nom;
        this.price=price;
        this.currency=currency;
        this.imageUrl=imageUrl;
    }

    //and here some setter and getter methods for each attributes of the product.
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
