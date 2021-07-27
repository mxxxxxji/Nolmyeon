package com.example.nolmyeon.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.OnScrapClickListener;
import com.example.nolmyeon.PopupActivity;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.activity.ImageViewerActivity;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.R;
import com.example.nolmyeon.model.Scrap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExhibitionViewAdapter extends RecyclerView.Adapter<ExhibitionViewAdapter.Holder>implements OnScrapClickListener{
    private ArrayList<Exhibition> listData = new ArrayList<>();
    private String url = "";
    Context context;
    int[] flag;
    OnScrapClickListener listener;
    public ExhibitionViewAdapter(Context context, ArrayList<Exhibition> exhibitionsList) {
        this.context = context;
        this.listData = exhibitionsList;
        flag = new int[listData.size()];
    }

    public void setOnScrapClickListener(OnScrapClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ExhibitionViewAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_exhibition, parent, false);
        //뷰홀더 객체 생성 하면서 뷰 객체를 전달하고
        ExhibitionViewAdapter.Holder holder = new ExhibitionViewAdapter.Holder(view);
        //그 뷰 홀더 객체 반환하기
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ExhibitionViewAdapter.Holder holder, int position) {
        if(listData.get(position).getUrl() != null){
            Glide.with(context).load(listData.get(position).getUrl().get(0)).into(holder.iv_image);
        }else{
            Glide.with(context).load("https://da62mall.com/mobile/icon/noimage.gif").into(holder.iv_image);
        }
        holder.tv_title.setText(listData.get(position).getTitle());
        holder.tv_address.setText(listData.get(position).getRdnmadr());
        holder.tv_phoneNumber.setText(listData.get(position).getPhoneNumber());
        Log.d("TAG_SCRAP_SIZE",GlobalApplication.getScrapArrayList().size()+"");
        if(GlobalApplication.getScrapArrayList().size() != 0){
            for(int i=0; i< GlobalApplication.getScrapArrayList().size(); i++) {
                Log.d("TAG_SCRAP",listData.get(position).getTitle()+", "+GlobalApplication.getScrapArrayList().get(i).getTitle());
                if(listData.get(position).getTitle().equals(GlobalApplication.getScrapArrayList().get(i).getTitle())){
                    Glide.with(context).load(R.drawable.scrap_orange).into(holder.iv_scrap);
                    flag[position] = 1;
                }
            }
        }

    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onExhibitionScrapClick(Holder holder, View view, int position) {
        if(listener!=null){
            listener.onExhibitionScrapClick(holder,view,position);
        }
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_address;
        TextView tv_phoneNumber;
        ImageView iv_image;
        ImageView iv_scrap;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.item_image);
            tv_title = itemView.findViewById(R.id.item_title);
            tv_address = itemView.findViewById(R.id.item_rdnmadr);
            tv_phoneNumber = itemView.findViewById(R.id.item_phoneNumber);
            iv_scrap = itemView.findViewById(R.id.item_scrap);


            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ImageViewerActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("position", position);
                        intent.putExtra("category", "exhibition");
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
                        intent.putExtra("category", "exhibition");
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
                        deleteScrap(GlobalApplication.getUser_number(), "exhibition", listData.get(position).getTitle());
                    }else{
                        Glide.with(context).load(R.drawable.scrap_orange).into(iv_scrap);//스크랩표시
                        flag[position] = 1;
                        Toast.makeText(context, "스크랩 되었습니다", Toast.LENGTH_LONG).show();
                        insertScrap(GlobalApplication.getUser_number(), "exhibition", listData.get(position).getTitle());
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
