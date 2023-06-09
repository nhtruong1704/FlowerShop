package com.example.flowerstore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.flowerstore.fragment.FragmentdsDonDatHang;
import com.example.flowerstore.fragment.Fragmentcategory;
import com.example.flowerstore.fragment.Fragmentproflie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
   public static Toolbar toolbar;
   public static BottomNavigationView bnv;
    TextView titletoolbar;
    public static String idstore="";
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv = findViewById(R.id.bnv);
        toolbar = findViewById(R.id.toolbar);
        titletoolbar = findViewById(R.id.toolbar_title);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.xam));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titletoolbar.setText("Home");
        titletoolbar.setTextSize(30);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        idstore=intent.getStringExtra("email");


        toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.ds:
                        titletoolbar.setText("Danh sách đơn hàng");
                        fragment = new FragmentdsDonDatHang();
                        loadFragment(fragment);
                        return true;
                    case R.id.category:
                        titletoolbar.setText("Thêm món ăn");
                        fragment = new Fragmentcategory();
                        loadFragment(fragment);
                        return true;
                    case R.id.profile:
                        titletoolbar.setText("Thông tin");
                        fragment = new Fragmentproflie();
                        loadFragment(fragment);
                        return true;
                }


                return false;
            }
        });


    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_l, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}