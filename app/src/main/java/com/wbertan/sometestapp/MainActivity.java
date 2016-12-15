package com.wbertan.sometestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.wbertan.sometestapp.fragments.FragmentAlbums;
import com.wbertan.sometestapp.fragments.FragmentPhotoViewer;
import com.wbertan.sometestapp.fragments.FragmentPhotos;

public class MainActivity extends AppCompatActivity {
    MainBroadcastReceiver mainBroadcastReceiver = null;

    class MainBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.wbertan.broadcast.ALBUM_SELECTED")) {
                FragmentPhotos fragmentPhotos = new FragmentPhotos();
                fragmentPhotos.setArguments(intent.getExtras());
                setFragment(fragmentPhotos);
            } else if(intent.getAction().equals("com.wbertan.broadcast.PHOTO_SELECTED")) {
                FragmentPhotoViewer fragmentPhotoViewer = new FragmentPhotoViewer();
                fragmentPhotoViewer.setArguments(intent.getExtras());
                setFragment(fragmentPhotoViewer);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(new FragmentAlbums());
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wbertan.broadcast.ALBUM_SELECTED");
        intentFilter.addAction("com.wbertan.broadcast.PHOTO_SELECTED");
        mainBroadcastReceiver = new MainBroadcastReceiver();
        registerReceiver(mainBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        if(mainBroadcastReceiver != null) {
            unregisterReceiver(mainBroadcastReceiver);
        }
        super.onPause();
    }

    public RecyclerView.Adapter getAdapter() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAlbums);
        if(recyclerView != null && recyclerView.getAdapter() != null) {
            return recyclerView.getAdapter();
        }
        return null;
    }

    private void setFragment(Fragment aFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (fragmentManager.getFragments() != null) {
            fragmentTransaction.addToBackStack(null);
        }
        Fragment fragmentExistente = fragmentManager.findFragmentByTag(aFragment.getClass().getSimpleName());
        if(fragmentExistente != null) {
            fragmentTransaction.replace(R.id.theFrameLayout, aFragment, aFragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.add(R.id.theFrameLayout, aFragment, aFragment.getClass().getSimpleName());
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }
}
