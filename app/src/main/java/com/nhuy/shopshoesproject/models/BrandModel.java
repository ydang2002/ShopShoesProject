package com.nhuy.shopshoesproject.models;

import java.io.Serializable;

public class BrandModel implements Serializable {
    String brandId, brandName;
    boolean hidden;

    public BrandModel() {
    }

    public BrandModel(String brandId, String brandName, boolean hidden) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.hidden = hidden;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId='" + brandId + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
