package com.example.admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.model.Store;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    ArrayList<Store> categoryList;
    Context context;
    String Tag;

    public StoreAdapter(ArrayList<Store> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.hienthiuser, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Store categories = categoryList.get(position);
        holder.title.setText(categories.getEmail());
            Picasso.get()
                    .load(categories.getImage())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Error : ", e.getMessage());
                        }
                    });





    }

    @Override
    public int getItemCount() {
        return categoryList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
