package com.example.nolmyeon.model;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Info {
    private String title;
    private String address;
    private String number;
    private String description;
    private String holiday;
    private String imageUrl;

    public Info(String title, String address, String number, String description, String holiday, String imageUrl) {
        this.title = title;
        this.address = address;
        this.number = number;
        this.description = description;
        this.holiday = holiday;
        this.imageUrl = imageUrl;
    }

    public Info() {
        this.title = null;
        this.address = null;
        this.number = null;
        this.description = null;
        this.holiday = null;
        this.imageUrl = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return super.toString();
    }
}
