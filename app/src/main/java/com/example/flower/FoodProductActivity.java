package com.example.flower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flower.adapter.FoodAdapter;
import com.example.flower.interfaceI.Foodcallback;
import com.example.flower.firebase.DaoFood;
import com.example.flower.model.Food;

import java.util.ArrayList;

public class FoodProductActivity extends AppCompatActivity {
    ArrayList<Food> foodArrayList;
    DaoFood daoFood;
    FoodAdapter foodAdapter;
    Toolbar toolbar;
    TextView titletoolbar;
    RecyclerView rcvfood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listfood );
        toolbar = findViewById(R.id.toolbar);
        titletoolbar = findViewById(R.id.toolbar_title);
        rcvfood = findViewById(R.id.rcvfood);
        Intent getdata = getIntent();
        String matl = getdata.getStringExtra("matl");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titletoolbar.setText("Chi tiết món ăn");
        titletoolbar.setTextSize(30);
        daoFood = new DaoFood(FoodProductActivity.this);
        foodArrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodArrayList,FoodProductActivity.this);
        GridLayoutManager idLayoutManager = new GridLayoutManager(FoodProductActivity.this,2);
        rcvfood.setLayoutManager(idLayoutManager);
        rcvfood.setHasFixedSize(true);
        rcvfood.setAdapter(foodAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodProductActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                finish();
            }
        });
        daoFood.getAll(new Foodcallback() {
            @Override
            public void onSuccess(ArrayList<Food> lists) {
                foodArrayList.clear();
                for (int i =0;i<lists.size();i++){
                    if (lists.get(i).getMatheloai().equalsIgnoreCase(matl)){
                        foodArrayList.add(lists.get(i));
                        foodAdapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onError(String message) {

            }
        });
    }


}