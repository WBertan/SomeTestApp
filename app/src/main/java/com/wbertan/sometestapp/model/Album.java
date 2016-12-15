package com.wbertan.sometestapp.model;

/**
 * Created by william.bertan on 14/12/2016.
 */
public class Album {
    private long id;
    private String title;

    public Album(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
