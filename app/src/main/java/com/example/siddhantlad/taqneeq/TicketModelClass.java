package com.example.siddhantlad.taqneeq;

public class TicketModelClass {
    private String name;
    private String entering;
    private String date;
    private String type;
    private String divided;
    private String id;
    private String venue;
    private String time;

    public TicketModelClass(String name, String date, String entering, String type, String divided, String id, String venue, String time) {
        this.name=name;
        this.date = date;
        this.entering = entering;
        this.type = type;
        this.divided=divided;
        this.id=id;
        this.venue=venue;
        this.time=time;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public String getEntering() {
        return entering;
    }
    public String getType() {
        return type;
    }
    public String getDivided() {
        return divided;
    }
    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }
}