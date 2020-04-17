package com.example.catalinadinu.eventive.Clase;

public class AppRating {
    private float rating;
    private String user;

    public AppRating() {

    }

    public AppRating(float rating, String user) {
        this.rating = rating;
        this.user = user;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + ", User: " + user;
    }
}
