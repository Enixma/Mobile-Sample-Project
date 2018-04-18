package com.enixma.sample.mobile.presentation.detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DetailModel implements Parcelable {

    private int id;
    private String brand;
    private String name;
    private String description;
    private String rating;
    private String price;

    public DetailModel(int id, String brand, String name, String description, String rating, String price) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.price = price;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.brand);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.rating);
        dest.writeString(this.price);
    }

    protected DetailModel(Parcel in) {
        this.id = in.readInt();
        this.brand = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.rating = in.readString();
        this.price = in.readString();
    }

    public static final Creator<DetailModel> CREATOR = new Creator<DetailModel>() {
        @Override
        public DetailModel createFromParcel(Parcel source) {
            return new DetailModel(source);
        }

        @Override
        public DetailModel[] newArray(int size) {
            return new DetailModel[size];
        }
    };
}
