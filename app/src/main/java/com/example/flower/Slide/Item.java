package com.example.flower.Slide;

public class Item {
    private String description;
    private String imageurl;

    public Item() {
    }

    public Item(String description, String imageurl) {
        this.description = description;
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
