package com.example.nolmyeon.adapter;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nolmyeon.Fragment.ImageFragment;

public class ImageViewAdapter extends FragmentStateAdapter {

    public int mCount;
    public ImageViewAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count;

    }
    @Override
    public ImageFragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new ImageFragment();
        else if(index==1) return new ImageFragment();
        else if(index==2) return new ImageFragment();
        else return new ImageFragment();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }
    public int getRealPosition(int position) { return position % mCount; }

}

