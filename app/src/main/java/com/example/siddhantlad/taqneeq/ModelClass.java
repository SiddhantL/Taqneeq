package com.example.siddhantlad.taqneeq;

public class ModelClass {
    private int image;
    private String title;
    private String classroom;
    private int score;

    public ModelClass(int image, String title, String classroom, int score) {
        this.image = image;
        this.title = title;
        this.classroom = classroom;
        this.score = score;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getClassroom() {
        return classroom;
    }

    public int getScore() {
        return score;
    }
}