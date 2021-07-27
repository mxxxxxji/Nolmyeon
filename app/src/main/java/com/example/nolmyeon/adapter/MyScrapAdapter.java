package com.example.nolmyeon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolmyeon.R;
import com.example.nolmyeon.model.MyData;
import com.example.nolmyeon.model.Scrap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyScrapAdapter extends RecyclerView.Adapter<MyScrapAdapter.Holder> {
    private ArrayList<Scrap> listData = new ArrayList<>();
    Context context;
    public MyScrapAdapter(Context context, ArrayList<Scrap> listData) {
        this.listData = listData;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_scrap, parent, false);
        MyScrapAdapter.Holder holder = new MyScrapAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        holder.tv_title.setText(listData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_image;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.item_title);
            iv_image = itemView.findViewById(R.id.item_image);
        }
    }
}
