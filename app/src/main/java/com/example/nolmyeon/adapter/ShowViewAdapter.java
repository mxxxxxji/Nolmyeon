package com.example.nolmyeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.PopupActivity;
import com.example.nolmyeon.R;
import com.example.nolmyeon.activity.ImageViewerActivity;
import com.example.nolmyeon.model.Show;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowViewAdapter extends RecyclerView.Adapter<ShowViewAdapter.Holder>{
    private ArrayList<Show> listData = new ArrayList<>();
    private String url = "";
    Context context;


    public ShowViewAdapter(Context context, ArrayList<Show> showsList) {
        this.context = context;
        this.listData = showsList;
    }

    @NonNull
    @NotNull
    @Override
    public ShowViewAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_show, parent, false);
        //뷰홀더 객체 생성 하면서 뷰 객체를 전달하고
        ShowViewAdapter.Holder holder = new ShowViewAdapter.Holder(view);
        //그 뷰 홀더 객체 반환하기
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewAdapter.Holder holder, int position) {
        if(listData.get(position).getUrl() != null){
            Glide.with(context).load(listData.get(position).getUrl().get(0)).into(holder.iv_image);
        }else{
            Glide.with(context).load("https://da62mall.com/mobile/icon/noimage.gif").into(holder.iv_image);
        }
        holder.tv_title.setText(listData.get(position).getEventNm());
        holder.tv_address.setText(listData.get(position).getOpar());
        holder.tv_date.setText(listData.get(position).getEventStartDate()+" ~ "+listData.get(position).getEventEndDate());
        holder.tv_phoneNumber.setText(listData.get(position).getPhoneNumber());
        //  holder.tv_info.setText(" ");
//        if(listData.get(position).getRstdeInfo() != null){
//            holder.tv_info.setText(listData.get(position).getRstdeInfo());
//        }else{
//            holder.tv_info.setText(" ");
//        }

    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_address;
        ImageView iv_image;
        TextView tv_date;
        TextView tv_phoneNumber;
        GradientDrawable drawable;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.item_image);
            tv_title = itemView.findViewById(R.id.item_title);
            tv_address = itemView.findViewById(R.id.item_rdnmadr);
            tv_date = itemView.findViewById(R.id.item_date);
            tv_phoneNumber =itemView.findViewById(R.id.item_phone_number);
            drawable = (GradientDrawable) context.getDrawable(R.drawable.background2);
            iv_image.setBackground(drawable);
            iv_image.setClipToOutline(true);

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ImageViewerActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("position", position);
                        intent.putExtra("category", "show");
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
                        intent.putExtra("category", "show");
                        context.startActivity(intent);
                    }
                }
            });
        }

    }
}
