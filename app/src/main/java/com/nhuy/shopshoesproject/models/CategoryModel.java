package com.nhuy.shopshoesproject.models;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    String CategoryId,CategoryName;
    boolean hidden;
    public CategoryModel()
    {

    }

    public CategoryModel(String categoryId, String categoryName, boolean hidden) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                '}';
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

}
