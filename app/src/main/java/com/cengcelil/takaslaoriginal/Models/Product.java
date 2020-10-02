package com.cengcelil.takaslaoriginal.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Product{
    private String status;
    private String title;
    private String description;
    private boolean isPriceable=true;
    private int price;
    private String address;
    private String uid;
    private CategoryItem category;
    @ServerTimestamp
    Date addedTime;
    private String documentId;

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    private String activityStatus = "active";
    public ArrayList<String> getIsLikedFrom() {
        return isLikedFrom;
    }

    public void setIsLikedFrom(ArrayList<String> isLikedFrom) {
        this.isLikedFrom = isLikedFrom;
    }

    private ArrayList<String> isLikedFrom;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Product() {
        isLikedFrom = new ArrayList<>();
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



}
