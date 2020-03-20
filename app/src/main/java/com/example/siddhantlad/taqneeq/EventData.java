package com.example.siddhantlad.taqneeq;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventData {

    private String Adult,Cost,Date,Drinks,Food,Intro,Music,Time,Venue,ID,Name;
    public EventData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public EventData(String Adult,String Cost,String Date,String Drinks,String Food,String Intro,String Music,String Name,String Time,String Venue,String ID) {
        this.Adult =Adult;
        this.Cost =Cost;
        this.Date =Date;
        this.Drinks =Drinks;
        this.Food =Food;
        this.Intro =Intro;
        this.Music =Music;
        this.Name =Name;
        this.Time =Time;
        this.Venue =Venue;
        this.ID=ID;
    }

    public String getAdult() {
        return Adult;
    }

    public void setAdult(String adult) {
        Adult = adult;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDrinks() {
        return Drinks;
    }

    public void setDrinks(String drinks) {
        Drinks = drinks;
    }

    public String getFood() {
        return Food;
    }

    public void setFood(String food) {
        Food = food;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    public String getMusic() {
        return Music;
    }

    public void setMusic(String music) {
        Music = music;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
