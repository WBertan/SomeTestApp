package com.wbertan.sometestapp.generics;

/**
 * Created by william.bertan on 15/12/2016.
 */

public interface GenericCallback<T> {
    void onSuccess(T aResponse);
    void onFailure(int aCode, String aMessage);
}
