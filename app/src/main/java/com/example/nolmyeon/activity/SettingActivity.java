package com.example.nolmyeon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kakao.sdk.user.UserApiClient;


import com.example.nolmyeon.R;

public class SettingActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageButton logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        backBtn = findViewById(R.id.back_btn);
        logoutBtn = findViewById(R.id.logout_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        Log.e("TAG", "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                    }else{
                        Log.e("TAG", "로그아웃 성공, SDK에서 토큰 삭제됨");
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                    return null;
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}