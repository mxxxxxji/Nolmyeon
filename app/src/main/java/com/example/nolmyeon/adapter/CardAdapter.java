package com.example.nolmyeon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nolmyeon.R;

import org.jetbrains.annotations.NotNull;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    String data1[], data2[];
    int images[];
    Context context;
    public CardAdapter(Context ct, String s1[], String s2[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }


    @NonNull
    @NotNull
    @Override
    public CardAdapter.CardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CardAdapter.CardViewHolder holder, int position) {
        holder.textView1.setText(data1[position]);
        holder.textView2.setText(data2[position]);
        holder.imageView.setImageResource(images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent i = new Intent(context, Main2Activity.class);
                i.putExtra("title",data1[position]);
                i.putExtra("description",data2[position]);
                i.putExtra("images",images[position]);
                context.startActivity(i);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;
        ImageView imageView;
        ConstraintLayout mainLayout;

        public CardViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
