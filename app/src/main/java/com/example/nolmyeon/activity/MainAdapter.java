package com.example.nolmyeon.activity;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolmyeon.R;
import com.example.nolmyeon.databinding.ItemMainBinding;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter {

    private ArrayList<String> names;
    private Context context;

    public MainAdapter(ArrayList<String> names, Context context) {
        this.names = names;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMainBinding binding = ItemMainBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        return new MainHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MainHolder holder = (MainHolder) viewHolder;
        ItemMainBinding binding = holder.binding;
        binding.textView.setText(names.get(i));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class MainHolder extends RecyclerView.ViewHolder {
        ItemMainBinding binding;

        public MainHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
