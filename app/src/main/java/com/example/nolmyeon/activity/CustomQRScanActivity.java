package com.example.nolmyeon.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nolmyeon.CustomDialog;
import com.example.nolmyeon.CustomDialog2;
import com.example.nolmyeon.CustomDialog3;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.GpsTracker;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.model.Stamp;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomQRScanActivity extends Activity implements DecoratedBarcodeView.TorchListener {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    public boolean captureFlag = false; //[QR 스캔 후 지속적으로 스캔 방지 플래그]

    private String[] results;
    private String contents;
    private int check = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_qrscan);


        Animator animator = ValueAnimator.ofFloat(0f,1f).setDuration(1000);

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
      //  barcodeScannerView.getBarcodeView().setCameraSettings(came);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {

            @Override
            public void barcodeResult(BarcodeResult result) {
                try{

                    stampLogData();
                    if(captureFlag == false){
                        String result_data = "";
                        Log.d("TAG_qr", result+"");
                        String str = result.toString();
                        Log.d("TAG_qr", str);
                        results = str.split(",");
                        Log.d("TAG_qr", results[0]+"  "+results[1]+"  "+results[2]+"  "+results[3]);


                        for(int i=0; i<GlobalApplication.getStampArrayList().size(); i++){
                            if(GlobalApplication.getStampArrayList().get(i).getTitle().equals(results[1])){
                                check=1;
                            }
                        }

                        if(check==1){
                            CustomDialog customDialog = new CustomDialog(CustomQRScanActivity.this);
                            customDialog.callFunction(results);
                            //TODO [플래그 값을 변경 실시 : 중복 스캔 방지]
                            captureFlag = true;

                        }else{
                            GpsTracker gpsTracker = new GpsTracker(CustomQRScanActivity.this);

                            Location curLocation = new Location("");
                            curLocation.setLatitude(gpsTracker.getLatitude());
                            curLocation.setLongitude(gpsTracker.getLongitude());
                            Log.d("TAG_qr", gpsTracker.getLatitude() +", " +gpsTracker.getLongitude());
                            double latitude = Double.parseDouble(results[2]);
                            double longitude = Double.parseDouble(results[3]);
                            Location location = new Location("");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            Log.d("TAG_qr", latitude +", " +longitude);
                            float distance = curLocation.distanceTo(location);

                            if(Math.abs(distance) < 500){

                                Log.d("TAG_QR_DB", check+"11111111");
                                CustomDialog2 customDialog2 = new CustomDialog2(CustomQRScanActivity.this);
                                customDialog2.callFunction(results);
                                insertStampLog();
                                stampLogData();
                                //TODO [플래그 값을 변경 실시 : 중복 스캔 방지]
                                captureFlag = true;
                            }else{
                                CustomDialog3 customDialog3 = new CustomDialog3(CustomQRScanActivity.this);
                                customDialog3.callFunction(results);
                                //TODO [플래그 값을 변경 실시 : 중복 스캔 방지]
                                captureFlag = true;

                            }
                        }


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    private void insertStampLog() {
        String sql = "insert into stamp(number, category, title, date) values(" + GlobalApplication.getUser_number() + ", \'" + results[0] + "\', \'"+results[1] + "\', NOW())";
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<String> call =  retrofitClient.service.getInsertStampLog(sql);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body()==null){
                    Log.d("TAG_qr", "error!!");
                    Log.d("TAG_QR_DB", check+"22222");
                }else{
                    Log.d("TAG_qr", response.body());
                    Log.d("TAG_QR_DB", check+"333333333");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
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

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        Log.d("TAG_REQUESTQR", requestCode +" ");
//        Log.d("TAG_RESULTCODE", resultCode +" ");
//        Log.d("TAG_DATA", data +" ");
//        if(result == null){
//            Toast.makeText(this, "해당 QR코드가 아닙니다.", Toast.LENGTH_LONG).show();
//            super.onActivityResult(requestCode, resultCode, data);
//            // todo
//        }else{
//            super.onActivityResult(requestCode, resultCode, data);
//            String str = result.getContents();
//            results = str.split(",");
//

//
//            Log.d("TAG", "requestCode : " + requestCode);
//            Log.d("TAG", "resultCode : " + resultCode);
//            Log.d("TAG", "data : " + data);
//            // todo
//            Log.d("TAG", "result : " + result.getContents());
//            Log.d("TAG", "result : " + result.getBarcodeImagePath());
//
//        }
//
//    }
    @Override
    protected void onResume() {
        super.onResume();

        capture.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
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

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

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

}