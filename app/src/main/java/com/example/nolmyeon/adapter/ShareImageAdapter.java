package com.example.nolmyeon.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.example.nolmyeon.model.Photo;
import com.github.mikephil.charting.utils.EntryXIndexComparator;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
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

    public void setListData(ArrayList<MyData> listData){
        this.listData = listData;
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
        try{
            Log.d("LOG_PHOTO", listData.size()+"");
            for(int i=0; i<GlobalApplication.getAllUser().size(); i++){
                if(listData.get(position).getNumber() == GlobalApplication.getAllUser().get(i).getNumber())
                    holder.name_tv.setText(GlobalApplication.getAllUser().get(i).getName());
            }
            for(int i=0; i<GlobalApplication.getPhotoArrayList().size(); i++){
                try{
                    holder.category_tv.setText(listData.get(position).getCategory());
                    String[] date = listData.get(position).getDate().split("T");
                    holder.date_tv.setText(date[0]);

                    if(listData.get(position).getCategory().equals("??????")){
                        //Log.d("TAG_PHOTO_COLOR",listData.get(position).getCategory()+", "+listData.get(position).getTitle());
                        holder.category_tv.setBackgroundColor(Color.parseColor("#3c9f2f")); }
                    if(listData.get(position).getCategory().equals("??????")){
                      //  Log.d("TAG_PHOTO_COLOR",listData.get(position).getCategory()+", "+listData.get(position).getTitle());
                        holder.category_tv.setBackgroundColor(Color.parseColor("#ffe02f")); }
                    if(listData.get(position).getCategory().equals("?????????")){
                       // Log.d("TAG_PHOTO_COLOR",listData.get(position).getCategory()+", "+listData.get(position).getTitle());
                        holder.category_tv.setBackgroundColor(Color.parseColor("#4c78a0")); }
                    if(listData.get(position).getCategory().equals("??????")){
                       // Log.d("TAG_PHOTO_COLOR",listData.get(position).getCategory()+", "+listData.get(position).getTitle());
                        holder.category_tv.setBackgroundColor(Color.parseColor("#8a502e")); }
                    if(listData.get(position).getCategory().equals("??????")){
                        //Log.d("TAG_PHOTO_COLOR",listData.get(position).getCategory()+", "+listData.get(position).getTitle());
                        holder.category_tv.setBackgroundColor(Color.parseColor("#ff633c")); }
                }catch (Exception e){
                    Log.d("TAG_PHOTO_COLOR",e.getMessage());
                }

            }

        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;
        public TextView category_tv;
        public TextView date_tv;
        public TextView name_tv;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.image);
            mTextView = (TextView)itemView.findViewById(R.id.textview);
            category_tv = itemView.findViewById(R.id.category_photo);
            date_tv = itemView.findViewById(R.id.date);
            name_tv = itemView.findViewById(R.id.name);

      }
    }

}

