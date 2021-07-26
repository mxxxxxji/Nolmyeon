package com.example.nolmyeon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.adapter.RankingAdapter;

import org.w3c.dom.Text;

public class RankingActivity extends AppCompatActivity {

    ImageButton backBtn;
    RecyclerView recyclerView;
    RankingAdapter adapter;

    TextView first_tv;
    TextView second_tv;
    TextView third_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ranking);
        init();
        first_tv.setText(GlobalApplication.getRankerArrayList().get(0).getName()+"님");
        second_tv.setText(GlobalApplication.getRankerArrayList().get(1).getName()+"님");
        third_tv.setText(GlobalApplication.getRankerArrayList().get(2).getName()+"님");
    }
    void init(){
        backBtn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.recyclerView);
        first_tv = findViewById(R.id.first_tv);
        second_tv = findViewById(R.id.second_tv);
        third_tv = findViewById(R.id.third_tv);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration((new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL)));
        adapter = new RankingAdapter(GlobalApplication.getRankerArrayList());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}