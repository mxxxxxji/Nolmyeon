package com.example.nolmyeon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nolmyeon.Fragment.CampingFragment;
import com.example.nolmyeon.Fragment.ExhibitionFragment;
import com.example.nolmyeon.Fragment.FestivalFragment;
import com.example.nolmyeon.Fragment.RuralFragment;
import com.example.nolmyeon.Fragment.ShowFragment;
import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Rural;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CategoryActivity extends AppCompatActivity {
    SmartTabLayout viewPagerTab;
    ViewPager viewPager;

    //현재 위치 받아오기
    private GpsTracker gpsTracker;
    double latitude;
    double longitude;

    TextView tv_location;
    // 프래그먼트 추가
    FragmentPagerItemAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

       // Intent intent = getIntent();
      //  PageNumber = intent.getIntExtra("PageNumber", 0);
        viewPager = (ViewPager) findViewById(R.id.viewpager);



        Log.d("LOG_LIST", GlobalApplication.getRuralArrayList().size() + "");
        if(GlobalApplication.getExhibitionList().size() == 0){
            adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("농어촌", RuralFragment.class)
                .add("캠핑", CampingFragment.class)
                .add("축제", FestivalFragment.class)
                .add("공연", ShowFragment.class)
                .create());
        }else if(GlobalApplication.getFestivalArrayList().size() == 0){
            adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add("전시", ExhibitionFragment.class)
                    .add("농어촌", RuralFragment.class)
                    .add("캠핑", CampingFragment.class)
                 //   .add("축제", FestivalFragment.class)
                    .add("공연", ShowFragment.class)
                    .create());
        }else if(GlobalApplication.getCampingArrayList().size() == 0){
            adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add("전시", ExhibitionFragment.class)
                    .add("농어촌", RuralFragment.class)
                   // .add("캠핑", CampingFragment.class)
                    .add("축제", FestivalFragment.class)
                    .add("공연", ShowFragment.class)
                    .create());
        }
        else if(GlobalApplication.getRuralArrayList().size() == 0){
            adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add("전시", ExhibitionFragment.class)
                    //.add("농어촌", RuralFragment.class)
                    .add("캠핑", CampingFragment.class)
                    .add("축제", FestivalFragment.class)
                    .add("공연", ShowFragment.class)
                    .create());
        }
        else if(GlobalApplication.getShowArrayList().size() == 0){
            adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add("전시", ExhibitionFragment.class)
                    .add("농어촌", RuralFragment.class)
                    .add("캠핑", CampingFragment.class)
                    .add("축제", FestivalFragment.class)
                    //.add("공연", ShowFragment.class)
                    .create());
        }else{
            adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("전시", ExhibitionFragment.class)
                .add("농어촌", RuralFragment.class)
                .add("캠핑", CampingFragment.class)
                .add("축제", FestivalFragment.class)
                .add("공연", ShowFragment.class)
                .create());
        }

        viewPager.setAdapter(adapter);
       // viewPager.setCurrentItem(PageNumber);
        //각 탭 이름 지정
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        tv_location = findViewById(R.id.current_location);

        gpsTracker = new GpsTracker(this);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        tv_location.setText(getCurrentAddress(latitude, longitude));
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

    //--------------------------------------------------------------------------------------------------------------------------------------------------

}