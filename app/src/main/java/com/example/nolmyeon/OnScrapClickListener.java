package com.example.nolmyeon;

import android.view.View;
import android.widget.ImageView;

import com.example.nolmyeon.adapter.ExhibitionViewAdapter;

public interface OnScrapClickListener {
    public void onExhibitionScrapClick(ExhibitionViewAdapter.Holder holder, View view, int position);
}
