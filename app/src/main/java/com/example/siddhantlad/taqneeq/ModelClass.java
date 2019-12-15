package com.example.siddhantlad.taqneeq;

public class ModelClass {
    private int image;
    private String title;
    private String venue,date,time,adult,drinks,music,food,intro;
    private int cost;

    public ModelClass(int image, String title, String venue, int cost,String date,String time,String adult, String drinks, String music,String food,String intro) {
        this.image = image;
        this.title = title;
        this.venue = venue;
        this.cost = cost;
        this.date=date;
        this.time=time;
        this.adult=adult;
        this.drinks=drinks;
        this.music=music;
        this.food=food;
        this.intro=intro;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getVenue() {
        return venue;
    }

    public int getCost() {
        return cost;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }

    public String getAdult() {
        return adult;
    }

    public String getDrinks() {
        return drinks;
    }

    public String getFood() {
        return food;
    }

    public String getMusic() {
        return music;
    }

    public String getIntro() {
        return intro;
    }
}