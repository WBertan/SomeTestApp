package com.wbertan.sometestapp.repository;

import com.wbertan.sometestapp.model.Album;
import com.wbertan.sometestapp.rest.service.RestServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by william.bertan on 14/12/2016.
 */

public class RepositoryAlbums {
    private RepositoryAlbums() {}

    private static class SingletonHolder {
        private static final RepositoryAlbums INSTANCE = new RepositoryAlbums();
    }

    public static RepositoryAlbums getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Call<List<Album>> getAlbumsCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Repository.getInstance().getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestServices restServices = retrofit.create(RestServices.class);

        return restServices.listAlbums();
    }
}
