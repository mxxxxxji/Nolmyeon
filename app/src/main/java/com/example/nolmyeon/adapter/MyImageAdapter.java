package com.example.nolmyeon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.R;
import com.example.nolmyeon.model.MyData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyImageAdapter extends RecyclerView.Adapter<MyImageAdapter.Holder> {
    private ArrayList<MyData> listData;
    Context context;

    public MyImageAdapter(Context context, ArrayList<MyData> listData ) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_image, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        holder.mTextView.setText(listData.get(position).text);
        Glide.with(context).load(listData.get(position).uri).into(holder.mImageView);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() { return listData.size(); }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;
        CheckBox checkBox;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.image);
            mTextView = (TextView)itemView.findViewById(R.id.textview);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
