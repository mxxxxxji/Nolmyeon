package com.example.nolmyeon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nolmyeon.CategoryActivity;
import com.example.nolmyeon.Descending;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.GpsTracker;
import com.example.nolmyeon.ImageSearchResponse;
import com.example.nolmyeon.MyService;
import com.example.nolmyeon.Ranker;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.R;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.MyData;
import com.example.nolmyeon.model.Photo;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Scrap;
import com.example.nolmyeon.model.Show;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageButton festivalBtn;
    private ImageButton exhibitionBtn;
    private ImageButton campingBtn;
    private ImageButton ruralBtn;
//    private Button mypageBtn;
    private Button listBtn;
    private Button mapBtn;
    private ImageButton stampBtn;
    private Button photoBtn;
    private Button shareBtn;
    private Button rankingBtn;

    private ImageButton settingBtn;

    GlobalApplication app = (GlobalApplication)getApplication();
    private Retrofit retrofitImage;
    private Service serviceImage;


    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private ArrayList<Exhibition> exhibitionArrayList;
    private ArrayList<Show> showArrayList;
    private ArrayList<Camping> campingArrayList;
    private ArrayList<Festival> festivalArrayList;
    private ArrayList<Rural> ruralArrayList;

    TextView myrank_tv;
    TextView first_tv;
    TextView second_tv;
    TextView third_tv;

    //현재 위치 받아오기
    private GpsTracker gpsTracker;
    private double latitude;
    private double longitude;
    TextView location_tv;
    TextView name_tv;

    ArrayList<MyData> myDataset = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //---------------------------------------------------------------------------------------------------
        //객체 생성
        retrofitImage = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serviceImage = retrofitImage.create(Service.class);
        //---------------------------------------------------------------------------------------------------
        // 사용자 이름
        name_tv  = findViewById(R.id.name_tv);
        name_tv.setText(GlobalApplication.getUser_name()+" 님,");

        //현재위치
        location_tv = findViewById(R.id.location);


        //랭킹
        myrank_tv = findViewById(R.id.myrank);
        first_tv = findViewById(R.id.first);
        second_tv = findViewById(R.id.second);
        third_tv = findViewById(R.id.third);

        mapBtn = findViewById(R.id.map_btn);
        listBtn = findViewById(R.id.list_btn);
        stampBtn = findViewById(R.id.stamp_btn);
        photoBtn  =  findViewById(R.id.photo_btn);
        shareBtn = findViewById(R.id.share_btn);
        rankingBtn = findViewById(R.id.ranking_btn);
        settingBtn = findViewById(R.id.setting_btn);

        getExhibitionImage();
        getFestivalImage();
        getShowImage();
        getCampingImage();
        getRuralImage();
        rank();

        BarChart chart = findViewById(R.id.barchart);

        int[] colors = {Color.GREEN,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE,
                Color.GRAY,
                Color.BLACK};

        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new BarEntry(GlobalApplication.getExhibition(), 0)); //전시
        NoOfEmp.add(new BarEntry(GlobalApplication.getFestival(), 1)); //축제
        NoOfEmp.add(new BarEntry(GlobalApplication.getCamping(), 2)); //캠핑
        NoOfEmp.add(new BarEntry(GlobalApplication.getRural(), 3)); //농어촌
        NoOfEmp.add(new BarEntry(GlobalApplication.getShows(), 4)); //공연

        ArrayList myData = new ArrayList();
        myData.add("전시");
        myData.add("축제");
        myData.add("캠핑");
        myData.add("농어촌");
        myData.add("공연");

        MyValueFormatter myValueFormatter = new MyValueFormatter();
        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);
        BarData data = new BarData( myData, bardataset);
        data.setValueFormatter(myValueFormatter);
        data.setValueTextSize(15.0f);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
        chart.getLegend().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(15F);

        YAxis yLeftAxis = chart.getAxisLeft();
        yLeftAxis.setDrawAxisLine(false);
        yLeftAxis.setDrawGridLines(false);
        yLeftAxis.setDrawLabels(false);

        YAxis yRightAxis = chart.getAxisRight();
        yRightAxis.setDrawAxisLine(false);
        yRightAxis.setDrawZeroLine(false);
        yRightAxis.setDrawGridLines(false);
        yRightAxis.setDrawLabels(false);

        chart.setDescription(null);




        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
               // intent.putExtra("PageNumber", 3);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("TAGERROR", "1111111");
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                Log.d("TAGERROR", "22222");
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                Log.d("TAGERROR", "3333");
                startActivity(intent);
                Log.d("TAGERROR", "4444");
            }
        });
        stampBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomQRScanActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyScrapActivity.class);//PhotoActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }

        });
        rankingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        // 앱 실행시 Background Service 실행
        Intent serviceintent = new Intent(this, MyService.class);
        startService(serviceintent);

        gpsTracker = new GpsTracker(this);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        location_tv.setText(getCurrentAddress(latitude, longitude));
        //--------------------------------------------------------------------------------------------

        Log.d("TAGSHOW", GlobalApplication.getShowArrayList().toString());

        getPhotoLog();
        getScrap();
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
                        GlobalApplication.setPhotoArrayList(pathArrayList);
                        downloadImg(pathArrayList.get(i).getTitle(), pathArrayList.get(i).getImgpath());
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

            }
        });
    }

    private void getExhibitionImage(){
        Log.d("TAGERROR", "555555");
        Retrofit retrofitImage;
        Service serviceImage;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //객체 생성
        retrofitImage = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        serviceImage = retrofitImage.create(Service.class);

        exhibitionArrayList = GlobalApplication.getExhibitionList();

        for(int i=0; i<exhibitionArrayList.size(); i++) {
            if (exhibitionArrayList.get(i) != null) {
                String query = exhibitionArrayList.get(i).getTitle();

                Call<ImageSearchResponse> call = serviceImage.geRepos("KakaoAK a35aec457d95adfb109ee49525dfd9e9", query);
                int finalI = i;
                call.enqueue(new Callback<ImageSearchResponse>() {
                    @Override
                    public void onResponse(Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().documents.size() != 0) {
                                ArrayList<String> imageUrl = new ArrayList<>();
                                for(int i=0; i<response.body().documents.size(); i++){
                                    imageUrl.add(response.body().documents.get(i).getImage_url());
                                }

                                exhibitionArrayList.get(finalI).setUrl(imageUrl);
                            } else {
                                String image_url = "https://da62mall.com/mobile/icon/noimage.gif";
                                ArrayList<String> imageUrl = new ArrayList<>();
                                for(int i=0; i<response.body().documents.size(); i++){
                                    imageUrl.add(image_url);
                                }
                                exhibitionArrayList.get(finalI).setUrl(imageUrl);
                            }

                        } else {
                            Log.e("Code: ", response.code() + " ");
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageSearchResponse> call, Throwable t) {

                    }
                });

            } else {
                Log.d("TAGNULL", "MAIN 355 NULL!!!");

            }
        }
    }
    private void getFestivalImage(){

        Retrofit retrofitImage;
        Service serviceImage;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //객체 생성
        retrofitImage = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        serviceImage = retrofitImage.create(Service.class);

        festivalArrayList = GlobalApplication.getFestivalArrayList();

        for(int i=0; i<festivalArrayList.size(); i++){
            String query = festivalArrayList.get(i).getFstvlNm();

            Call<ImageSearchResponse> call = serviceImage.geRepos("KakaoAK a35aec457d95adfb109ee49525dfd9e9", query);
            int finalI = i;
            call.enqueue(new Callback<ImageSearchResponse>() {
                @Override
                public void onResponse(Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        if(response.body().documents.size() != 0){
                            ArrayList<String> imageUrl = new ArrayList<>();
                            for(int i=0; i<response.body().documents.size(); i++){
                                imageUrl.add(response.body().documents.get(i).getImage_url());
                            }

                            festivalArrayList.get(finalI).setUrl(imageUrl);
                        }else{
//                            Log.d("getimage", response.isSuccessful()+"");
//                            Log.d("getimage", "Campingonresponse " + "이미지 없음");
                            String url ="https://da62mall.com/mobile/icon/noimage.gif";
                            ArrayList<String> imageUrl = new ArrayList<>();
                            for(int i=0; i<response.body().documents.size(); i++){
                                imageUrl.add(url);
                            }

                            festivalArrayList.get(finalI).setUrl(imageUrl);

                        }

                    }else{
                        Log.e("Code: " , response.code()+" ");
                    }
                }

                @Override
                public void onFailure(Call<ImageSearchResponse> call, Throwable t) {

                }
            });

        }
    }
    private void getCampingImage(){

        Retrofit retrofitImage;
        Service serviceImage;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //객체 생성
        retrofitImage = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        serviceImage = retrofitImage.create(Service.class);

        campingArrayList = GlobalApplication.getCampingArrayList();

        for(int i=0; i<campingArrayList.size(); i++){
            String query = campingArrayList.get(i).getCampgNm();


            Call<ImageSearchResponse> call = serviceImage.geRepos("KakaoAK a35aec457d95adfb109ee49525dfd9e9", query);
            int finalI = i;
            call.enqueue(new Callback<ImageSearchResponse>() {
                @Override
                public void onResponse(Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        if(response.body().documents.size() != 0){
                            ArrayList<String> imageUrl = new ArrayList<>();
                            for(int i=0; i<response.body().documents.size(); i++){
                                imageUrl.add(response.body().documents.get(i).getImage_url());
                            }
                            campingArrayList.get(finalI).setUrl(imageUrl);
                        }else{
                            String url ="https://da62mall.com/mobile/icon/noimage.gif";
                            ArrayList<String> imageUrl = new ArrayList<>();
                            for(int i=0; i<response.body().documents.size(); i++){
                                imageUrl.add(url);
                            }

                            campingArrayList.get(finalI).setUrl(imageUrl);


                        }
                        Log.e("Code: " , response.code()+" ");
                    }
                }

                @Override
                public void onFailure(Call<ImageSearchResponse> call, Throwable t) {

                }
            });

        }
    }
    private void getRuralImage(){

        Retrofit retrofitImage;
        Service serviceImage;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //객체 생성
        retrofitImage = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        serviceImage = retrofitImage.create(Service.class);

        ruralArrayList = GlobalApplication.getRuralArrayList();

        for(int i=0; i<ruralArrayList.size(); i++){
            String query = ruralArrayList.get(i).getExprnVilageNm();

            Call<ImageSearchResponse> call = serviceImage.geRepos("KakaoAK a35aec457d95adfb109ee49525dfd9e9", query);
            int finalI = i;
            call.enqueue(new Callback<ImageSearchResponse>() {
                @Override
                public void onResponse(Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        if(response.body().documents.size() != 0){
                            ArrayList<String> imageUrl = new ArrayList<>();
                            for(int i=0; i<response.body().documents.size(); i++){
                                imageUrl.add(response.body().documents.get(i).getImage_url());
                            }

                            ruralArrayList.get(finalI).setUrl(imageUrl);
                        }else{
//                            Log.d("getimage", response.isSuccessful()+"");
//                            Log.d("getimage", "Campingonresponse " + "이미지 없음");
                            String url ="https://da62mall.com/mobile/icon/noimage.gif";
                            ArrayList<String> imageUrl = new ArrayList<>();
                            for(int i=0; i<response.body().documents.size(); i++){
                                imageUrl.add(url);
                            }

                            ruralArrayList.get(finalI).setUrl(imageUrl);

                        }

                    }else{
                        Log.e("Code: " , response.code()+" ");
                    }
                }

                @Override
                public void onFailure(Call<ImageSearchResponse> call, Throwable t) {

                }
            });

        }
    }
    private void getShowImage(){
        Log.d("TAGERROR", "555555");
        Retrofit retrofitImage;
        Service serviceImage;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //객체 생성
        retrofitImage = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        serviceImage = retrofitImage.create(Service.class);

        showArrayList = GlobalApplication.getShowArrayList();

        for(int i=0; i<showArrayList.size(); i++) {
            if (showArrayList.get(i) != null) {
                String query = showArrayList.get(i).getEventNm();

                Call<ImageSearchResponse> call = serviceImage.geRepos("KakaoAK a35aec457d95adfb109ee49525dfd9e9", query);
                int finalI = i;
                call.enqueue(new Callback<ImageSearchResponse>() {
                    @Override
                    public void onResponse(Call<ImageSearchResponse> call, Response<ImageSearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().documents.size() != 0) {
                                ArrayList<String> imageUrl = new ArrayList<>();
                                for(int i=0; i<response.body().documents.size(); i++){
                                    imageUrl.add(response.body().documents.get(i).getImage_url());
                                }

                                showArrayList.get(finalI).setUrl(imageUrl);
                            } else {
                                String url ="https://da62mall.com/mobile/icon/noimage.gif";
                                ArrayList<String> imageUrl = new ArrayList<>();
                                for(int i=0; i<response.body().documents.size(); i++){
                                    imageUrl.add(url);
                                }

                                showArrayList.get(finalI).setUrl(imageUrl);
                            }

                        } else {
                            Log.e("Code: ", response.code() + " ");
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageSearchResponse> call, Throwable t) {

                    }
                });

            } else {
                Log.d("TAGNULL", "MAIN 355 NULL!!!");

            }
        }
    }

    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation( latitude, longitude, 100);
            //  Log.d("TAG_gg", addresses.get(0)+"");
            Toast.makeText(this, "현재 위치는 "+addresses.get(0).getAddressLine(0)+" 입니다", Toast.LENGTH_LONG).show();
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            //showDialogForLocationServiceSetting();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            //showDialogForLocationServiceSetting();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            // showDialogForLocationServiceSetting(); return "주소 미발견";
        }
        String result = addresses.get(0).getAdminArea() +" " + addresses.get(0).getThoroughfare();
        return result;
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private void rank(){
        Log.d("rank", "111");
        //ArrayList<Pair<Pair<String, Long>, Integer>> user_stamp = new ArrayList<Pair<Pair<String, Long>, Integer>>();

        ArrayList<Ranker> userStamp = new ArrayList<Ranker>();
        for(int i = 0; i< GlobalApplication.getAllUser().size(); i++){
            String name = GlobalApplication.getAllUser().get(i).getName();
            long number = GlobalApplication.getAllUser().get(i).getNumber();
            Log.d("rank", GlobalApplication.getAllUser().get(i).getExhibition()+", "+ GlobalApplication.getAllUser().get(i).getRural()+", "+GlobalApplication.getAllUser().get(i).getFestival() + ", "+ GlobalApplication.getAllUser().get(i).getCamping()+", "+GlobalApplication.getAllUser().get(i).getShows());
            int stamp_count = GlobalApplication.getAllUser().get(i).getExhibition()+ GlobalApplication.getAllUser().get(i).getRural()
                    + GlobalApplication.getAllUser().get(i).getFestival()+ GlobalApplication.getAllUser().get(i).getCamping() + GlobalApplication.getAllUser().get(i).getShows();

            userStamp.add(new Ranker(stamp_count, number, name));

        }
        int stamp = GlobalApplication.getExhibition()+ GlobalApplication.getFestival()+ GlobalApplication.getCamping()+ GlobalApplication.getShows();
        //현재 랭킹
        Log.d("rank", userStamp.toString());
        Collections.sort(userStamp, new Descending());
        Log.d("rank", userStamp.toString());
        Log.d("rank", userStamp.get(0)+"");
        Log.d("rank", userStamp.get(0)+"");
        first_tv.setText(userStamp.get(0).getName());
        second_tv.setText(userStamp.get(1).getName());
        third_tv.setText(userStamp.get(2).getName());

        //현재 나의 순위
        long myNumber = GlobalApplication.getUser_number();
        Log.d("rank", GlobalApplication.getUser_number()+"회원번호");
        Log.d("rank", userStamp.indexOf(myNumber)+"내 순위");
        for(int i=0; i<userStamp.size(); i++){
            if(userStamp.get(i).getNumber() == myNumber){
                myrank_tv.setText((i+1)+"위");
            }
        }
        GlobalApplication.setRankerArrayList(userStamp);
    }
}

class MyValueFormatter implements ValueFormatter{
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return "" + ((int) value);
    }
}

