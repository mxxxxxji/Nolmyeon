package com.example.nolmyeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.PopupActivity;
import com.example.nolmyeon.R;
import com.example.nolmyeon.activity.ImageViewerActivity;
import com.example.nolmyeon.model.Festival;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class FestivalViewAdapter extends RecyclerView.Adapter<FestivalViewAdapter.Holder> {

    private ArrayList<Festival> listData = new ArrayList<>();
    Context context;

    public FestivalViewAdapter(ArrayList<Festival> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }


    @NonNull
    @Override
    public FestivalViewAdapter.Holder onCreateViewHolder(@NonNull @NotNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_festival, parent, false);
        FestivalViewAdapter.Holder holder = new FestivalViewAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FestivalViewAdapter.Holder holder, int position) {
        holder.tv_title.setText(listData.get(position).getFstvlNm());
        holder.tv_address.setText(listData.get(position).getRdnmadr());
        holder.tv_date.setText(listData.get(position).getFstvlStartDate()+" ~ "+listData.get(position).getFstvlEndDate());
        holder.tv_phoneNumber.setText(listData.get(position).getPhoneNumber());

     //   Log.d("Festival", listData.get(position).getUrl().get(0));
        Log.d("Festival_", "" + listData.size());
        try{
           // Log.d("Festival_", listData.get(position).getUrl().get(0));
         //   Log.d("Festival_", !listData.get(position).getUrl().get(0).isEmpty()+"");
            if(!listData.get(position).getUrl().get(0).isEmpty()){
                Glide.with(context).load(listData.get(position).getUrl().get(0)).into(holder.iv_image);
            }else{
                Glide.with(context).load("https://da62mall.com/mobile/icon/noimage.gif").into(holder.iv_image);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_title;
        TextView tv_address;
        TextView tv_date;
        TextView tv_phoneNumber;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.item_image);
            tv_title = itemView.findViewById(R.id.item_title);
            tv_address = itemView.findViewById(R.id.item_rdnmadr);
            tv_date = itemView.findViewById(R.id.item_date);
            tv_phoneNumber = itemView.findViewById(R.id.item_phone_number);
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ImageViewerActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("position", position);
                        intent.putExtra("category", "festival");
                        context.startActivity(intent);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, PopupActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("position", position);
                        intent.putExtra("category", "festival");
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
