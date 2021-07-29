package com.example.nolmyeon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.model.MyData;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class ImageViewerActivity2 extends AppCompatActivity {
    PhotoView photoView;
    ImageButton closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer2);
        photoView = findViewById(R.id.photoView);
        closeBtn = findViewById(R.id.btn_close);
        //데이터 가져오기
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        String title = intent.getStringExtra("title");
        Uri uri = (Uri) intent.getParcelableExtra("uri");
        Log.d("VIEWER", uri+"");
        Glide.with(getApplication()).load(uri).into(photoView);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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