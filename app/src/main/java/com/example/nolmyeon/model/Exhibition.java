package com.example.nolmyeon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Exhibition {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("fcltyNm")
    @Expose
    private String title;

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

    @SerializedName("operPhoneNumber")
    @Expose
    private String operPhoneNumber;

    @SerializedName("operInstitutionNm")
    @Expose
    private String operInstitutionNm;

    @SerializedName("homepageUrl")
    @Expose
    private String homepageUrl;

    @SerializedName("fcltyInfo")
    @Expose
    private String fcltyInfo;

    @SerializedName("weekdayOperOpenHhmm")
    @Expose
    private String weekdayOperOpenHhmm;

    @SerializedName("weekdayOperColseHhmm")
    @Expose
    private String weekdayOperColseHhmm;

    @SerializedName("holidayOperOpenHhmm")
    @Expose
    private String holidayOperOpenHhmm;

    @SerializedName("holidayCloseOpenHhmm")
    @Expose
    private String holidayCloseOpenHhmm;

    @SerializedName("rstdeInfo")
    @Expose
    private String rstdeInfo;

    @SerializedName("adultChrge")
    @Expose
    private String adultChrge;

    @SerializedName("yngbgsChrge")
    @Expose
    private int yngbgsChrge;

    @SerializedName("childChrge")
    @Expose
    private int childChrge;

    @SerializedName("etcChrgeInfo")
    @Expose
    private String etcChrgeInfo;

    @SerializedName("fcltyIntrcn")
    @Expose
    private String fcltyIntrcn;


    @SerializedName("trnsportInfo")
    @Expose
    private String trnsportInfo;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("hinstitutionNm")
    @Expose
    private String institutionNm;

    @SerializedName("referenceDate")
    @Expose
    private String referenceDate;

    @SerializedName("insttCode")
    @Expose
    private String insttCode;

    @SerializedName("url")
    @Expose
    private ArrayList<String> url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getOperPhoneNumber() {
        return operPhoneNumber;
    }

    public void setOperPhoneNumber(String operPhoneNumber) {
        this.operPhoneNumber = operPhoneNumber;
    }

    public String getOperInstitutionNm() {
        return operInstitutionNm;
    }

    public void setOperInstitutionNm(String operInstitutionNm) {
        this.operInstitutionNm = operInstitutionNm;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getFcltyInfo() {
        return fcltyInfo;
    }

    public void setFcltyInfo(String fcltyInfo) {
        this.fcltyInfo = fcltyInfo;
    }

    public String getWeekdayOperOpenHhmm() {
        return weekdayOperOpenHhmm;
    }

    public void setWeekdayOperOpenHhmm(String weekdayOperOpenHhmm) {
        this.weekdayOperOpenHhmm = weekdayOperOpenHhmm;
    }

    public String getWeekdayOperColseHhmm() {
        return weekdayOperColseHhmm;
    }

    public void setWeekdayOperColseHhmm(String weekdayOperColseHhmm) {
        this.weekdayOperColseHhmm = weekdayOperColseHhmm;
    }

    public String getHolidayOperOpenHhmm() {
        return holidayOperOpenHhmm;
    }

    public void setHolidayOperOpenHhmm(String holidayOperOpenHhmm) {
        this.holidayOperOpenHhmm = holidayOperOpenHhmm;
    }

    public String getHolidayCloseOpenHhmm() {
        return holidayCloseOpenHhmm;
    }

    public void setHolidayCloseOpenHhmm(String holidayCloseOpenHhmm) {
        this.holidayCloseOpenHhmm = holidayCloseOpenHhmm;
    }

    public String getRstdeInfo() {
        return rstdeInfo;
    }

    public void setRstdeInfo(String rstdeInfo) {
        this.rstdeInfo = rstdeInfo;
    }

    public String getAdultChrge() {
        return adultChrge;
    }

    public void setAdultChrge(String adultChrge) {
        this.adultChrge = adultChrge;
    }

    public int getYngbgsChrge() {
        return yngbgsChrge;
    }

    public void setYngbgsChrge(int yngbgsChrge) {
        this.yngbgsChrge = yngbgsChrge;
    }

    public int getChildChrge() {
        return childChrge;
    }

    public void setChildChrge(int childChrge) {
        this.childChrge = childChrge;
    }

    public String getEtcChrgeInfo() {
        return etcChrgeInfo;
    }

    public void setEtcChrgeInfo(String etcChrgeInfo) {
        this.etcChrgeInfo = etcChrgeInfo;
    }

    public String getFcltyIntrcn() {
        return fcltyIntrcn;
    }

    public void setFcltyIntrcn(String fcltyIntrcn) {
        this.fcltyIntrcn = fcltyIntrcn;
    }

    public String getTrnsportInfo() {
        return trnsportInfo;
    }

    public void setTrnsportInfo(String trnsportInfo) {
        this.trnsportInfo = trnsportInfo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInstitutionNm() {
        return institutionNm;
    }

    public void setInstitutionNm(String institutionNm) {
        this.institutionNm = institutionNm;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getInsttCode() {
        return insttCode;
    }

    public void setInsttCode(String insttCode) {
        this.insttCode = insttCode;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rdnmadr='" + rdnmadr + '\'' +
                ", lnmadr='" + lnmadr + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", operPhoneNumber='" + operPhoneNumber + '\'' +
                ", operInstitutionNm='" + operInstitutionNm + '\'' +
                ", homepageUrl='" + homepageUrl + '\'' +
                ", fcltyInfo='" + fcltyInfo + '\'' +
                ", weekdayOperOpenHhmm='" + weekdayOperOpenHhmm + '\'' +
                ", weekdayOperColseHhmm='" + weekdayOperColseHhmm + '\'' +
                ", holidayOperOpenHhmm='" + holidayOperOpenHhmm + '\'' +
                ", holidayCloseOpenHhmm='" + holidayCloseOpenHhmm + '\'' +
                ", rstdeInfo='" + rstdeInfo + '\'' +
                ", adultChrge='" + adultChrge + '\'' +
                ", yngbgsChrge=" + yngbgsChrge +
                ", childChrge=" + childChrge +
                ", etcChrgeInfo='" + etcChrgeInfo + '\'' +
                ", fcltyIntrcn='" + fcltyIntrcn + '\'' +
                ", trnsportInfo='" + trnsportInfo + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", institutionNm='" + institutionNm + '\'' +
                ", referenceDate='" + referenceDate + '\'' +
                ", insttCode='" + insttCode + '\'' +
                '}';
    }
}
