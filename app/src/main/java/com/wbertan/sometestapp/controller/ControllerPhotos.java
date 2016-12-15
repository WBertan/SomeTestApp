package com.wbertan.sometestapp.controller;

import android.support.annotation.NonNull;

import com.wbertan.sometestapp.generics.GenericCallback;
import com.wbertan.sometestapp.model.Photo;
import com.wbertan.sometestapp.repository.RepositoryPhotos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by william.bertan on 15/12/2016.
 */

public class ControllerPhotos {
    private ControllerPhotos() {}

    private static class SingletonHolder {
        private static final ControllerPhotos INSTANCE = new ControllerPhotos();
    }

    public static ControllerPhotos getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getPhotos(@NonNull GenericCallback<List<Photo>> aCallback, long aAlbumId) {
        Call<List<Photo>> callPhoto = RepositoryPhotos.getInstance().getPhotosCall(aAlbumId);
        callPhoto.enqueue(new GetPhotosCallback(aCallback));
    }

    class GetPhotosCallback implements Callback<List<Photo>> {
        private GenericCallback<List<Photo>> mCallback;

        public GetPhotosCallback(@NonNull GenericCallback<List<Photo>> aCallback) {
            this.mCallback = aCallback;
        }
        @Override
        public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
            if(response.body() != null) {
                mCallback.onSuccess(response.body());
                return;
            }
            mCallback.onFailure(response.code(), response.message());
        }

        @Override
        public void onFailure(Call<List<Photo>> call, Throwable t) {
            mCallback.onFailure(-1, t.getMessage());
        }
    }
}
