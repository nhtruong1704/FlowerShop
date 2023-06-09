package com.example.flower;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


import com.example.flower.adapter.Tabadapter;
import com.example.flower.fragment.FragmentLogin;
import com.example.flower.fragment.FragmentSignup;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TabLayout tableLayout;
    CardView facebook,twitter,google;
    private Tabadapter adapter;
    public  static ViewPager viewPager;

    float v=0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tableLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Intent is = new Intent(this, MainActivity.class);
            startActivity(is);
        }

        adapter = new Tabadapter(this.getSupportFragmentManager());
        adapter.addFragment(new FragmentLogin(), "Đăng nhập");
        adapter.addFragment(new FragmentSignup(), "Đăng ký");
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.Xanhbaemin));
    }
}