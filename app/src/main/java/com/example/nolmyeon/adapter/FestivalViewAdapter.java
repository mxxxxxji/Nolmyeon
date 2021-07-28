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
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.PopupActivity;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.activity.ImageViewerActivity;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Scrap;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalViewAdapter extends RecyclerView.Adapter<FestivalViewAdapter.Holder> {

    private ArrayList<Festival> listData = new ArrayList<>();
    Context context;
    int[] flag;

    public FestivalViewAdapter(ArrayList<Festival> listData, Context context) {
        this.listData = listData;
        this.context = context;
        flag = new int[listData.size()];
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
        ImageView iv_scrap;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.item_image);
            tv_title = itemView.findViewById(R.id.item_title);
            tv_address = itemView.findViewById(R.id.item_rdnmadr);
            tv_date = itemView.findViewById(R.id.item_date);
            tv_phoneNumber = itemView.findViewById(R.id.item_phone_number);
            iv_scrap = itemView.findViewById(R.id.item_scrap);
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
            iv_scrap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    getScrap();
                    if(flag[position]==1){//이미 스크랩된 아이템
                        Glide.with(context).load(R.drawable.scrap_orange2).into(iv_scrap);//스크랩표시
                        flag[position] = 0;
                        Toast.makeText(context, "스크랩이 취소 되었습니다", Toast.LENGTH_LONG).show();
                        deleteScrap(GlobalApplication.getUser_number(), "exhibition", listData.get(position).getFstvlNm());
                    }else{
                        Glide.with(context).load(R.drawable.scrap_orange).into(iv_scrap);//스크랩표시
                        flag[position] = 1;
                        Toast.makeText(context, "스크랩 되었습니다", Toast.LENGTH_LONG).show();
                        insertScrap(GlobalApplication.getUser_number(), "exhibition", listData.get(position).getFstvlNm());
                    }
                }
            });

        }
    }
    public void getScrap(){
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Scrap>> call = retrofitClient.service.getScrap(GlobalApplication.getUser_number());
        call.enqueue(new Callback<ArrayList<Scrap>>() {
            @Override
            public void onResponse(Call<ArrayList<Scrap>> call, Response<ArrayList<Scrap>> response) {
                if(response.isSuccessful() && response.body() != null){
                    GlobalApplication.setScrapArrayList(response.body());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Scrap>> call, Throwable t) {
                Log.d("TAG_SCRAP", t.getMessage());
            }
        });
    }
    public void insertScrap(long number, String category, String title){
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<String> call = retrofitClient.service.insertScrap(number, category, title);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("TAG_SCRAP", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG_SCRAP", t.getMessage());
            }
        });
    }
    public void deleteScrap(long number, String category, String title){
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<String> call = retrofitClient.service.deleteScrap(number, category, title);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("TAG_SCRAP", response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG_SCRAP", t.getMessage());
            }
        });
    }
}
