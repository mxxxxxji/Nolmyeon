package com.example.nolmyeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.PopupActivity;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.activity.ImageViewerActivity;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Scrap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RuralViewAdapter extends RecyclerView.Adapter<RuralViewAdapter.Holder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private ArrayList<Rural> listData = new ArrayList<>();
    Context context;
    int[] flag;
    public RuralViewAdapter(Context context, ArrayList<Rural> ruralList) {
        this.listData = ruralList;
        this.context = context;
        flag = new int[listData.size()];
    }


    @NonNull
    @NotNull
    @Override
    public RuralViewAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rural,parent,false);
            //뷰홀더 객체 생성 하면서 뷰 객체를 전달하고
            RuralViewAdapter.Holder holder = new RuralViewAdapter.Holder(view);
            //그 뷰 홀더 객체 반환하기
            return holder;
        }else{
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            RuralViewAdapter.Holder holder = new RuralViewAdapter.Holder(view);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RuralViewAdapter.Holder holder, int position) {
        if(listData.get(position).getExprnPicUrl() != null){
            Log.d("Camping", "이미지 있음");
            Glide.with(context).load(listData.get(position).getUrl().get(0)).into(holder.iv_image);
        }else{
            Log.d("Camping", "이미지 없음");
            Glide.with(context).load("https://da62mall.com/mobile/icon/noimage.gif").into(holder.iv_image);
        }

        holder.tv_title.setText(listData.get(position).getExprnVilageNm());
        holder.tv_address.setText(listData.get(position).getRdnmadr());
        holder.tv_homepage.setText(listData.get(position).getHomepageUrl());
    }
    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    private void populateItemRows(Holder holder, int position) {
        String item = listData.get(position).getExprnVilageNm();
        holder.setItem(item);
    }
    @Override
    public int getItemViewType(int position) {
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_address;
        TextView tv_homepage;
        ImageView iv_image;
        ImageView iv_scrap;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.item_title);
            tv_address = itemView.findViewById(R.id.item_rdnmadr);
            tv_homepage = itemView.findViewById(R.id.item_homepage_url);
            iv_image = itemView.findViewById(R.id.item_image);
            iv_scrap = itemView.findViewById(R.id.item_scrap);
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ImageViewerActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("position", position);
                        intent.putExtra("category", "rural");
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
                        intent.putExtra("category", "rural");
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
                        deleteScrap(GlobalApplication.getUser_number(), "exhibition", listData.get(position).getExprnVilageNm());
                    }else{
                        Glide.with(context).load(R.drawable.scrap_orange).into(iv_scrap);//스크랩표시
                        flag[position] = 1;
                        Toast.makeText(context, "스크랩 되었습니다", Toast.LENGTH_LONG).show();
                        insertScrap(GlobalApplication.getUser_number(), "exhibition", listData.get(position).getExprnVilageNm());
                    }
                }
            });
        }

        public void setItem(String item) {
            tv_title.setText(item);
        }

    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
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
