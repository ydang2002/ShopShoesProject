package com.nhuy.shopshoesproject.models;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    String CategoryId,CategoryName;
    public CategoryModel()
    {

    }
    public CategoryModel(String CategoryId, String CategoryName)
    {
        this.CategoryId= CategoryId;
        this.CategoryName= CategoryName;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                '}';
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
