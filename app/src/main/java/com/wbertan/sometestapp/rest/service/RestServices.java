package com.wbertan.sometestapp.rest.service;

import com.wbertan.sometestapp.model.Album;
import com.wbertan.sometestapp.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by william.bertan on 14/12/2016.
 */

public interface RestServices {
    @GET("albums")
    Call<List<Album>> listAlbums();

    @GET("albums/{albumId}/photos")
    Call<List<Photo>> listAlbumsPhotos(@Path("albumId") long albumId);
}
