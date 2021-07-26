package com.example.nolmyeon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private long number;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("exhibition")
    @Expose
    private Integer exhibition;
    @SerializedName("festival")
    @Expose
    private Integer festival;
    @SerializedName("camping")
    @Expose
    private Integer camping;
    @SerializedName("rural")
    @Expose
    private Integer rural;
    @SerializedName("shows")
    @Expose
    private Integer shows;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getFestival() {
        return festival;
    }
    public void setFestival(Integer festival) {
        this.festival = festival;
    }
    public Integer getExhibition() {
        return exhibition;
    }
    public void setExhibition(Integer exhibition) {
        this.exhibition = exhibition;
    }
    public Integer getCamping() {
        return camping;
    }
    public void setCamping(Integer camping) {
        this.camping = camping;
    }
    public Integer getRural() {
        return rural;
    }
    public void setRural(Integer rural) {
        this.rural = rural;
    }
    public Integer getShows() {
        return shows;
    }
    public void setShows(Integer shows) {
        this.shows = shows;
    }

}
