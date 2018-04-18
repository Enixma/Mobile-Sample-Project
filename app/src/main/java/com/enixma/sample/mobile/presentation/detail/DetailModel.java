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

    public DetailModel(int id, String brand, String name, String description) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.description = description;
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
    }

    public DetailModel() {
    }

    protected DetailModel(Parcel in) {
        this.id = in.readInt();
        this.brand = in.readString();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<DetailModel> CREATOR = new Parcelable.Creator<DetailModel>() {
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
