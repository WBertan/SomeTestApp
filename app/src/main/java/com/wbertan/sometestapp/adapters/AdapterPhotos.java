package com.wbertan.sometestapp.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wbertan.sometestapp.R;
import com.wbertan.sometestapp.model.Photo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by william.bertan on 14/12/2016.
 */

public class AdapterPhotos extends RecyclerView.Adapter<AdapterPhotos.ViewHolder> {
    private List<Photo> mDataset;
    private ViewHolderAction mViewHolderAction;
    private int mSelectedPosition = -1;

    public AdapterPhotos() {
        mDataset = new ArrayList<>();
    }

    public AdapterPhotos(ViewHolderAction viewHolderAction) {
        this();
        this.mViewHolderAction = viewHolderAction;
    }

    public void add(Photo Photo) {
        mDataset.add(Photo);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void addAll(Collection<Photo> collection) {
        mDataset.addAll(collection);
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public Photo getSelectedItem() {
        if(mSelectedPosition > -1) {
            return mDataset.get(mSelectedPosition);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : -1;
    }

    @Override
    public AdapterPhotos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photo_item, parent, false);
        return new ViewHolder(view);
    }

    public interface ViewHolderAction {
        void executeRecyclerItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView mCardView;
        @BindView(R.id.imageViewPhotoThumbnail)
        ImageView mImageViewPhotoThumbnail;
        @BindView(R.id.textViewPhotoTitle)
        TextView mTextViewPhotoTitle;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Photo photo = mDataset.get(position);
        holder.mTextViewPhotoTitle.setText(photo.getTitle());
        Glide.with(holder.itemView.getContext())
             .load(photo.getThumbnailUrl())
             .error(android.R.drawable.ic_menu_report_image)
             .placeholder(android.R.drawable.progress_indeterminate_horizontal)
             .crossFade()
             .into(holder.mImageViewPhotoThumbnail);
        holder.mCardView.setCardBackgroundColor(mSelectedPosition == position ? Color.LTGRAY : Color.WHITE);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedPosition = position;
                notifyDataSetChanged();
                if(mViewHolderAction != null) {
                    mViewHolderAction.executeRecyclerItemClick(view, position);
                }
            }
        });
    }
}