package com.example.nolmyeon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.adapter.ImageViewAdapter;
import com.example.nolmyeon.adapter.PhotoAdapter;
import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Show;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class ImageViewerActivity extends FragmentActivity {
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;
    private CircleIndicator3 mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_viewer);

        //데이터 가져오기
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        String category = intent.getStringExtra("category");
        Log.d("TAGpopupactivity", position+"");
        Log. d("TAGpopupactivity", category);
        putData(position, category);

        //ViewPager2
        mPager = findViewById(R.id.viewpager);

        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);

        //ViewPager Setting
//        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        mPager.setCurrentItem(1000);
//        mPager.setOffscreenPageLimit(3);


        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }

        });

//저절로 넘어가는 애니메이션
//        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
//
//        mPager.setPageTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float myOffset = position * -(2 * pageOffset + pageMargin);
//                if (mPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//                    if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                        page.setTranslationX(-myOffset);
//                    } else {
//                        page.setTranslationX(myOffset);
//                    }
//                } else {
//                    page.setTranslationY(myOffset);
//                }
//            }
//        });

    }
    void putData(int position, String category){
        if(category.equals("exhibition")){
            //ViewPager2
            mPager = findViewById(R.id.viewpager);
            PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(),GlobalApplication.getExhibitionList().get(position).getUrl());
            mPager.setAdapter(adapter);

        }else if(category.equals("rural")){
            //ViewPager2
            mPager = findViewById(R.id.viewpager);
            PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(),GlobalApplication.getRuralArrayList().get(position).getUrl());
            mPager.setAdapter(adapter);
        }else if(category.equals("camping")){
            //ViewPager2
            mPager = findViewById(R.id.viewpager);
            PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(),GlobalApplication.getCampingArrayList().get(position).getUrl());
            mPager.setAdapter(adapter);
        }else if(category.equals("festival")){
            //ViewPager2
            mPager = findViewById(R.id.viewpager);
            PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(),GlobalApplication.getFestivalArrayList().get(position).getUrl());
            mPager.setAdapter(adapter);
        }else if(category.equals("show")){
            //ViewPager2
            mPager = findViewById(R.id.viewpager);
            PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(),GlobalApplication.getShowArrayList().get(position).getUrl());
            mPager.setAdapter(adapter);
        }else{

        }
    }

}
