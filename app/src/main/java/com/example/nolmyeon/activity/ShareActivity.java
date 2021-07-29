package com.example.nolmyeon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
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

public class ShareActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ShareImageAdapter mAdapter;
    GridLayoutManager gridLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;

    //뒤로가기
    ImageButton backBtn;
    ImageButton myBtn;
    ImageButton addBtn;
    ArrayList<MyData> allDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Log.d("TAG_REFRESH", "갱신******");
        getPhotoLog();


        swipeRefreshLayout = findViewById(R.id.refresh_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // use a linear layout manager
        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        ArrayList<MyData> pathArrayList =  GlobalApplication.getAllDataset();
        for(int i=0; i<GlobalApplication.getAllDataset().size(); i++){
            Log.d("TAG_REFRESH", pathArrayList.get(i).uri.toString());
        }

        // specify an adapter (see also next example)
        mAdapter = new ShareImageAdapter(getApplicationContext(),pathArrayList);
        mRecyclerView.setAdapter(mAdapter);

        backBtn = findViewById(R.id.back_btn);
        myBtn = findViewById(R.id.my_btn);
        addBtn = findViewById(R.id.add_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPhotoActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<MyData> pathArrayList2 =  GlobalApplication.getAllDataset();
                for(int i=0; i<pathArrayList2.size(); i++){
                    Log.d("TAG_SHARE", pathArrayList2.get(i).toString());
                    Log.d("TAG_SHARE", pathArrayList2.get(i).uri.toString());
                }

                // specify an adapter (see also next example)
                mAdapter.setListData(pathArrayList2);
                // mAdapter = new ShareImageAdapter(getApplication(),pathArrayList2);
                mAdapter.notifyDataSetChanged();
                // mRecyclerView.setAdapter(mAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    public void downloadImg(Photo photo, String title, String path){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageReference.child(path).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("dataset", title);
                        allDataList.add(new MyData(title, uri, photo.getNumber(),photo.getCategory(), photo.getImgpath(), photo.getContents(), photo.getDate(), photo.getLatitude(), photo.getLongitude()));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
        GlobalApplication.setAllDataset(allDataList);
    }
    public void getPhotoLog(){
        Log.d("TAG_REFRESH", "갱신2222");
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
                        downloadImg(pathArrayList.get(i), pathArrayList.get(i).getTitle(), pathArrayList.get(i).getImgpath());
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}