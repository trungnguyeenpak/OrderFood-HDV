package com.trungnguyeen.orderfood.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by trungnguyeen on 4/12/18.
 */

public class Food {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("photoList")
    @Expose
    private Object photoList;

    /**
     * No args constructor for use in serialization
     *
     */
    public Food() {
    }

    /**
     *
     * @param id
     * @param category
     * @param price
     * @param description
     * @param name
     * @param image
     * @param photoList
     */
    public Food(Integer id, String name, String description, Double price, String image, Category category, Object photoList) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.photoList = photoList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Object getPhotoList() {
        return photoList;
    }

    public void setPhotoList(Object photoList) {
        this.photoList = photoList;
    }

}