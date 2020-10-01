package com.cengcelil.takaslaoriginal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CategoryItem implements Parcelable, Serializable {
    private int image_id;

    protected CategoryItem(Parcel in) {
        image_id = in.readInt();
        name = in.readString();
        category_id = in.readInt();
    }

    public CategoryItem() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image_id);
        dest.writeString(name);
        dest.writeInt(category_id);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getCategory_id() {
        return category_id;
    }

    private String name;

    public CategoryItem(int image_id, String name, int category_id) {
        this.image_id = image_id;
        this.name = name;
        this.category_id = category_id;
    }

    private int category_id;


    public int getImage_id() {
        return image_id;
    }

    public String getName() {
        return name;
    }
}
