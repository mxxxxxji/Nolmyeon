package com.example.nolmyeon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.GpsTracker;
import com.example.nolmyeon.R;

import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Info;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Show;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;


import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    MapView mapView;
    public MapPoint mapPoint;
    private MarkerEventListener eventLister = new MarkerEventListener(this);
    Toolbar toolbar;
    LinearLayout page;
    TextView title_tv;

    TextView address_tv;
    TextView phoneNumber_tv;
    ImageView image_iv;

    Animation translate_left;
    Animation translate_right;

    boolean isPageOpen = false;
    BoomMenuButton bmb;
    GlobalApplication app = (GlobalApplication)getApplication();

    String current = null;
    Exhibition findExhibition = null;
    Festival findFestival = null;
    Camping findCamping = null;
    Rural findRural = null;
    Show findShow = null;

    //Button position_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();



    }
    //------------------------------------------------------------------------------------------------

    void init(){


       // ??????
       mapView = new MapView(this);
       ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
       mapViewContainer.addView(mapView);
       //mapView.setCurrentLocationTrackingMode( MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading );


       page = findViewById(R.id.page);
       title_tv = findViewById(R.id.title_tv);
       address_tv = findViewById(R.id.address_tv);
       phoneNumber_tv = findViewById(R.id.phoneNumber_tv);
       image_iv = findViewById(R.id.image_iv);
       bmb = (BoomMenuButton) findViewById(R.id.bmb);

       toolbar = (Toolbar) findViewById(R.id.toolbar);

       setSupportActionBar(toolbar);getSupportActionBar().setTitle("???????????? ??????");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ???????????? ??????
       //getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_btn_back);

       translate_left = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
       translate_right = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

       SlidingAnimationListener listener = new SlidingAnimationListener();
       translate_left.setAnimationListener(listener);
       translate_right.setAnimationListener(listener);

     //  position_btn = findViewById(R.id.position_btn);

       bmb.addBuilder(new TextInsideCircleButton.Builder()
               .normalImageRes(R.drawable.bf_change_bana_s)
               .normalText("??????")
               .textSize(25)
               .textRect(new Rect(Util.dp2px(35), Util.dp2px(130), Util.dp2px(150), Util.dp2px(80)))
               .buttonRadius(Util.dp2px(90))
               //left_margin, top_margin, ??????, ??????
               .imageRect(new Rect(Util.dp2px(40), Util.dp2px(25), Util.dp2px(140), Util.dp2px(140)))
               .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        mapView.removeAllPOIItems(); // ????????? ?????? ??????
                        ArrayList<Exhibition> exhibitionList = app.getExhibitionList();
                        for(int i=0; i<exhibitionList.size(); i++){
                            CustomMarker(exhibitionList.get(i).getTitle(), exhibitionList.get(i).getLatitude(), exhibitionList.get(i).getLongitude(),R.drawable.marker_2);
                        }
                        ArrayList<Festival> festivalArrayList  = app.getFestivalArrayList();
                        for(int i=0; i<festivalArrayList.size(); i++){
                            CustomMarker(festivalArrayList.get(i).getFstvlNm(), festivalArrayList.get(i).getLatitude(), festivalArrayList.get(i).getLongitude(), R.drawable.marker_5);
                        }
                        ArrayList<Camping> campingArrayList = app.getCampingArrayList();
                        for(int i=0; i<campingArrayList.size(); i++){
                            CustomMarker(campingArrayList.get(i).getCampgNm(), campingArrayList.get(i).getLatitude(), campingArrayList.get(i).getLongitude(),R.drawable.marker_4);
                        }
                        ArrayList<Rural> ruralArrayList = app.getRuralArrayList();
                        for(int i=0; i<ruralArrayList.size(); i++){
                            CustomMarker(ruralArrayList.get(i).getExprnVilageNm(), ruralArrayList.get(i).getLatitude(), ruralArrayList.get(i).getLongitude(),R.drawable.marker_6);
                        }
                        ArrayList<Show> showArrayList = app.getShowArrayList();
                        for(int i=0; i<showArrayList.size(); i++){
                            CustomMarker(showArrayList.get(i).getEventNm(), showArrayList.get(i).getLatitude(), showArrayList.get(i).getLongitude(),R.drawable.marker_9);
                        }
                        // ??? ?????? ??????
                        mapView.setZoomLevel(7, true);
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(GlobalApplication.getLatitude(), GlobalApplication.getLongitude()), true);
                    }
               })
       );
       bmb.addBuilder(new TextInsideCircleButton.Builder()
               .normalImageRes(R.drawable.exhibition_icon)
               .normalText("??????")
               .textSize(25)
               .textRect(new Rect(Util.dp2px(35), Util.dp2px(130), Util.dp2px(150), Util.dp2px(80)))
               .buttonRadius(Util.dp2px(90))
               .imageRect(new Rect(Util.dp2px(50), Util.dp2px(25), Util.dp2px(140), Util.dp2px(140)))
               .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        mapView.removeAllPOIItems(); // ????????? ?????? ??????
                        ArrayList<Exhibition> exhibitionList = app.getExhibitionList();
                        for(int i=0; i<exhibitionList.size(); i++){
                            CustomMarker(exhibitionList.get(i).getTitle(), exhibitionList.get(i).getLatitude(), exhibitionList.get(i).getLongitude(), R.drawable.marker_2);
                        }
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(GlobalApplication.getLatitude(), GlobalApplication.getLongitude()), true);
                        // ??? ?????? ??????
                        mapView.setZoomLevel(5, true);
                    }
               })
       );
       bmb.addBuilder(new TextInsideCircleButton.Builder()
               .normalImageRes(R.drawable.festival_icon)
               .normalText("??????")
               .textSize(25)
               .textRect(new Rect(Util.dp2px(35), Util.dp2px(130), Util.dp2px(150), Util.dp2px(80)))
               .buttonRadius(Util.dp2px(90))
               .imageRect(new Rect(Util.dp2px(40), Util.dp2px(20), Util.dp2px(140), Util.dp2px(140)))
               .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        mapView.removeAllPOIItems();// ????????? ?????? ??????
                        ArrayList<Festival> festivalArrayList  = app.getFestivalArrayList();
                        for(int i=0; i<festivalArrayList.size(); i++){
                            CustomMarker(festivalArrayList.get(i).getFstvlNm(), festivalArrayList.get(i).getLatitude(), festivalArrayList.get(i).getLongitude(), R.drawable.marker_5);
                        }
                        // ??? ?????? ??????
                        mapView.setZoomLevel(7, true);
                    }
               })
       );
       bmb.addBuilder(new TextInsideCircleButton.Builder()
               .normalImageRes(R.drawable.camping_icon)
               .normalText("??????")
               .textSize(25)
               .textRect(new Rect(Util.dp2px(35), Util.dp2px(130), Util.dp2px(150), Util.dp2px(80)))
               .buttonRadius(Util.dp2px(90))
               .imageRect(new Rect(Util.dp2px(40), Util.dp2px(15), Util.dp2px(140), Util.dp2px(140)))
               .listener(new OnBMClickListener() {
                   @Override
                   public void onBoomButtonClick(int index) {
                       mapView.removeAllPOIItems();// ????????? ?????? ??????
                       ArrayList<Camping> campingArrayList = app.getCampingArrayList();
                       for(int i=0; i<campingArrayList.size(); i++){
                           CustomMarker(campingArrayList.get(i).getCampgNm(), campingArrayList.get(i).getLatitude(), campingArrayList.get(i).getLongitude(),R.drawable.marker_4);
                       }
                       // ??? ?????? ??????
                       mapView.setZoomLevel(7, true);
                   }

               })

       );
       bmb.addBuilder(new TextInsideCircleButton.Builder()
               .normalImageRes(R.drawable.rural_icon)
               .normalText("?????????")
               .textSize(25)
               .textRect(new Rect(Util.dp2px(35), Util.dp2px(130), Util.dp2px(150), Util.dp2px(80)))
               .buttonRadius(Util.dp2px(90))
               .imageRect(new Rect(Util.dp2px(40), Util.dp2px(30), Util.dp2px(150), Util.dp2px(150)))
               .listener(new OnBMClickListener() {
                   @Override
                   public void onBoomButtonClick(int index) {
                       mapView.removeAllPOIItems();// ????????? ?????? ??????
                        ArrayList<Rural> ruralArrayList = app.getRuralArrayList();
                        for(int i=0; i<ruralArrayList.size(); i++){
                            CustomMarker(ruralArrayList.get(i).getExprnVilageNm(), ruralArrayList.get(i).getLatitude(), ruralArrayList.get(i).getLongitude(),R.drawable.marker_6);
                        }
                       // ??? ?????? ??????
                       mapView.setZoomLevel(7, true);
                   }
               })
       );
        bmb.addBuilder(new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.show_icon)
                .normalText("??????")
                .textSize(25)
                .textRect(new Rect(Util.dp2px(35), Util.dp2px(130), Util.dp2px(150), Util.dp2px(80)))
                .buttonRadius(Util.dp2px(90))
                .imageRect(new Rect(Util.dp2px(45), Util.dp2px(20), Util.dp2px(130), Util.dp2px(130)))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        mapView.removeAllPOIItems();// ????????? ?????? ??????
                        ArrayList<Show> showArrayList = app.getShowArrayList();
                        for(int i=0; i<showArrayList.size(); i++){
                            CustomMarker(showArrayList.get(i).getEventNm(), showArrayList.get(i).getLatitude(), showArrayList.get(i).getLongitude(),R.drawable.marker_9);
                        }
                        // ??? ?????? ??????
                        mapView.setZoomLevel(7, true);
                    }
                })
        );

//        position_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mapView.removeAllPOIItems();// ????????? ?????? ??????
//
//            }
//        });
     //   getHashKey();
    }
    //------------------------------------------------------------------------------------------------
//    private void getHashKey(){
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("KeyHash", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar??? back??? ????????? ??? ??????
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    public void mOnClose(View view){

        if (isPageOpen) {
            slideDown(view);
        } else {
            slideUp(view);
        }
        isPageOpen = !isPageOpen;

    }
    class SlidingAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            if (isPageOpen) {
                page.setVisibility(View.INVISIBLE);
                isPageOpen = false;
            } else {
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    //------------------------------------------------------------------------------------------------
    Info search(String title){

        ArrayList<Exhibition> exhibitionArrayList = app.getExhibitionList();
        ArrayList<Festival> festivalArrayList = app.getFestivalArrayList();
        ArrayList<Camping> campingArrayList = app.getCampingArrayList();
        ArrayList<Rural> ruralArrayList = app.getRuralArrayList();
        ArrayList<Show> showArrayList = app.getShowArrayList();
        Info info = new Info();
        for(Exhibition e : exhibitionArrayList){
            if((e.getTitle()).equals(title)){
                findExhibition = e;
                info.setTitle(findExhibition.getTitle());
                info.setAddress(findExhibition.getRdnmadr());
                info.setNumber(findExhibition.getPhoneNumber());
                info.setDescription(findExhibition.getFcltyInfo());
                info.setHoliday(findExhibition.getRstdeInfo());
                info.setImageUrl(findExhibition.getUrl().get(0));
                break;
            }
        }
        for( Festival f : festivalArrayList){
            if((f.getFstvlNm()).equals(title)){
                findFestival = f;
                info.setTitle(findFestival.getFstvlNm());
                info.setAddress(findFestival.getRdnmadr());
                info.setNumber(findFestival.getPhoneNumber());
                info.setDescription(findFestival.getFstvlCo());
                info.setImageUrl(findFestival.getUrl().get(0));
                break;
            }
        }
        for( Camping c : campingArrayList){
            if((c.getCampgNm()).equals(title)){
                findCamping = c;
                info.setTitle(findCamping.getCampgNm());
                info.setAddress(findCamping.getRdnmadr());
                info.setNumber( findCamping.getPhoneNumber());
                info.setDescription( findCamping.getCampgSe());
                info.setImageUrl(findCamping.getUrl().get(0));
                break;
            }
        }
        for( Rural r : ruralArrayList){
            if((r.getExprnVilageNm()).equals(title)){
                findRural = r;
                info.setTitle(findRural.getExprnVilageNm());
                info.setAddress(findRural.getRdnmadr());
                info.setNumber(findRural.getHomepageUrl());
                info.setDescription(findRural.getExprnCn());
                info.setImageUrl(findRural.getUrl().get(0));
                break;
            }
        }
        for( Show s : showArrayList){
            if((s.getEventNm()).equals(title)){
                findShow = s;
                info.setTitle(findShow.getEventNm());
                info.setAddress(findShow.getOpar());
                info.setNumber(findShow.getPhoneNumber());
                info.setDescription(findShow.getEventStartDate() +" ~ " + findShow.getEventEndDate());
                info.setImageUrl(findShow.getUrl().get(0));
                break;
            }
        }
        return info;
    }
    //-------------------------------------------------------------------------------------------------
    // ?????? ?????? ??????
    public void Marker(String MakerName, double startX, double startY) {
        Log.d(MakerName,"Marker()" );

        mapPoint = MapPoint.mapPointWithGeoCoord(startX, startY);
        mapView.setMapCenterPoint( mapPoint, true ); //true??? ??? ?????? ??? ??????????????? ????????? ????????? false??? ?????????????????? ???????????????.;
        mapView.setPOIItemEventListener(eventLister);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(MakerName); // ?????? ?????? ??? ??????????????? ?????? ??????
        marker.setMapPoint( mapPoint );
        marker.setMarkerType( MapPOIItem.MarkerType.RedPin ); // ????????? ???????????????, ???????????? ???????????? RedPin ?????? ??????.
        marker.setSelectedMarkerType( MapPOIItem.MarkerType.BluePin );
        mapView.addPOIItem( marker );

    }
    //-------------------------------------------------------------------------------------------------
    // ?????? ?????? ??????
    public void CustomMarker(String MakerName, double startX, double startY, int resourceId) {
        Log.d(MakerName,"Marker()" );

        mapPoint = MapPoint.mapPointWithGeoCoord(startX, startY);
        mapView.setMapCenterPoint( mapPoint, true ); //true??? ??? ?????? ??? ??????????????? ????????? ????????? false??? ?????????????????? ???????????????.;
        mapView.setPOIItemEventListener(eventLister);

        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName(MakerName); // ?????? ?????? ??? ??????????????? ?????? ??????
        customMarker.setMapPoint(mapPoint);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        customMarker.setCustomImageResourceId(resourceId);
        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi ??? ??????????????? ???????????? ???????????? ????????? ?????? ?????? ?????????????????? ????????? ????????? ??????.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // ?????? ???????????? ????????? ?????? ??????(???????????????) ?????? - ?????? ????????? ?????? ?????? ?????? x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) ???.
        customMarker.setMarkerType( MapPOIItem.MarkerType.CustomImage );
        customMarker.setSelectedMarkerType( MapPOIItem.MarkerType.BluePin );
        customMarker.isCustomImageAutoscale();
        mapView.addPOIItem(customMarker);

    }
    //------------------------------------------------------------------------------------------------
    class MarkerEventListener implements MapView.POIItemEventListener {

        public MarkerEventListener(Context context) {

        }

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

            Log.d("TAGMAPVIEW",mapPOIItem.getMapPoint().getMapPointWCONGCoord().x+", " +mapPOIItem.getMapPoint().getMapPointWCONGCoord().y);
            Log.d("TAGMAPVIEW",mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude+", " +mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude);

            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude, mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude), true);
            //?????? ?????????
            //???????????? ??????
            if (isPageOpen) {
                    page.startAnimation(translate_right);
                } else {
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translate_left);
            }
            Log.d("TAGMAPVIEW1111",mapPOIItem.getItemName());
            Log.d("TAGMAPVIEW1111",mapPOIItem.getMapPoint().toString());
            Info info = search(mapPOIItem.getItemName());
            title_tv.setText(info.getTitle());
            address_tv.setText(info.getAddress());
            phoneNumber_tv.setText(info.getNumber());
            Glide.with(page).load(info.getImageUrl()).into(image_iv);


        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
            //????????? ?????????
        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
            //????????? ?????????
            //???????????????
        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
            //????????? ??????????????? ??????
        }
    }
    //------------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}