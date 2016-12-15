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
import com.wbertan.sometestapp.adapters.AdapterPhotos;
import com.wbertan.sometestapp.controller.ControllerPhotos;
import com.wbertan.sometestapp.generics.GenericCallback;
import com.wbertan.sometestapp.model.Photo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william.bertan on 14/12/2016.
 */

public class FragmentPhotos extends Fragment implements AdapterPhotos.ViewHolderAction, GenericCallback<List<Photo>> {
    @BindView(R.id.recyclerViewPhotos)
    protected RecyclerView mRecyclerViewPhotos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
        mRecyclerViewPhotos.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        AdapterPhotos adapterPhotos = new AdapterPhotos(this);
        mRecyclerViewPhotos.setAdapter(adapterPhotos);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundleArguments = getArguments();
        if(bundleArguments != null && bundleArguments.containsKey("albumId")) {
            ControllerPhotos.getInstance().getPhotos(this, bundleArguments.getLong("albumId"));
        }
    }

    @Override
    public void executeRecyclerItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setAction("com.wbertan.broadcast.PHOTO_SELECTED");
        intent.putExtra("photoUrl", ((AdapterPhotos)mRecyclerViewPhotos.getAdapter()).getSelectedItem().getUrl());
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onSuccess(List<Photo> aResponse) {
        AdapterPhotos adapterPhotos = (AdapterPhotos) mRecyclerViewPhotos.getAdapter();
        adapterPhotos.addAll(aResponse);
    }

    @Override
    public void onFailure(int aCode, String aMessage) {
        Toast.makeText(getActivity(), "An error occured while reaching the Photos!", Toast.LENGTH_SHORT).show();
    }
}
