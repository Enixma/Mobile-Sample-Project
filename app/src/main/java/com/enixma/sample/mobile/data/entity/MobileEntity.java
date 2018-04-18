package com.enixma.sample.mobile.data.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileEntity extends RealmObject{

    @PrimaryKey
    private int id;
    private String brand;
    private String name;
    private String thumbImageURL;
    private String description;
    private double rating;
    private double price;
    private boolean isFavorite;
    RealmList<MobileImageEntity> mobileImageEntities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbImageURL() {
        return thumbImageURL;
    }

    public void setThumbImageURL(String thumbImageURL) {
        this.thumbImageURL = thumbImageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


    public RealmList<MobileImageEntity> getMobileImageEntities() {
        return mobileImageEntities;
    }

    public void setMobileImageEntities(RealmList<MobileImageEntity> mobileImageEntities) {
        this.mobileImageEntities = mobileImageEntities;
    }
}
