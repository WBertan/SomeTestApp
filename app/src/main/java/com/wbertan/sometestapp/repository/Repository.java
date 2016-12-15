package com.wbertan.sometestapp.repository;

/**
 * Created by william.bertan on 14/12/2016.
 */

public class Repository {
    private Repository() {}

    private static class SingletonHolder {
        private static final Repository INSTANCE = new Repository();
    }

    public static Repository getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getUrl() {
        return "http://jsonplaceholder.typicode.com/";
    }
}
