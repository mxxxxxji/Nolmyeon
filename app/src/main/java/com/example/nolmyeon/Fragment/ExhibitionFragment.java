package com.example.nolmyeon.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.ImageSearchResponse;
import com.example.nolmyeon.R;
import com.example.nolmyeon.RetrofitClient;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.adapter.ExhibitionViewAdapter;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Scrap;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExhibitionFragment extends Fragment {

    private RecyclerView exhibiRcyclerView;
    private ExhibitionViewAdapter adapter;
    private ArrayList<Exhibition> exhibitionArrayList;

    // 상속 액티비티 선언
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exhibition,container,false);
        exhibiRcyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_exhibition);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        exhibiRcyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Exhibition> exhibitionList = new ArrayList<>();
        exhibitionList.addAll(GlobalApplication.getExhibitionList());
        adapter = new ExhibitionViewAdapter(getActivity(), exhibitionList);
        exhibiRcyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());

//        TextView title = (TextView) view.findViewById(R.id.item_title);
//        title.setText(String.valueOf(position));
    }
    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        if (context instanceof Activity) {
        }
    }


}
