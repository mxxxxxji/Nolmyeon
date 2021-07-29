package com.example.nolmyeon.model;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyData {
    public String text;
    public Uri uri;


    private long number;
    private String category;

    private String imgpath;

    private String contents;

    private String date;

    private double latitude;

    private double longitude;

    public MyData(String text, Uri uri, long number, String category, String imgpath, String contents, String date, double latitude, double longitude) {
        this.text = text;
        this.uri = uri;
        this.number = number;
        this.category = category;
        this.imgpath = imgpath;
        this.contents = contents;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "MyData{" +
                "text='" + text + '\'' +
                ", uri=" + uri +
                '}';
    }
}
