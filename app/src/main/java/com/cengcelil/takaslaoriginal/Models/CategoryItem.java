package com.cengcelil.takaslaoriginal.Models;

public class CategoryItem {
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
}
