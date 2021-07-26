package com.example.nolmyeon;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nolmyeon.activity.CustomQRScanActivity;
import com.example.nolmyeon.activity.MainActivity;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Stamp;
import com.example.nolmyeon.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialog2{

    public Context context;
    GlobalApplication app;

    public CustomDialog2(@NonNull Context context) {
        this.context = context;

    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String[] results) {

        Log.d("TAG_qr",results.toString());
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog2);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();
        Log.d("TAG_qr","1111111111");
        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView title_tv = dlg.findViewById(R.id.title_tv);
        final ImageButton okButton = dlg.findViewById(R.id.ok_btn);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Log.d("TAG_qr","22222222222222");
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                title_tv.setText(results[1]);
                RetrofitClient retrofitClient = new RetrofitClient();
                Call<ArrayList<User>> call = retrofitClient.service.updateStamp(results[0], GlobalApplication.getUser_number());
                call.enqueue(new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        if(response.isSuccessful() && response.body() != null){
                            ArrayList<User> updateUserData = new ArrayList<>();
                            updateUserData.addAll(response.body());
                            GlobalApplication.setAllUser(updateUserData);
                            myDataUpdate(updateUserData);
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.d("TAG_qr","No!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                });

                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
                ((CustomQRScanActivity)context).finish();

            }

        });
    }

    public void myDataUpdate(ArrayList<User> AllUserData){
        for(int i =0; i<AllUserData.size(); i++){
            if(GlobalApplication.getUser_number() == AllUserData.get(i).getNumber()){
                GlobalApplication.setExhibition(AllUserData.get(i).getExhibition());
                GlobalApplication.setFestival(AllUserData.get(i).getFestival());
                GlobalApplication.setCamping(AllUserData.get(i).getCamping());
                GlobalApplication.setRural(AllUserData.get(i).getRural());
                GlobalApplication.setShows(AllUserData.get(i).getShows());
            }
        }
    }


}
