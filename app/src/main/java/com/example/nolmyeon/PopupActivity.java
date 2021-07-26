package com.example.nolmyeon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Show;

import java.util.ArrayList;
import java.util.List;

public class PopupActivity extends Activity {
    TextView txtText;
    TextView txtText2;
    TextView txtText3;
    TextView txtText4;
    TextView title;
    GlobalApplication app = (GlobalApplication)getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.txtText);
        txtText2 = (TextView)findViewById(R.id.txtText2);
        txtText3 = (TextView)findViewById(R.id.txtText3);
        txtText4 = (TextView)findViewById(R.id.txtText4);
        title = (TextView)findViewById(R.id.title);



        //데이터 가져오기
        Intent intent = getIntent();
        int data = intent.getIntExtra("position", 0);
        String category = intent.getStringExtra("category");
        Log.d("TAGpopupactivity", data+"");
        Log.d("TAGpopupactivity", category +"");
        putData(data, category);


    }
    void putData(int data, String category){
        if(category.equals("exhibition")){
            ArrayList<Exhibition> exhibitionlist = new ArrayList<Exhibition>();
            exhibitionlist = app.getExhibitionList();
            Log.d("popupactivity", exhibitionlist.get(0).getTitle());
            title.setText(exhibitionlist.get(data).getTitle());
            txtText.setText(exhibitionlist.get(data).getRdnmadr());
            txtText2.setText(exhibitionlist.get(data).getPhoneNumber());
            txtText3.setText(exhibitionlist.get(data).getFcltyIntrcn());
            txtText4.setText(exhibitionlist.get(data).getRstdeInfo());
        }else if(category.equals("rural")){
            ArrayList<Rural> ruralArrayList = new ArrayList<Rural>();
            ruralArrayList = app.getRuralArrayList();
            title.setText(ruralArrayList.get(data).getExprnVilageNm());
            txtText.setText(ruralArrayList.get(data).getRdnmadr());
            txtText2.setText(ruralArrayList.get(data).getHomepageUrl());
            txtText3.setVisibility(View.GONE);
            txtText4.setText(ruralArrayList.get(data).getExprnCn());
        }else if(category.equals("camping")){
            ArrayList<Camping> campingArrayList = new ArrayList<Camping>();
            campingArrayList = app.getCampingArrayList();
            title.setText(campingArrayList.get(data).getCampgNm());
            if(campingArrayList.get(data).getRdnmadr() == null)
                txtText.setText(campingArrayList.get(data).getLnmadr());
            else txtText.setText(campingArrayList.get(data).getRdnmadr());
            txtText2.setText(campingArrayList.get(data).getOfficePhoneNumber());
            txtText3.setText(campingArrayList.get(data).getCvntl());
            txtText4.setVisibility(View.GONE);
        }else if(category.equals("festival")){
            ArrayList<Festival> festivalArrayList = new ArrayList<>();
            festivalArrayList = GlobalApplication.getFestivalArrayList();
            title.setText(festivalArrayList.get(data).getFstvlNm());
            txtText.setText(festivalArrayList.get(data).getFstvlStartDate()+" ~ "+festivalArrayList.get(data).getFstvlEndDate());
            txtText2.setText(festivalArrayList.get(data).getFstvlCo());
            txtText3.setText(festivalArrayList.get(data).getHomepageUrl());
            txtText4.setText(festivalArrayList.get(data).getOpar());
        }else if(category.equals("show")){
            ArrayList<Show> showArrayList = new ArrayList<>();
            showArrayList = GlobalApplication.getShowArrayList();
            title.setText(showArrayList.get(data).getEventNm());
            txtText.setText(showArrayList.get(data).getEventStartDate()+" ~ "+showArrayList.get(data).getEventEndDate());
            txtText2.setText(showArrayList.get(data).getEventStartTime()+" ~ "+showArrayList.get(data).getEventEndTime());
            txtText3.setText(showArrayList.get(data).getEventCo());
            txtText3.setText(showArrayList.get(data).getPhoneNumber());
        }else{

        }

    }
    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
