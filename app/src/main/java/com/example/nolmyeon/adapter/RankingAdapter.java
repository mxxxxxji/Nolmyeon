package com.example.nolmyeon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolmyeon.R;
import com.example.nolmyeon.Ranker;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.Holder>{

    private ArrayList<Ranker> listData = new ArrayList<>();

    public RankingAdapter(ArrayList<Ranker> rankerArrayList) {
        this.listData = rankerArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public RankingAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranker, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RankingAdapter.Holder holder, int position) {
        holder.rank_tv.setText((position+1)+"위");
        holder.user_tv.setText(listData.get(position).getName());
        holder.stamp_tv.setText(listData.get(position).getCount()+"개");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView rank_tv;
        TextView user_tv;
        TextView stamp_tv;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            rank_tv = itemView.findViewById(R.id.rank_tv);
            user_tv = itemView.findViewById(R.id.user_tv);
            stamp_tv = itemView.findViewById(R.id.stamp_tv);
        }
    }
}
