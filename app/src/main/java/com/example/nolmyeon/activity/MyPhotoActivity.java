package com.example.nolmyeon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.adapter.MyImageAdapter;
import com.example.nolmyeon.adapter.ShareImageAdapter;
import com.example.nolmyeon.model.MyData;
import com.example.nolmyeon.model.Photo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPhotoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    GridLayoutManager gridLayoutManager;
    ArrayList<Photo> pathArrayList = new ArrayList<>();
    ArrayList<MyData> myDataset = new ArrayList<>();
    //뒤로가기
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photo);
        getPhotoLog();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // use a linear layout manager
        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<MyData> pathArrayList =  GlobalApplication.getMyDataset();
        for(int i=0; i<GlobalApplication.getMyDataset().size(); i++){
            Log.d("TAG_SHARE", pathArrayList.get(i).uri.toString());
        }

        // specify an adapter (see also next example)
        mAdapter = new MyImageAdapter(getApplicationContext(),pathArrayList);
        mRecyclerView.setAdapter(mAdapter);

        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void downloadImg(String title, String path){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageReference.child(path).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("dataset", title);
                        myDataset.add(new MyData(title, uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
        GlobalApplication.setMyDataset(myDataset);
    }
    public void getPhotoLog(){
        ArrayList<Photo> pathArrayList = new ArrayList<>();
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Photo>> call =  retrofitClient.service.getPhotoLog();
        call.enqueue(new Callback<ArrayList<Photo>>() {
            @Override
            public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                Log.d("dataset", response.isSuccessful()+"" );
                if(response.isSuccessful() && response.body() != null){
                    Log.d("dataset", response.body()+"" );
                    pathArrayList.addAll(response.body());
                    for(int i=0; i<pathArrayList.size(); i++){
                        Log.d("dataset", pathArrayList.get(i).getTitle());
                        downloadImg(pathArrayList.get(i).getTitle(), pathArrayList.get(i).getImgpath());
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

            }
        });
    }

}