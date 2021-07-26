package com.example.nolmyeon;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //겍체 생성
    Retrofit retrofit = new Retrofit.Builder()
            //서버 url 설정
            .baseUrl("https://project-intern02.wjthinkbig.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public Service service = retrofit.create(Service.class);
}