package com.example.nolmyeon.model;

import android.graphics.Point;

import java.util.ArrayList;

public class PointData {
    public String title;
    public double latiitude;
    public double longitude;
    public PointData(String title, double latiitude, double longitude) {
        this.title = title;
        this.latiitude = latiitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatiitude() {
        return latiitude;
    }

    public void setLatiitude(double latiitude) {
        this.latiitude = latiitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
