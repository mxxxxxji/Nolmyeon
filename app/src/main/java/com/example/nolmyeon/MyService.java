package com.example.nolmyeon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Rural;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MyService extends Service {

    // 위도 경도를 담을 변수 static 이 코드 전체에 부담을 준다.
    // 가능 하면 지역 변수로 그리고 클래스 마다 간섭하지 못하게 하는것이 좋다.
    static double latitude;
    static double longitude;
    // 위치 제공자가 누군지를 담을 변수
    static String status;

    NotificationManager Notifi_M;
    ServiceThread thread;

    static SharedPreferences sharedPref = null;
    static SharedPreferences.Editor editor = null;

    PreferencesManager prefManager = new PreferencesManager();
    static int saveCheck; //prefManager.getIntPreferences(this, "timeSaveCheck");

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("TAG>>", "mainService is onCreate");
        sharedPref = getSharedPreferences("CurrentTime", MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG_MYSERVICE>>","onstartcommand");
        //채널열기
        createNotificationChannel(this);
        //notification 셋팅
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( MyService.this )
                .setSmallIcon( R.drawable.custom_icon )
                .setLargeIcon( BitmapFactory.decodeResource( getResources(), R.drawable.bf_change_apple_s) )
                .setContentTitle( "Title" )
                .setContentText( "ContentText" )
                .setAutoCancel( true )
     //           .setContentIntent( pendingIntent )
                .setDefaults( Notification.DEFAULT_ALL )
                .setOnlyAlertOnce( true )
                .setChannelId( "my_notification" )
                .setColor( Color.parseColor( "#ffffff" ) );
        //foreground에서 시작
        startForeground(1, notificationBuilder.build());

        onStartInit();
        Notifi_M = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();

//        Intent stampIntent = new Intent(getApplicationContext(), Stamp.class);
////        //포그라운드 서비스 상태에서 알림 누를 시 StampActivity로 이동
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, stampIntent, PendingIntent.FLAG_CANCEL_CURRENT);
////
////        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
////            NotificationChannel channel = new NotificationChannel("channel", "play!!", NotificationManager.IMPORTANCE_DEFAULT);
////
////            //Notification과 채널 연결
////            NotificationManager notificationManager = ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE));
////            notificationManager.createNotificationChannel(channel);
////
////            //Notification 세팅
////            NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "channel")
////                    .setSmallIcon(R.drawable.ic_launcher_background)
////                    .setContentTitle("현재 실행중인 앱 이름")
////                    .setContentIntent(pendingIntent)
////                    .setContentText("");
////            //id값은 0보다 큰 양수여야 함
////            notificationManager.notify(1, notification.build());
////            //foreground에서 시작
////            startForeground(1, notification.build());
//        }
        return START_STICKY;
    }

    private void onStartInit() {

    }

    @Override
    public void onDestroy() {
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();


    }
    public void createNotificationChannel(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("channel", "play!!", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(false);
            notificationManager.createNotificationChannel(channel);
        }

    }
    public class myServiceHandler extends Handler{
        @Override
        public void handleMessage(android.os.Message msg) {
//            NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
//            Intent intent = new Intent( MyService.this, Stamp.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP );
//            PendingIntent pendingIntent = PendingIntent.getActivity( MyService.this, 0, intent, PendingIntent.FLAG_ONE_SHOT );
//            Uri soundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                @SuppressLint("WrongConstant")
//                NotificationChannel notificationChannel = new NotificationChannel( "my_notification", "n_channel", NotificationManager.IMPORTANCE_MAX );
//                notificationChannel.setDescription( "description" );
//                notificationChannel.setName( "Channel Name" );
//                assert notificationManager != null;
//                notificationManager.createNotificationChannel( notificationChannel );
//
//            }
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( MyService.this )
//                    .setSmallIcon( R.drawable.custom_icon )
//                    .setLargeIcon( BitmapFactory.decodeResource( getResources(), R.drawable.artgallery ) )
//                    .setContentTitle( "Title" )
//                    .setContentText( "ContentText" )
//                    .setAutoCancel( true )
//                    .setSound( soundUri )
//                    .setContentIntent( pendingIntent )
//                    .setDefaults( Notification.DEFAULT_ALL )
//                    .setOnlyAlertOnce( true )
//                    .setChannelId( "my_notification" )
//                    .setColor( Color.parseColor( "#ffffff" ) );



//            //.setProgress(100,50,false);
//            long mNow = System.currentTimeMillis();
//            Date mDate = new Date(mNow);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            Log.d("MYSERVICE","현재시간 : " + simpleDateFormat.format(mDate));
//            assert notificationManager != null;
//            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
//            Calendar cal = Calendar.getInstance();
//            int hour = cal.get( Calendar.HOUR_OF_DAY );
//            if (hour == 10) {
//                notificationManager.notify( m, notificationBuilder.build() );
//                thread.stopForever();
//            } else if (hour == 11) {
//                notificationManager.notify( m, notificationBuilder.build() );
//                thread.stopForever();
//            }
//
//



//            Log.e("longi>>","" + longi);
//            Log.e("lati>>","" + lati);
//            Toast.makeText(getApplication(), "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

            ArrayList<Rural> ruralArrayList = GlobalApplication.getRuralArrayList();
            ArrayList<Exhibition> exhibitionArrayList = GlobalApplication.getExhibitionList();
            ArrayList<Camping> campingArrayList = GlobalApplication.getCampingArrayList();

//            // 현재위치가
//            GpsTracker gpsTracker = new GpsTracker(getApplication());
//            Location curLocation = new Location("");
//            curLocation.setLatitude(gpsTracker.getLatitude());
//            curLocation.setLongitude(gpsTracker.getLongitude());
//            if(saveCheck == 0){
//                for(int i=0; i<exhibitionArrayList.size(); i++){
//                    double latitude = exhibitionArrayList.get(i).getLatitude();
//                    double longitude = exhibitionArrayList.get(i).getLongitude();
//
//                    Location location = new Location("");
//                    location.setLatitude(latitude);
//                    location.setLongitude(longitude);
//
//                    float distance = curLocation.distanceTo(location);
//
//
//                    if(Math.abs(distance) < 3) { //범위 내에 있다면
//                        Log.d("MyService", distance + "km : "+exhibitionArrayList.get(i).getTitle());
//                        //현재 시간 확인
//                        long mNow = System.currentTimeMillis();
//                        Date date = new Date(mNow);
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(date);
//
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                        Log.d("MYSERVICE","현재시간 : " + simpleDateFormat.format(cal.getTime()));
//                        prefManager.setPreferences(getApplication(),"currentTime", simpleDateFormat.format(date));
//                        prefManager.setPreferences(getApplication(), "studyLatitude", (float)latitude);
//                        prefManager.setPreferences(getApplication(), "studyLongitude", (float)longitude);
//
//                        saveCheck = 1;
//                        cal.add(Calendar.MINUTE, 1);
//                        Log.d("MYSERVICE","30분 뒤 : " + simpleDateFormat.format(cal.getTime()));
//                        prefManager.setPreferences(getApplication(), "endTime", simpleDateFormat.format(cal.getTime()));
//                    }
//
//
//                }
//            }
//            // 학습장소에 도착했지만 30분(1분) 내에 학습 장소를 벗어난 경우
//            double latitude = prefManager.getFloatPreferences(getApplication(), "studyLatitude");
//            double longitude = prefManager.getFloatPreferences(getApplication(), "studyLongitude");
//            Location location = new Location("");
//            location.setLatitude(latitude);
//            location.setLongitude(longitude);
//            float distance = curLocation.distanceTo(location);
//
//            if(saveCheck == 1 && Math.abs(distance) > 3){
//
//
//            }
//            // 만약 현재 시간이 처음시간보다 30분을 넘는다면
//            // 알림



        }
    }

}
