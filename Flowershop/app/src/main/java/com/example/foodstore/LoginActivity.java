package com.example.flower;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.example.flower.adapter.Tabadapter;
import com.example.flower.fragment.FragmentLogin;
import com.example.flower.fragment.FragmentSignup;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {
    TabLayout tableLayout;
    CardView facebook,twitter,google;
    private Tabadapter adapter;
    ViewPager viewPager;
    float v=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tableLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapter = new Tabadapter(this.getSupportFragmentManager());
        adapter.addFragment(new FragmentLogin(), "Đăng nhập");
        adapter.addFragment(new FragmentSignup(), "Đăng ký");
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.hong));
    }
}