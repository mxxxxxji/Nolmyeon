package com.example.nolmyeon.activity;



import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.GpsTracker;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Show;
import com.example.nolmyeon.model.Stamp;
import com.example.nolmyeon.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadingActivity extends Activity {
    /* 로딩 화면이 표시되는 시간을 설정(ms) */
    private final int SPLASH_DISPLAY_TIME =3000;
    //현재 위치 받아오기
    private GpsTracker gpsTracker;
    double latitude;
    double longitude;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    GlobalApplication app = (GlobalApplication)getApplication();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        GpsTracker gpsTracker = new GpsTracker(LoadingActivity.this);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        GlobalApplication.setLatitude(latitude);
        GlobalApplication.setLongitude(longitude);
        Log.d("LoadingTAG", latitude+","+longitude);
        Log.d("LoadingTAG", "2222");
        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("bookclub_loading.json");
        animationView.playAnimation();


        Log.d("LoadingTAG", "33333333");
        stampLogData();

        Log.d("LoadingTAG", "44444444");
        getExhibitionData();
        getDataRural();
        getDataCamping();
        getDataFestival();
        getDataShow();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        }, SPLASH_DISPLAY_TIME);
    }
    @Override
    public void onBackPressed() {
        /* 로딩 화면에서 뒤로가기 기능 제거. */
    }
    public void stampLogData(){
        long number = GlobalApplication.getUser_number();
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Stamp>> call =  retrofitClient.service.getStampLog1(number);
        call.enqueue(new Callback<ArrayList<Stamp>>() {
            @Override
            public void onResponse(Call<ArrayList<Stamp>> call, Response<ArrayList<Stamp>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ArrayList<Stamp> stampArrayList = new ArrayList<>();
                    stampArrayList.addAll(response.body());
                    GlobalApplication.setStampArrayList(stampArrayList);
                    Log.d("TAG_QR_Loading", stampArrayList+"");
                }else{
                    Log.d("TAG_QR", "NO DATA");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Stamp>> call, Throwable t) {
            }
        });
    }

    public void getExhibitionData(){
        gpsTracker = new GpsTracker(this);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

   //     getCurrentAddress(latitude, longitude);
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Exhibition>> call =  retrofitClient.service.getExhibition();
        Location myLocation = new Location("");
        myLocation.setLatitude(latitude);
        myLocation.setLongitude(longitude);
        Log.d("TAGNULL",  latitude+", "+longitude);
        call.enqueue(new Callback<ArrayList<Exhibition>>() {
            @Override
            public void onResponse(Call<ArrayList<Exhibition>> call, Response<ArrayList<Exhibition>> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("TAGNULL", response.body().toString());
                    ArrayList<Exhibition> pointData = new ArrayList<>();
                    pointData.addAll(response.body());
                    Log.d("TAGNULL", pointData.get(0).getTitle());
                    ArrayList<Exhibition> exhibitionList= new ArrayList<Exhibition>();
                    for(int i=0; i<pointData.size(); i++){
                        if(pointData.get(i) != null){

                            Log.d("TAGNULL", pointData.get(i).toString());


                            Location location = new Location("");
                            location.setLatitude(pointData.get(i).getLatitude());
                            location.setLongitude(pointData.get(i).getLongitude());
                            float distance = myLocation.distanceTo(location)/1000;

                            // 거리 2km 이내
                            if(distance < 5) {

                                //Marker(pointData.get(i).title, pointData.get(i).latiitude, pointData.get(i).longitude);
                                exhibitionList.add(pointData.get(i));

                                //Log.d("TAG", pointData.get(i).getTitle() + ":distance: "+distance+"km");
                                //Log.d("TAG", pointData.get(i).getRdnmadr() + ":distance: "+distance+"km");
                            }else{
                                Log.d("TAGNULL", "NULL!!!!!!!!!");

                            }
                        }

                    }
                    app.setExhibitionList(exhibitionList);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Exhibition>> call, Throwable t) {

            }
        });
    }
    public void getDataRural(){
        gpsTracker = new GpsTracker(this);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        Log.d("TAG",  latitude+", "+longitude);
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Rural>> call =  retrofitClient.service.getRural();

        Location myLocation = new Location("");
        myLocation.setLatitude(latitude);
        myLocation.setLongitude(longitude);
        call.enqueue(new Callback<ArrayList<Rural>>() {
            @Override
            public void onResponse(Call<ArrayList<Rural>> call, Response<ArrayList<Rural>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ArrayList<Rural> pointData = new ArrayList<>();
                    pointData.addAll(response.body());

                    ArrayList<Rural> ruralList= new ArrayList<Rural>();
                    for(int i=0; i<pointData.size(); i++){
                        Location location = new Location("");
                        location.setLatitude(pointData.get(i).getLatitude());
                        location.setLongitude(pointData.get(i).getLongitude());
                        float distance = myLocation.distanceTo(location)/1000;

                        // 거리 2km 이내
                        if(distance < 20) {
                            //Marker(pointData.get(i).title, pointData.get(i).latiitude, pointData.get(i).longitude);
                            ruralList.add(pointData.get(i));

                            //Log.d("TAG", pointData.get(i).getExprnVilageNm() + ":distance: "+distance+"km");
                            // Log.d("TAG", pointData.get(i).getRdnmadr() + ":distance: "+distance+"km");
                        }
                    }
                    app.setRuralArrayList(ruralList);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rural>> call, Throwable t) {

            }
        });
    }
    public void getDataCamping(){
        Log.d("TAG",  "CAMPINGDATA");
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Camping>> call =  retrofitClient.service.getCamping();

        gpsTracker = new GpsTracker(this);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        Log.d("TAG",  latitude+", "+longitude);
        Location myLocation = new Location("");
        myLocation.setLatitude(latitude);
        myLocation.setLongitude(longitude);

        call.enqueue(new Callback<ArrayList<Camping>>() {
            @Override
            public void onResponse(Call<ArrayList<Camping>> call, Response<ArrayList<Camping>> response) {
                if(response.isSuccessful() && response.body() != null){
                    ArrayList<Camping> pointData = new ArrayList<>();
                    pointData.addAll(response.body());
                    ArrayList<Camping> campingsList= new ArrayList<Camping>();
                    for(int i=0; i<pointData.size(); i++){
                        Location location = new Location("");
                        location.setLatitude(pointData.get(i).getLatitude());
                        location.setLongitude(pointData.get(i).getLongitude());
                        float distance = myLocation.distanceTo(location)/1000;

                        // 거리 5km 이내
                        if(distance < 10) {
                            //Marker(pointData.get(i).title, pointData.get(i).latiitude, pointData.get(i).longitude);
                            campingsList.add(pointData.get(i));

                            Log.d("TAG", pointData.get(i).getCampgNm() + ":distance: "+distance+"km");
                            Log.d("TAG", pointData.get(i).getRdnmadr() + ":distance: "+distance+"km");
                        }
                    }
                    app.setCampingArrayList(campingsList);
                    //                  Calculation2(pointData);
//                    for(int i=0; i<response.body().size(); i++){
//                        pointData.addAll(response.body());
//                    }
//                    Calculation(pointData);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Camping>> call, Throwable t) {


            }
        });
    }
    public void getDataFestival(){
        Log.d("TAG",  "FESTIVAL");
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Festival>> call =  retrofitClient.service.getFestival();
        gpsTracker = new GpsTracker(this);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        Log.d("TAG",  latitude+", "+longitude);
        Location myLocation = new Location("");
        myLocation.setLatitude(latitude);
        myLocation.setLongitude(longitude);
        call.enqueue(new Callback<ArrayList<Festival>>() {
            @Override
            public void onResponse(Call<ArrayList<Festival>> call, Response<ArrayList<Festival>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ArrayList<Festival> pointData = new ArrayList<>();
                    pointData.addAll(response.body());

                    ArrayList<Festival> festivalsList = new ArrayList<Festival>();
                    for (int i = 0; i < pointData.size(); i++) {
                        Location location = new Location("");
                        location.setLatitude(pointData.get(i).getLatitude());
                        location.setLongitude(pointData.get(i).getLongitude());
                        float distance = myLocation.distanceTo(location) / 1000;

                        // 거리 5km 이내
                        if (distance < 5) {
                            //Marker(pointData.get(i).title, pointData.get(i).latiitude, pointData.get(i).longitude);
                            festivalsList.add(pointData.get(i));
                        }
                    }
                    app.setFestivalArrayList(festivalsList);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Festival>> call, Throwable t) {

            }
        });

    }
    public void getDataShow(){
        Log.d("TAG",  "SHOW");
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<ArrayList<Show>> call =  retrofitClient.service.getShow();
        gpsTracker = new GpsTracker(this);

        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        Log.d("TAG",  latitude+", "+longitude);

        Location myLocation = new Location("");
        myLocation.setLatitude(latitude);
        myLocation.setLongitude(longitude);
        call.enqueue(new Callback<ArrayList<Show>>() {
            @Override
            public void onResponse(Call<ArrayList<Show>> call, Response<ArrayList<Show>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Log.d("TAGSHOW",  response.body().toString());
                    ArrayList<Show> pointData = new ArrayList<>();
                    pointData.addAll(response.body());
                    Log.d("TAGSHOW",  pointData.toString());
                    ArrayList<Show> showsList = new ArrayList<Show>();
                    for (int i = 0; i < pointData.size(); i++) {
                        Location location = new Location("");
                        location.setLatitude(pointData.get(i).getLatitude());
                        location.setLongitude(pointData.get(i).getLongitude());
                        float distance = myLocation.distanceTo(location) / 1000;

                        // 거리 5km 이내
                        if (distance < 10) {
                            showsList.add(pointData.get(i));
                        }
                    }
                    app.setShowArrayList(showsList);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Show>> call, Throwable t) {
                Log.d("TAGSHOW",  "NULL");
            }
        });

    }
    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(LoadingActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(LoadingActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(LoadingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(LoadingActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoadingActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(LoadingActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(LoadingActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(LoadingActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoadingActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}