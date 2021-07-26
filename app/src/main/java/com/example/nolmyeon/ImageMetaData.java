package com.example.nolmyeon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageMetaData {
    @SerializedName("total_count")
    public int total_count;

    @SerializedName("is_end")
    public boolean is_end;
}
