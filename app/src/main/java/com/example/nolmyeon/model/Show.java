package com.example.nolmyeon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Show {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("eventNm")
    @Expose
    private String eventNm;

    @SerializedName("opar")
    @Expose
    private String opar;

    @SerializedName("eventCo")
    @Expose
    private String eventCo;

    @SerializedName("eventStartDate")
    @Expose
    private String eventStartDate;

    @SerializedName("eventEndDate")
    @Expose
    private String eventEndDate;

    @SerializedName("eventStartTime")
    @Expose
    private String eventStartTime;

    @SerializedName("eventEndTime")
    @Expose
    private String eventEndTime;

    @SerializedName("mnnst")
    @Expose
    private String mnnst;

    @SerializedName("auspcInstt")
    @Expose
    private String auspcInstt;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("seatNumber")
    @Expose
    private String seatNumber;

    @SerializedName("admfee")
    @Expose
    private String admfee;

    @SerializedName("atpn")
    @Expose
    private String atpn;

    @SerializedName("homepageUrl")
    @Expose
    private String homepageUrl;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("url")
    @Expose
    private ArrayList<String> url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventNm() {
        return eventNm;
    }

    public void setEventNm(String eventNm) {
        this.eventNm = eventNm;
    }

    public String getOpar() {
        return opar;
    }

    public void setOpar(String opar) {
        this.opar = opar;
    }

    public String getEventCo() {
        return eventCo;
    }

    public void setEventCo(String eventCo) {
        this.eventCo = eventCo;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getMnnst() {
        return mnnst;
    }

    public void setMnnst(String mnnst) {
        this.mnnst = mnnst;
    }

    public String getAuspcInstt() {
        return auspcInstt;
    }

    public void setAuspcInstt(String auspcInstt) {
        this.auspcInstt = auspcInstt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getAdmfee() {
        return admfee;
    }

    public void setAdmfee(String admfee) {
        this.admfee = admfee;
    }

    public String getAtpn() {
        return atpn;
    }

    public void setAtpn(String atpn) {
        this.atpn = atpn;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
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

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
