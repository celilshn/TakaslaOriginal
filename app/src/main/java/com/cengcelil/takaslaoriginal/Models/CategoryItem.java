package com.cengcelil.takaslaoriginal.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryItem implements Parcelable {
    private int image_id;
    private String name;

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

    }
}
