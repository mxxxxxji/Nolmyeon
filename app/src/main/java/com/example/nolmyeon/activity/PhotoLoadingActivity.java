package com.example.nolmyeon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.nolmyeon.R;
import com.example.nolmyeon.Service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_loading);
        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //데이터 가져오기
                Intent intent = getIntent();
                String sql = intent.getStringExtra("sql");
                putData(sql);
                Intent intent2 = new Intent(PhotoLoadingActivity.this, ShareActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                finish();
            }
        }, 2000); // 화면에 Logo 2초간 보이기
    }// startLoading Method..
    public void putData(String sql){
        Log.d("TAG_PHOTO111__", sql);
        Retrofit retrofit;
        Service service;


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //객체 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("https://project-intern02.wjthinkbig.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        service = retrofit.create(Service.class);

        Call<String> call =  service.getPhoto(sql);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG_PHOTO", response + "");
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG_PHOTO", "FAIL!!!!!");
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}