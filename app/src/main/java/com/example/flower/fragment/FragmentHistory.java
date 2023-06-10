package com.example.flower.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.flower.R;
import com.example.flower.adapter.Tabadapter;
import com.google.android.material.tabs.TabLayout;

public class FragmentHistory extends Fragment {
    private Tabadapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView back;
    TextView titletoolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmenthistory,container,false);
       getActivity(). getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.Xanhbaemin));
        tabLayout = view.findViewById(R.id.tabLayout);
        back =  view.findViewById(R.id.back);
        titletoolbar =  view.findViewById(R.id.toolbar_title);
        viewPager =view.findViewById(R.id.viewPager);
        adapter = new Tabadapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentDatHang(), "");
        adapter.addFragment(new FragmentGD(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        titletoolbar.setText("Order History");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentProfile()).commit();
            }
        });
        return view;

    }



}
