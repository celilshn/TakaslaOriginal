package com.cengcelil.takaslaoriginal.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.File;

public class CapturedItem implements Parcelable {

    File file;
    private boolean dragEnable;
    private boolean dropEnable;
    protected CapturedItem(Parcel in) {
    }

    public static final Creator<CapturedItem> CREATOR = new Creator<CapturedItem>() {
        @Override
        public CapturedItem createFromParcel(Parcel in) {
            return new CapturedItem(in);
        }

        @Override
        public CapturedItem[] newArray(int size) {
            return new CapturedItem[size];
        }
    };

    public boolean isDragEnable() {
        return dragEnable;
    }

    public void setDragEnable(boolean dragEnable) {
        this.dragEnable = dragEnable;
    }

    public boolean isDropEnable() {
        return dropEnable;
    }

    public void setDropEnable(boolean dropEnable) {
        this.dropEnable = dropEnable;
    }
    public File getFile() {
        return file;
    }

    public CapturedItem(File file) {
        this.file = file;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
