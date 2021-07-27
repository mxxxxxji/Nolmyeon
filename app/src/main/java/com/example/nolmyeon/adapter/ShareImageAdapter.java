package com.example.nolmyeon.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.activity.ShareActivity;
import com.example.nolmyeon.model.MyData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareImageAdapter extends RecyclerView.Adapter<ShareImageAdapter.Holder> {
    private ArrayList<MyData> listData;
    Context context;

    public ShareImageAdapter(Context context, ArrayList<MyData> listData) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        holder.mTextView.setText(listData.get(position).text);
        Glide.with(context).load(listData.get(position).uri).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.image);
            mTextView = (TextView)itemView.findViewById(R.id.textview);
      }
    }

}

