package com.example.nolmyeon;

import android.app.Application;
import android.content.Context;

import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.MyData;
import com.example.nolmyeon.model.Photo;
import com.example.nolmyeon.model.PointData;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Scrap;
import com.example.nolmyeon.model.Show;
import com.example.nolmyeon.model.Stamp;
import com.example.nolmyeon.model.User;
import com.kakao.sdk.common.KakaoSdk;

import java.util.ArrayList;

public class GlobalApplication extends Application {
 //   private static GlobalApplication instance;
    public static Context context;
    private String id;
    private String title;
    private String data_test;
    private static double latitude;
    private static double longitude;
    //-------------------------------------------------------

    private static ArrayList<PointData> exhibitionDist = new ArrayList<PointData>();
    private static ArrayList<Exhibition> exhibitionList = new ArrayList<Exhibition>();
    private static ArrayList<Rural> ruralArrayList  = new ArrayList<>();
    private static ArrayList<Camping> campingArrayList = new ArrayList<>();
    private static ArrayList<Festival> festivalArrayList = new ArrayList<>();
    private static ArrayList<Show> showArrayList = new ArrayList<>();
    private static ArrayList<Stamp> stampArrayList = new ArrayList<>();
    private static ArrayList<MyData> myDataset = new ArrayList<>();
    private static ArrayList<MyData> allDataset = new ArrayList<>();
    private static ArrayList<Ranker> rankerArrayList = new ArrayList<>();
    private static ArrayList<Integer> exhibitionFlag = new ArrayList<>();
    private static ArrayList<Scrap> scrapArrayList = new ArrayList<>();
    private static ArrayList<Photo> photoArrayList = new ArrayList<>();
    //사용자 정보
    public static String user_name;
    public static long user_number;
    public static String user_email;

    private static int exhibition;
    private static int festival;
    private static int camping;
    private static int rural;
    private static int shows;
    private static int admin; //관리자 계정인지 여부

    private static String date;


    //모든 사용자 정보
    private static ArrayList<User> allUser = new ArrayList<User>();

    public static ArrayList<Exhibition> getExhibitionList() {
        return exhibitionList;
    }

    public static void setExhibitionList(ArrayList<Exhibition> exhibitionList) {
        GlobalApplication.exhibitionList = exhibitionList;
    }

    public static ArrayList<PointData> getExhibitionDist() {
        return exhibitionDist;
    }
    public static void setExhibitionDist(ArrayList<PointData> exhibitionDist) {
        GlobalApplication.exhibitionDist = exhibitionDist;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        GlobalApplication.user_name = user_name;
    }

    public static long getUser_number() {
        return user_number;
    }

    public static void setUser_number(long user_number) {
        GlobalApplication.user_number = user_number;
    }

    public static String getUser_email() {
        return user_email;
    }

    public static void setUser_email(String user_email) {
        GlobalApplication.user_email = user_email;
    }

    public static int getFestival() {
        return festival;
    }

    public static void setFestival(int festival) {
        GlobalApplication.festival = festival;
    }

    public static void setExhibition(int exhibition) {
        GlobalApplication.exhibition = exhibition;
    }

    public static int getExhibition() {
        return exhibition;
    }

    public static int getCamping() {
        return camping;
    }

    public static void setCamping(int camping) {
        GlobalApplication.camping = camping;
    }

    public static int getRural() {
        return rural;
    }

    public static void setRural(int rural) {
        GlobalApplication.rural = rural;
    }


    public static int getShows() {
        return shows;
    }

    public static void setShows(int shows) {
        GlobalApplication.shows = shows;
    }

    public static int getAdmin() {
        return admin;
    }

    public static void setAdmin(int admin) {
        GlobalApplication.admin = admin;
    }
//-------------------------------------------------------------------------------------------------

    public static ArrayList<User> getAllUser() {
        return allUser;
    }

    public static void setAllUser(ArrayList<User> allUser) {
        GlobalApplication.allUser = allUser;
    }

    //-------------------------------------------------------------------------------------------------

    public static ArrayList<Rural> getRuralArrayList() {
        return ruralArrayList;
    }

    public static void setRuralArrayList(ArrayList<Rural> ruralArrayList) {
        GlobalApplication.ruralArrayList = ruralArrayList;
    }

    public static ArrayList<Camping> getCampingArrayList() {
        return campingArrayList;
    }

    public static void setCampingArrayList(ArrayList<Camping> campingArrayList) {
        GlobalApplication.campingArrayList = campingArrayList;
    }

    public static ArrayList<Festival> getFestivalArrayList() {
        return festivalArrayList;
    }

    public static void setFestivalArrayList(ArrayList<Festival> festivalArrayList) {
        GlobalApplication.festivalArrayList = festivalArrayList;
    }

    public static ArrayList<Show> getShowArrayList() {
        return showArrayList;
    }

    public static void setShowArrayList(ArrayList<Show> showArrayList) {
        GlobalApplication.showArrayList = showArrayList;
    }

    public static ArrayList<Stamp> getStampArrayList() {
        return stampArrayList;
    }

    public static void setStampArrayList(ArrayList<Stamp> stampArrayList) {
        GlobalApplication.stampArrayList = stampArrayList;
    }

    public static ArrayList<MyData> getMyDataset() {
        return myDataset;
    }

    public static void setMyDataset(ArrayList<MyData> myDataset) {
        GlobalApplication.myDataset = myDataset;
    }

    public static ArrayList<Ranker> getRankerArrayList() {
        return rankerArrayList;
    }

    public static void setRankerArrayList(ArrayList<Ranker> rankerArrayList) {
        GlobalApplication.rankerArrayList = rankerArrayList;
    }

    public static ArrayList<Integer> getExhibitionFlag() {
        return exhibitionFlag;
    }


    public static void setExhibitionFlag(ArrayList<Integer> exhibitionFlag) {
        GlobalApplication.exhibitionFlag = exhibitionFlag;
    }

    public static ArrayList<Scrap> getScrapArrayList() {
        return scrapArrayList;
    }

    public static void setScrapArrayList(ArrayList<Scrap> scrapArrayList) {
        GlobalApplication.scrapArrayList = scrapArrayList;
    }

    public static ArrayList<Photo> getPhotoArrayList() {
        return photoArrayList;
    }

    public static void setPhotoArrayList(ArrayList<Photo> photoArrayList) {
        GlobalApplication.photoArrayList = photoArrayList;
    }

    public static ArrayList<MyData> getAllDataset() {
        return allDataset;
    }

    public static void setAllDataset(ArrayList<MyData> allDataset) {
        GlobalApplication.allDataset = allDataset;
    }

    //-------------------------------------------------------------------------------------------------
    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        GlobalApplication.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        GlobalApplication.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getData_test() {
        return data_test;
    }

    public void setData_test(String data_test) {
        this.data_test = data_test;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        GlobalApplication.date = date;
    }

    //-----------------------------------------------------------------------------



    @Override
    public void onCreate() {
        super.onCreate();
  //      instance = this;
        context = getApplicationContext();
        // 네이티브 앱 키로 초기화
        KakaoSdk.init(this, "cb8f21040716c13e2643bcd18e8f81c6");
    }



}
