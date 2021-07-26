package com.example.nolmyeon.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.Image;
import com.example.nolmyeon.ImageSearchResponse;
import com.example.nolmyeon.R;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.adapter.CampingViewAdapter;
import com.example.nolmyeon.adapter.RuralViewAdapter;
import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Rural;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CampingFragment extends Fragment {
    private RecyclerView campingRecyclerView;
    private CampingViewAdapter adapter;
;

    ArrayList<Camping> campingArrayList;
    // 상속 액티비티 선언
    private Activity activity;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_camping,container,false);
        campingRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_camping);
        campingRecyclerView.addItemDecoration((new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        campingRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Camping> campingsList = new ArrayList<>();
        campingsList.addAll(GlobalApplication.getCampingArrayList());
      //  Log.d("TAG", campingsList.get(0).getCampgNm());
        adapter = new CampingViewAdapter(getActivity(), campingsList);
        campingRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        if (context instanceof Activity) {
        }
    }

}
