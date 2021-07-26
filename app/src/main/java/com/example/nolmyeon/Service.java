package com.example.nolmyeon;

import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Photo;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Show;
import com.example.nolmyeon.model.Stamp;
import com.example.nolmyeon.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Service {

    @GET("/exhibition")
    Call<ArrayList<Exhibition>> getExhibition();
    @GET("/rural")
    Call<ArrayList<Rural>> getRural();
    @GET("/camping")
    Call<ArrayList<Camping>> getCamping();
    @GET("/festival")
    Call<ArrayList<Festival>> getFestival();
    @GET("/show")
    Call<ArrayList<Show>> getShow();

    @GET("/v2/search/image")
    Call<ImageSearchResponse> geRepos(@Header("Authorization") String app_key, @Query("query") String query);

    @GET("/putUser")
    Call<String> putUser(@Query("number")long number, @Query("email")String email, @Query("name")String name);
    @GET("/user")
    Call<User> check(@Query("number") String number);
    @GET("/userAll")
    Call<ArrayList<User>> getAllUser();


    @GET("/updateStamp")
    Call<ArrayList<User>> updateStamp(@Query("category") String category, @Query("number") long userNumber);
    @GET("/stampLog1")
    Call<ArrayList<Stamp>> getStampLog1(@Query("number")long userNumber);
    @GET("/stampLog2")
    Call<ArrayList<Stamp>> getStampLog2(@Query("number")long userNumber, @Query("category") String category);

    @GET("/insertStampLog")
    Call<String> getInsertStampLog(@Query("sql")String sql);

    @GET("/photo")
    Call<String> getPhoto(@Query("sql")String sql);

    @GET("/photolog")
    Call<ArrayList<Photo>> getPhotoLog();
    @GET("/MyPhotoLog")
    Call<ArrayList<Photo>> getMyPhotoLog();

}
