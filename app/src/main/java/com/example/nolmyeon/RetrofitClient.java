package com.example.nolmyeon;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //겍체 생성

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS);

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            //서버 url 설정
            .baseUrl("https://project-intern02.wjthinkbig.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    public Service service = retrofit.create(Service.class);
}