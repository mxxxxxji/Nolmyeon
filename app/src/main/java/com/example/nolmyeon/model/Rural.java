package com.example.nolmyeon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rural {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("exprnVilageNm")
    @Expose
    private String exprnVilageNm; //마을이름

    @SerializedName("exprnSe")
    @Expose
    private String exprnSe; //체험 종류1

    @SerializedName("exprnCn")
    @Expose
    private String exprnCn; //체험 종류2

    @SerializedName("exprnPicUrl")
    @Expose
    private String exprnPicUrl; //사진url


    @SerializedName("rdnmadr")
    @Expose
    private String rdnmadr; //도로명주소

    @SerializedName("institutionNm")
    @Expose
    private String institutionNm; //주최도시

    @SerializedName("appnDate")
    @Expose
    private String appnDate;

    @SerializedName("homepageUrl")
    @Expose
    private String homepageUrl;


    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("instt_code")
    @Expose
    private String instt_code;

    @SerializedName("instt_nm")
    @Expose
    private String instt_nm;

    @SerializedName("url")
    @Expose
    private ArrayList<String> url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExprnVilageNm() {
        return exprnVilageNm;
    }

    public void setExprnVilageNm(String exprnVilageNm) {
        this.exprnVilageNm = exprnVilageNm;
    }

    public String getExprnSe() {
        return exprnSe;
    }

    public void setExprnSe(String exprnSe) {
        this.exprnSe = exprnSe;
    }

    public String getExprnCn() {
        return exprnCn;
    }

    public void setExprnCn(String exprnCn) {
        this.exprnCn = exprnCn;
    }

    public String getExprnPicUrl() {
        return exprnPicUrl;
    }

    public void setExprnPicUrl(String exprnPicUrl) {
        this.exprnPicUrl = exprnPicUrl;
    }

    public String getRdnmadr() {
        return rdnmadr;
    }

    public void setRdnmadr(String rdnmadr) {
        this.rdnmadr = rdnmadr;
    }

    public String getInstitutionNm() {
        return institutionNm;
    }

    public void setInstitutionNm(String institutionNm) {
        this.institutionNm = institutionNm;
    }

    public String getAppnDate() {
        return appnDate;
    }

    public void setAppnDate(String appnDate) {
        this.appnDate = appnDate;
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

    public String getInstt_code() {
        return instt_code;
    }

    public void setInstt_code(String instt_code) {
        this.instt_code = instt_code;
    }

    public String getInstt_nm() {
        return instt_nm;
    }

    public void setInstt_nm(String instt_nm) {
        this.instt_nm = instt_nm;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
