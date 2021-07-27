package com.example.nolmyeon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.adapter.MyScrapAdapter;
import com.example.nolmyeon.adapter.ShareImageAdapter;
import com.example.nolmyeon.model.MyData;
import com.example.nolmyeon.model.Scrap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyScrapActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MyScrapAdapter mAdapter;
    //뒤로가기
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_scrap);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        getScrap();
        // specify an adapter (see also next example)
        mAdapter = new MyScrapAdapter(getApplicationContext(),GlobalApplication.getScrapArrayList());
        mRecyclerView.setAdapter(mAdapter);

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}