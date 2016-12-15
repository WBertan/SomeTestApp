package com.wbertan.sometestapp.controller;

import android.support.annotation.NonNull;

import com.wbertan.sometestapp.generics.GenericCallback;
import com.wbertan.sometestapp.model.Album;
import com.wbertan.sometestapp.repository.RepositoryAlbums;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by william.bertan on 15/12/2016.
 */

public class ControllerAlbums {
    private ControllerAlbums() {}

    private static class SingletonHolder {
        private static final ControllerAlbums INSTANCE = new ControllerAlbums();
    }

    public static ControllerAlbums getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getAlbums(@NonNull GenericCallback<List<Album>> aCallback) {
        Call<List<Album>> callAlbum = RepositoryAlbums.getInstance().getAlbumsCall();
        callAlbum.enqueue(new GetAlbumsCallback(aCallback));
    }

    class GetAlbumsCallback implements Callback<List<Album>> {
        private GenericCallback<List<Album>> mCallback;

        public GetAlbumsCallback(@NonNull GenericCallback<List<Album>> aCallback) {
            this.mCallback = aCallback;
        }
        @Override
        public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
            if(response.body() != null) {
                mCallback.onSuccess(response.body());
                return;
            }
            mCallback.onFailure(response.code(), response.message());
        }

        @Override
        public void onFailure(Call<List<Album>> call, Throwable t) {
            mCallback.onFailure(-1, t.getMessage());
        }
    }
}
