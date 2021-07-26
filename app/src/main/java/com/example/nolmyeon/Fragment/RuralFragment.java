package com.example.nolmyeon.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.nolmyeon.ImageSearchResponse;
import com.example.nolmyeon.R;
import com.example.nolmyeon.Service;
import com.example.nolmyeon.adapter.RuralViewAdapter;
import com.example.nolmyeon.model.Rural;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RuralFragment extends Fragment {

    private RecyclerView ruralRecyclerView;
    private RuralViewAdapter adapter;

    // 상속 액티비티 선언
    private Activity activity;
    private boolean isLoading = false;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rural,container,false);
        ruralRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_rural);
        ruralRecyclerView.addItemDecoration((new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL)));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ruralRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Rural> ruralArrayList = new ArrayList<>();
        ruralArrayList.addAll(GlobalApplication.getRuralArrayList());
        adapter = new RuralViewAdapter(getActivity(), ruralArrayList);
        ruralRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
 //       TextView title = (TextView) view.findViewById(R.id.item_title);
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
