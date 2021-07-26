package com.example.nolmyeon.adapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import worldline.com.foldablelayout.FoldableLayout;
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<String> mItems;
    Context context;
    public PhotoAdapter(Context context,List<String> mItems) {
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_image, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoViewHolder holder, int position) {
        Glide.with(context).load(mItems.get(position)).into(holder.image_iv);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView image_iv;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.image_iv);
        }
    }
}