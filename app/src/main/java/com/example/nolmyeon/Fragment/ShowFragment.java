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
import com.example.nolmyeon.R;
import com.example.nolmyeon.adapter.ExhibitionViewAdapter;
import com.example.nolmyeon.adapter.ShowViewAdapter;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Show;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowFragment extends Fragment {
    private RecyclerView showRecyclerView;
    private ShowViewAdapter adapter;
    private ArrayList<Show> showArrayList;

    // 상속 액티비티 선언
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show,container,false);
        showRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerView_show);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        showRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.recyclerview_divider));
        showRecyclerView.addItemDecoration(dividerItemDecoration);


        ArrayList<Show> showArrayList = new ArrayList<>();
        showArrayList.addAll(GlobalApplication.getShowArrayList());
        Log.d("TAGSHOW",  showArrayList.toString());
        adapter = new ShowViewAdapter(getActivity(), showArrayList);
        showRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
    }
    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        if (context instanceof Activity) {
        }
    }
}
