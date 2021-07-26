package com.example.nolmyeon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageSearchResponse {
    @SerializedName("meta")
    public ImageMetaData metadata;
    @SerializedName("documents")
    public List<Image> documents;
}
