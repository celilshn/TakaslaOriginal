package com.cengcelil.takaslaoriginal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CategoryItem implements Parcelable, Serializable {
    private int image_id;
    private String name;

    protected CategoryItem(Parcel in) {
        image_id = in.readInt();
        name = in.readString();
    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };

    public int getImage_id() {
        return image_id;
    }

    public String getName() {
        return name;
    }

    public CategoryItem(int image_id, String name) {
        this.image_id = image_id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image_id);
        parcel.writeString(name);
    }
}
