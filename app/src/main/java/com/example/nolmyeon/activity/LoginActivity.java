package com.example.nolmyeon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.model.User;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 추가해야할 것
// LoginActivity - Dialog
// MainActivity - Logout 버튼
public class LoginActivity extends Activity {
    private static final String TAG="사용자";
    private ImageButton btn_login;
    int check = 0;
    GlobalApplication app = (GlobalApplication)getApplication();
    Retrofit retrofit;
    Service service;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);


        allUserData();

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, (oAuthToken, error) -> {
                    if (error != null) {
                        Log.e(TAG, "로그인 실패", error); // 다이얼로그로 띄위기
                    } else if (oAuthToken != null) {
                        Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                        UserApiClient.getInstance().me((user, meError) -> {
                            if (meError != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", meError);
                            } else {
                                for(int i=0; i<app.getAllUser().size(); i++){
                                    if(app.getAllUser().get(i).getNumber()==user.getId()){
                                        check=1;
                                    }
                                }
                                if(check==1){//DB에 사용자정보가 있을 때
                                    Log.d("TAG_NEW_USER", "check : "+ check);
                                    System.out.println("로그인 완료");
                                    Log.i(TAG, user.getKakaoAccount().getProfile().getNickname() +
                                            user.getId() +
                                            user.getKakaoAccount().getEmail());

                                    app.setUser_name(user.getKakaoAccount().getProfile().getNickname());
                                    app.setUser_number(user.getId());
                                    app.setUser_email(user.getKakaoAccount().getEmail());

                                    UserData();
                                    startMainActivity();
                                }else{//DB에 사용자 정보가 없을 때
                                    Log.d("TAG_NEW_USER", "check : "+ check);
                                    putUserData(user.getId(), user.getKakaoAccount().getEmail(), user.getKakaoAccount().getProfile().getNickname());
                                    app.setUser_name(user.getKakaoAccount().getProfile().getNickname());
                                    app.setUser_number(user.getId());
                                    app.setUser_email(user.getKakaoAccount().getEmail());
                                    UserData();
                                    startMainActivity();
                                }
                            }
                            return null;
                        });
                    }
                    return null;
                });
            }
        });
    }
    public void putUserData(long number, String email, String name){
        Log.d("TAG_NEW_USER", number + email+name);
        Retrofit retrofit;
        Service service;
        //객체 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("https://project-intern02.wjthinkbig.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
        Call<String> call = service.putUser(number, email, name);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("TTAG_NEW_USER", response.body()+"");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TTAG_NEW_USER", t.getMessage());
            }
        });
    }
    public void allUserData(){
        Retrofit retrofit;
        Service service;

        long number = app.getUser_number();
        //객체 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("https://project-intern02.wjthinkbig.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
        Log.d("MypageTAG",number+"");
        Call<ArrayList<User>> call =  service.getAllUser();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                Log.d("MypageTAG", ""+response.isSuccessful());
                if(response.isSuccessful() && response.body() != null){

                    ArrayList<User> allUser = new ArrayList<User>();
                    allUser.addAll(response.body());
                    app.setAllUser(allUser);
                    Log.d("MypageTAG", response + "");
                    Log.d("MypageTAG", response.body().toString() + "");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.d("LoadingTAG", t.getMessage());
            }
        });
    }
    public void UserData(){
        long number = app.getUser_number();

        //객체 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("https://project-intern02.wjthinkbig.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
        Log.d("TAG_NEW_USER",number+"");
        Call<User> call =  service.check(number+"");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.d("TAG_NEW_USER", (response.isSuccessful())+"11111141");
                if(response.isSuccessful() && response.body() != null){
                    Log.d("LoadingTAG", response + "");
                    User userInfo = response.body();
                    Log.d("TAG_NEW_USER", response.body().toString() + "");
                    Log.d("TAG_NEW_USER", userInfo.getName() + "");
                    app.setExhibition(userInfo.getExhibition());
                    app.setFestival(userInfo.getFestival());
                    app.setCamping(userInfo.getCamping());
                    app.setRural(userInfo.getRural());
                    app.setShows(userInfo.getShows());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("LoadingTAG", t.getMessage());
            }
        });
    }
    protected void startMainActivity(){ //MainActivity로 이동
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //액티비티를 종료할 때 애니메이션 없애기
        overridePendingTransition(0,0);
    }

}