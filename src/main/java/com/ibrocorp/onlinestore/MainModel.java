package com.ibrocorp.onlinestore;

public class MainModel {
    int id;
    String nom;
    Double price;
    String currency;
    String imageUrl;
    public MainModel(int id,String nom,Double price,String currency,String imageUrl){
        this.id=id;
        this.nom=nom;
        this.price=price;
        this.currency=currency;
        this.imageUrl=imageUrl;
    }

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
