package com.cengcelil.takaslaoriginal.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Parcelable, Serializable {
    private String status;
    private String title;
    private String description;
    private boolean isPriceable=true;
    private int price;
    private String address;
    private String uid;
    private CategoryItem category;
    protected Product(Parcel in) {
        status = in.readString();
        title = in.readString();
        description = in.readString();
        isPriceable = in.readByte() != 0;
        price = in.readInt();
        address = in.readString();
        uid = in.readString();
        category = in.readParcelable(CategoryItem.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Product() {
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPriceable() {
        return isPriceable;
    }

    public void setPriceable(boolean priceable) {
        isPriceable = priceable;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CategoryItem getCategory() {
        return category;
    }

    public void setCategory(CategoryItem category) {
        this.category = category;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeByte((byte) (isPriceable ? 1 : 0));
        parcel.writeInt(price);
        parcel.writeString(address);
        parcel.writeString(uid);
        parcel.writeParcelable(category, i);
    }

    @Override
    public String toString() {
        return "Product{" +
                "status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPriceable=" + isPriceable +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", uid='" + uid + '\'' +
                ", category=" + category +
                '}';
    }
}
