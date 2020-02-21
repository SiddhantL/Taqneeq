package com.example.siddhantlad.taqneeq;

public class ModelClassTicket {
    private int image;
    private String id,enters,status,cost,name,eventName;

    public ModelClassTicket(int image, String id, String name, String enters, String cost, String status, String eventName) {
        this.image = image;
        this.cost=cost;
        this.name=name;
        this.enters=enters;
        this.status=status;
        this.id=id;
        this.eventName=eventName;
    }

    public int getImage() {
        return image;
    }
    public String getId() {
        return id;
    }

    public String getCost() {
        return cost;
    }

    public String getEnters() {
        return enters;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getEventName() {
        return eventName;
    }
}