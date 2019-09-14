package com.example.siddhantlad.taqneeq;

public class ModelClass {
    private int image;
    private String title;

    public ModelClass(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}