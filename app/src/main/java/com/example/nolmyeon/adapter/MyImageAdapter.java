package com.example.nolmyeon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.model.MyData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            mImageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("삭제");
//                    builder.setMessage("해당 항목을 삭제하시겠습니까?");
//                    builder.setPositiveButton("예",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
                    int position = getAbsoluteAdapterPosition();
                    deletePhoto(listData.get(position).getText());
                    listData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listData.size());
//                                }
//                            });
//                    builder.setNegativeButton("아니오",
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
