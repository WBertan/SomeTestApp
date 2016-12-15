package com.wbertan.sometestapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wbertan.sometestapp.R;
import com.wbertan.sometestapp.adapters.AdapterAlbums;
import com.wbertan.sometestapp.controller.ControllerAlbums;
import com.wbertan.sometestapp.generics.GenericCallback;
import com.wbertan.sometestapp.model.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william.bertan on 14/12/2016.
 */

public class FragmentAlbums extends Fragment implements AdapterAlbums.ViewHolderAction, GenericCallback<List<Album>> {
    @BindView(R.id.recyclerViewAlbums)
    protected RecyclerView mRecyclerViewAlbums;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        ButterKnife.bind(this, view);
        mRecyclerViewAlbums.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        AdapterAlbums adapterAlbums = new AdapterAlbums(this);
        mRecyclerViewAlbums.setAdapter(adapterAlbums);
    }

    @Override
    public void onResume() {
        super.onResume();
        ControllerAlbums.getInstance().getAlbums(this);
    }

    @Override
    public void executeRecyclerItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setAction("com.wbertan.broadcast.ALBUM_SELECTED");
        intent.putExtra("albumId", ((AdapterAlbums)mRecyclerViewAlbums.getAdapter()).getSelectedItem().getId());
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onSuccess(List<Album> aResponse) {
        AdapterAlbums adapterAlbums = (AdapterAlbums) mRecyclerViewAlbums.getAdapter();
        adapterAlbums.addAll(aResponse);
    }

    @Override
    public void onFailure(int aCode, String aMessage) {
        Toast.makeText(getActivity(), "An error occured while reaching the albums!", Toast.LENGTH_SHORT).show();
    }
}
