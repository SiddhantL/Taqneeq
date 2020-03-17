package com.example.siddhantlad.taqneeq;

public class EventData {
   public String Adult,Cost,Date,Drinks,Food,Intro,Music,Name,Time,Venue;
    public EventData(){}
    //Constructor
    public EventData(String Adult,String Cost,String Date,String Drinks,String Food,String Intro,String Music,String Name,String Time,String Venue) {
        this.Adult = Adult;
        this.Cost = Cost;
        this.Drinks = Drinks;
        this.Food = Food;
        this.Intro = Intro;
        this.Music=Music;
        this.Name=Name;
        this.Time=Time;
        this.Venue=Venue;

//Below are All the getter and setter etc
    }

    public String getName() {
        return Name;
    }

    public String getCost() {
        return Cost;
    }

    public String getVenue() {
        return Venue;
    }

    public String getTime() {
        return Time;
    }

    public String getIntro() {
        return Intro;
    }

    public String getMusic() {
        return Music;
    }

    public String getFood() {
        return Food;
    }

    public String getDrinks() {
        return Drinks;
    }

    public String getAdult() {
        return Adult;
    }

    public String getDate() {
        return Date;
    }
}