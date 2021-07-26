package com.example.nolmyeon;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Rural;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static androidx.core.content.ContextCompat.getSystemService;

public class ServiceThread extends Thread {

    static double latitude;
    static double longitude;
    // 위치 제공자가 누군지를 담을 변수
    static String status;

    Handler handler;
    boolean isRun = true;
    public ServiceThread(Handler handler) { this.handler = handler; }
    public void stopForever() { synchronized (this) { this.isRun = false; } }
    public void run() {
        //반복적으로 수행할 작업을 한다.
        while (isRun) {
            handler.sendEmptyMessage( 0 );

            //쓰레드에 있는 핸들러에게 메세지를 보냄
            try {
                Thread.sleep( 5000 );//5초//10초씩 쉼
                //10초씩 쉰다
                } catch (Exception e) {
            }
        }
    }

}

