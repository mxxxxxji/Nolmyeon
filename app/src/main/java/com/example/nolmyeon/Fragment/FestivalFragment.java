package com.example.nolmyeon.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.ImageSearchResponse;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.adapter.FestivalViewAdapter;
import com.example.nolmyeon.adapter.RuralViewAdapter;
import com.example.nolmyeon.model.Festival;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.nolmyeon.R;

public class FestivalFragment extends Fragment {

    private RecyclerView festivalRecyclerView;
    private FestivalViewAdapter adapter;
    private ArrayList<Festival> festivalArrayList = new ArrayList<>();

    // 상속 액티비티 선언
    private Activity activity;


    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_festival, container, false);
        festivalRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_festival);
        festivalRecyclerView.addItemDecoration((new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        festivalRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Festival> festivalList = new ArrayList<>();
        festivalList.addAll(GlobalApplication.getFestivalArrayList());
        Log.d("Festival_", "data size" + GlobalApplication.getFestivalArrayList().size());
        Log.d("Festival_", "data size" + festivalList.size());
        adapter = new FestivalViewAdapter(festivalList, getActivity());
        festivalRecyclerView.setAdapter(adapter);

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
