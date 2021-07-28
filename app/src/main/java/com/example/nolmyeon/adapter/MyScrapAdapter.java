package com.example.nolmyeon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nolmyeon.GlobalApplication;
import com.example.nolmyeon.R;
import com.example.nolmyeon.activity.MainActivity2;
import com.example.nolmyeon.model.Camping;
import com.example.nolmyeon.model.Exhibition;
import com.example.nolmyeon.model.Festival;
import com.example.nolmyeon.model.Info;
import com.example.nolmyeon.model.MyData;
import com.example.nolmyeon.model.Rural;
import com.example.nolmyeon.model.Scrap;
import com.example.nolmyeon.model.Show;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyScrapAdapter extends RecyclerView.Adapter<MyScrapAdapter.Holder> {
    private ArrayList<Scrap> listData = new ArrayList<>();
    Context context;
    Exhibition findExhibition = null;
    Festival findFestival = null;
    Camping findCamping = null;
    Rural findRural = null;
    Show findShow = null;
    public MyScrapAdapter(Context context, ArrayList<Scrap> listData) {
        this.listData = listData;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_scrap, parent, false);
        MyScrapAdapter.Holder holder = new MyScrapAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, int position) {
        holder.tv_title.setText(listData.get(position).getTitle());
        Info info = search(listData.get(position).getTitle());
        Glide.with(context).load(info.getImageUrl()).into(holder.iv_image);
        holder.text.setText(info.getAddress());
        holder.text2.setText(info.getNumber());
        holder.text3.setText(info.getHoliday());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_image;
        TextView text;
        TextView text2;
        TextView text3;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.item_title);
            iv_image = itemView.findViewById(R.id.item_image);
            text = itemView.findViewById(R.id.text);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);

        }
    }
    public Info search(String title) {
        ArrayList<Exhibition> exhibitionArrayList = GlobalApplication.getExhibitionList();
        ArrayList<Festival> festivalArrayList = GlobalApplication.getFestivalArrayList();
        ArrayList<Camping> campingArrayList = GlobalApplication.getCampingArrayList();
        ArrayList<Rural> ruralArrayList = GlobalApplication.getRuralArrayList();
        ArrayList<Show> showArrayList = GlobalApplication.getShowArrayList();
        Info info = new Info();
        for(Exhibition e : exhibitionArrayList){
            if((e.getTitle()).equals(title)){
                findExhibition = e;
                info.setTitle(findExhibition.getTitle());
                info.setAddress(findExhibition.getRdnmadr());
                info.setNumber(findExhibition.getPhoneNumber());
                info.setDescription(findExhibition.getFcltyInfo());
                info.setHoliday(findExhibition.getRstdeInfo());
                info.setImageUrl(findExhibition.getUrl().get(0));
                break;
            }
        }
        for( Festival f : festivalArrayList){
            if((f.getFstvlNm()).equals(title)){
                findFestival = f;
                info.setTitle(findFestival.getFstvlNm());
                info.setAddress(findFestival.getRdnmadr());
                info.setNumber(findFestival.getPhoneNumber());
                info.setDescription(findFestival.getFstvlCo());
                info.setImageUrl(findFestival.getUrl().get(0));
                break;
            }
        }
        for( Camping c : campingArrayList){
            if((c.getCampgNm()).equals(title)){
                findCamping = c;
                info.setTitle(findCamping.getCampgNm());
                info.setAddress(findCamping.getRdnmadr());
                info.setNumber( findCamping.getPhoneNumber());
                info.setDescription( findCamping.getCampgSe());
                info.setImageUrl(findCamping.getUrl().get(0));
                break;
            }
        }
        for( Rural r : ruralArrayList){
            if((r.getExprnVilageNm()).equals(title)){
                findRural = r;
                info.setTitle(findRural.getExprnVilageNm());
                info.setAddress(findRural.getRdnmadr());
                info.setNumber(findRural.getHomepageUrl());
                info.setDescription(findRural.getExprnCn());
                info.setImageUrl(findRural.getUrl().get(0));
                break;
            }
        }
        for( Show s : showArrayList){
            if((s.getEventNm()).equals(title)){
                findShow = s;
                info.setTitle(findShow.getEventNm());
                info.setAddress(findShow.getOpar());
                info.setNumber(findShow.getPhoneNumber());
                info.setDescription(findShow.getEventStartDate() +" ~ " + findShow.getEventEndDate());
                info.setImageUrl(findShow.getUrl().get(0));
                break;
            }
        }
        return info;
    }
}
