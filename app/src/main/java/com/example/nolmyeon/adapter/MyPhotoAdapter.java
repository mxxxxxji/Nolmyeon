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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.activity.ImageViewerActivity2;
import com.example.nolmyeon.model.MyData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.Holder> {
    private ArrayList<MyData> listData;
    Context context;

    public MyPhotoAdapter(Context context, ArrayList<MyData> listData ) {
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
    }

    @Override
    public int getItemCount() { return listData.size(); }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.image);
            mTextView = (TextView)itemView.findViewById(R.id.textview);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                       // Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ImageViewerActivity2.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("position", position);
                        intent.putExtra("title", listData.get(position).getText());
                        intent.putExtra("uri", listData.get(position).getUri());
                        //((Activity)context).startActivityForResult(intent, IMAGE_ACTIVITY);
                        context.startActivity(intent);
                    }
                }
            });

            mImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("??????");
//                    builder.setMessage("?????? ????????? ?????????????????????????");
//                    builder.setPositiveButton("???",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
                    int position = getAbsoluteAdapterPosition();
                    deletePhoto(listData.get(position).getText());
                    listData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listData.size());
//                                }
//                            });
//                    builder.setNegativeButton("?????????",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//                    builder.show();

                    return true;
                }
            });
        }
    }
    public void deletePhoto(String title){
        Log.d("LOG_DELETE_PHOTO", title);
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<String> call = retrofitClient.service.deletePhoto(GlobalApplication.getUser_number(), title);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("LOG_DELETE_PHOTO", response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("LOG_DELETE_PHOTO", t.getMessage());
            }
        });
    }
}
