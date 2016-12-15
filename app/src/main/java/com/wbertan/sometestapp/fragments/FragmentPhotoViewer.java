package com.wbertan.sometestapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wbertan.sometestapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william.bertan on 14/12/2016.
 */

public class FragmentPhotoViewer extends Fragment {
    @BindView(R.id.imageViewPhoto)
    ImageView imageViewPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_viewer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundleArguments = getArguments();
        if(bundleArguments != null && bundleArguments.containsKey("photoUrl")) {
            Glide.with(getActivity())
                 .load(bundleArguments.getString("photoUrl"))
                 .error(android.R.drawable.ic_menu_report_image)
                 .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                 .crossFade()
                 .into(imageViewPhoto);
        }
    }
}
