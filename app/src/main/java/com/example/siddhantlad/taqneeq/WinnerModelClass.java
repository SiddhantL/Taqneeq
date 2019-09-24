package com.example.siddhantlad.taqneeq;

public class WinnerModelClass {
    private int image;
    private String title;
    private String classroom;
    private int score;
    private String winner1;
    private String winner2;
    private String winner3;


    public WinnerModelClass(int image, String title,String classroom, int score,String winner1,String winner2,String winner3) {
        this.image = image;
        this.title = title;
        this.winner1 = winner1;
        this.winner2 = winner2;
        this.winner3 = winner3;
        this.classroom = classroom;
        this.score = score;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getWinner1() {
        return winner1;
    }
    public String getWinner2() {
        return winner2;
    }
    public String getWinner3() {
        return winner3;
    }

    public String getClassroom() {
        return classroom;
    }

    public int getScore() {
        return score;
    }
}