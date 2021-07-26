package com.example.nolmyeon.model;

import android.net.Uri;

public class MyData {
    public String text;
    public Uri uri;
    public MyData(String text, Uri uri){
        this.text = text;
        this.uri = uri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
