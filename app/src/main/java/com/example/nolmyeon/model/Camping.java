package com.example.nolmyeon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Camping {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("campgNm")
    @Expose
    private String campgNm;
    @SerializedName("campgSe")
    @Expose
    private String campgSe;


    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("rdnmadr")
    @Expose
    private String rdnmadr;

    @SerializedName("lnmadr")
    @Expose
    private String lnmadr;

    @SerializedName("officePhoneNumber")
    @Expose
    private String officePhoneNumber;

    @SerializedName("campgUnitSpce")
    @Expose
    private String campgUnitSpce;

    @SerializedName("dayAcmdCo")
    @Expose
    private String dayAcmdCo;

    @SerializedName("cvntl")
    @Expose
    private String cvntl;

    @SerializedName("safentl")
    @Expose
    private String safentl;

    @SerializedName("useTime")
    @Expose
    private String useTime;

    @SerializedName("useCharge")
    @Expose
    private String useCharge;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("referenceDate")
    @Expose
    private String referenceDate;

    @SerializedName("institutionNm")
    @Expose
    private String institutionNm;

    @SerializedName("url")
    @Expose
    private ArrayList<String> url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampgNm() {
        return campgNm;
    }

    public void setCampgNm(String campgNm) {
        this.campgNm = campgNm;
    }

    public String getCampgSe() {
        return campgSe;
    }

    public void setCampgSe(String campgSe) {
        this.campgSe = campgSe;
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

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    public String getCampgUnitSpce() {
        return campgUnitSpce;
    }

    public void setCampgUnitSpce(String campgUnitSpce) {
        this.campgUnitSpce = campgUnitSpce;
    }

    public String getDayAcmdCo() {
        return dayAcmdCo;
    }

    public void setDayAcmdCo(String dayAcmdCo) {
        this.dayAcmdCo = dayAcmdCo;
    }

    public String getCvntl() {
        return cvntl;
    }

    public void setCvntl(String cvntl) {
        this.cvntl = cvntl;
    }

    public String getSafentl() {
        return safentl;
    }

    public void setSafentl(String safentl) {
        this.safentl = safentl;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getUseCharge() {
        return useCharge;
    }

    public void setUseCharge(String useCharge) {
        this.useCharge = useCharge;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getInstitutionNm() {
        return institutionNm;
    }

    public void setInstitutionNm(String institutionNm) {
        this.institutionNm = institutionNm;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}
