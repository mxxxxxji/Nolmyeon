package com.example.nolmyeon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Festival {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("fstvlNm")
    @Expose
    private String fstvlNm;

    @SerializedName("opar")
    @Expose
    private String opar;

    @SerializedName("fstvlStartDate")
    @Expose
    private String fstvlStartDate;

    @SerializedName("fstvlEndDate")
    @Expose
    private String fstvlEndDate;


    @SerializedName("fstvlCo")
    @Expose
    private String fstvlCo;

    @SerializedName("auspcInstt")
    @Expose
    private String auspcInstt;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("homepageUrl")
    @Expose
    private String homepageUrl;

    @SerializedName("relateInfo")
    @Expose
    private String relateInfo;

    @SerializedName("rdnmadr")
    @Expose
    private String rdnmadr;

    @SerializedName("lnmadr")
    @Expose
    private String lnmadr;

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

    public String getFstvlNm() {
        return fstvlNm;
    }

    public void setFstvlNm(String fstvlNm) {
        this.fstvlNm = fstvlNm;
    }

    public String getOpar() {
        return opar;
    }

    public void setOpar(String opar) {
        this.opar = opar;
    }

    public String getFstvlStartDate() {
        return fstvlStartDate;
    }

    public void setFstvlStartDate(String fstvlStartDate) {
        this.fstvlStartDate = fstvlStartDate;
    }

    public String getFstvlEndDate() {
        return fstvlEndDate;
    }

    public void setFstvlEndDate(String fstvlEndDate) {
        this.fstvlEndDate = fstvlEndDate;
    }

    public String getFstvlCo() {
        return fstvlCo;
    }

    public void setFstvlCo(String fstvlCo) {
        this.fstvlCo = fstvlCo;
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

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getRelateInfo() {
        return relateInfo;
    }

    public void setRelateInfo(String relateInfo) {
        this.relateInfo = relateInfo;
    }

    public String getRdnmadr() {
        return rdnmadr;
    }

    public void setRdnmadr(String rdnmadr) {
        this.rdnmadr = rdnmadr;
    }

    public String getLnmadr() {
        return lnmadr;
    }

    public void setLnmadr(String lnmadr) {
        this.lnmadr = lnmadr;
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
