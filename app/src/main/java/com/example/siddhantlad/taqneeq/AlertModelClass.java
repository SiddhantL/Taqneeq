package com.example.siddhantlad.taqneeq;

public class AlertModelClass {
    private int image;
    private String title;
    private String classroom;
    private int score;
    private String alert;

    public AlertModelClass(String alert, String title, String classroom, int score,int image) {
        this.image = image;
        this.title = title;
        this.classroom = classroom;
        this.score = score;
        this.alert=alert;
    }

    public String getAlert() {
        return alert;
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
    public int getImage() {
        return image;
    }
}