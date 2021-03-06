package com.example.nolmyeon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.GpsTracker;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.model.MyData;
import com.example.nolmyeon.model.Photo;
import com.example.nolmyeon.model.Stamp;
import com.example.nolmyeon.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoActivity extends Activity {
    private final int GALLERY_CODE = 10;
    ImageView  photo;
    Button upload_btn;
    private FirebaseStorage storage;

    // ??????
    private int mYear =0, mMonth=0, mDay=0;
    // ???????????? ?????????
    Spinner spinner;
    TextView category_tv;
    EditText title_et;
    EditText contents_et;

    String title;
    String date;
    String category;
    String path;
    String contents;

    //?????? ?????? ????????????
    private GpsTracker gpsTracker;
    private double latitude;
    private double longitude;

    Uri file;
    String sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        photo = findViewById(R.id.photo);
        upload_btn = findViewById(R.id.upload_btn);
        title_et = findViewById(R.id.title_et);
        contents_et = findViewById(R.id.contents_et);
        storage = FirebaseStorage.getInstance();


        // ????????? ???????????? ??????????????? ?????? ?????? ??????.
        TextView tv = findViewById(R.id.date_tv);
        Calendar cal = Calendar.getInstance();
        tv.setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));

        // ???????????? ?????????
        category_tv = findViewById(R.id.category_tv);
        spinner = findViewById(R.id.spinner);
        String[] items = {"??????", "??????", "??????", "?????????", "??????"};
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(
                this, R.layout.spinner_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_tv.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gpsTracker = new GpsTracker(this);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();


        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file==null){
                    Toast.makeText(PhotoActivity.this, "????????? ???????????? ??????????????????!!", Toast.LENGTH_SHORT).show();
                }else{
                    title = title_et.getText().toString();
                    category = spinner.getSelectedItem().toString();
                    contents = contents_et.getText().toString();
                    loadAlbum();

                    if(GlobalApplication.getDate()!=null){
                        Log.d("DATE_CHECK", GlobalApplication.getDate());
                        sql = "insert into photo (number, title, category, imgpath, contents, date, latitude, longitude)" +
                                " values("+GlobalApplication.getUser_number()+ ",'"+ title +"', '"+category+"', '"+ path+"', '"+ contents +"','" + GlobalApplication.getDate() +"'," +latitude+", "+longitude+")";
                        Log.d("TAG_PHOTO__", sql);
                        Intent intent = new Intent(PhotoActivity.this, PhotoLoadingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("sql", sql);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.d("DATE_CHECK", "date is null!!");
                    }

                }


            }
        });


    }

    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    // Date Picker?????? ????????? ????????? TextView??? ??????
                    TextView tv = findViewById(R.id.date_tv);
                    tv.setText(String.format("%d-%d-%d", yy,mm+1,dd));
                    date = String.format("%d-%d-%d", yy,mm+1,dd);
                    Log.d("DATE_CHECK", date);
                    GlobalApplication.setDate(date);
                }
            };
    public void onDateClick(View view){
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
    }

    public void onClick_upload(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);

    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        DatePickerDialog dpd = new DatePickerDialog
                (PhotoActivity.this, // ??????????????? ????????????
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view,
                                                  int year, int monthOfYear,int dayOfMonth) {
                                Toast.makeText(getApplicationContext(),
                                        year+"??? "+(monthOfYear+1)+"??? "+dayOfMonth+"??? ??? ??????????????????",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        , // ???????????? ???????????? ??? ??????????????? ???????????????
                        //    ????????? ????????? ??????
                        2015, 6, 21); // ????????? ?????????
        return dpd;
    }

    private void loadAlbum(){

        StorageReference storageReference = storage.getReference();
        path = "photo/" + title +".png";
        StorageReference riverRef = storageReference.child(path);
        UploadTask uploadTask  = riverRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(PhotoActivity.this, "????????? ??????????????? ????????? ?????? ???????????????", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PhotoActivity.this, "????????? ??????????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    //    downloadImg();
                }
            });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE){
            if(data!=null){
                 file = data.getData();
                try{
                    InputStream in = getContentResolver().openInputStream(file);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    photo.setImageBitmap(img);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }
//    public void downloadImg() {
//        ArrayList<MyData> myDataset = new ArrayList<>();
//        for (int i = 1; i < 4; i++) {
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            StorageReference storageReference = storage.getReference();
//
//            int finalI = i;
//            storageReference.child("photo/" + finalI + ".png").getDownloadUrl()
//                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            myDataset.add(new MyData(finalI + ".png", uri));
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull @NotNull Exception e) {
//
//                }
//            });
//        }
//        GlobalApplication.setMyDataset(myDataset);
//    }
}