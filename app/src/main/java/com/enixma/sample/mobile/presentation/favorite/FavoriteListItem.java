package com.enixma.sample.mobile.presentation.favorite;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class FavoriteListItem {
    private int id;
    private String brand;
    private String name;
    private String description;
    private String thumbImageURL;
    private String price;
    private String rating;
    private boolean isFavorite;

    public FavoriteListItem(int id, String brand, String name, String description, String thumbImageURL, String price, String rating, boolean isFavorite){
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.thumbImageURL = thumbImageURL;
        this.price = price;
        this.rating = rating;
        this.isFavorite = isFavorite;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbImageURL() {
        return thumbImageURL;
    }

    public void setThumbImageURL(String thumbImageURL) {
        this.thumbImageURL = thumbImageURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
