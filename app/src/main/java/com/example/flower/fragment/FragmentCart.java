package com.example.flower.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.R;
import com.example.flower.adapter.CartAdapter;
import com.example.flower.firebase.DaoHoaDon;
import com.example.flower.firebase.DaoStore;
import com.example.flower.firebase.DaoUser;
import com.example.flower.local.Localstorage;
import com.example.flower.model.HoaDon;
import com.example.flower.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentCart extends Fragment {
    RecyclerView rcvcart;
    CartAdapter cartAdapter =null;
    ArrayList<HoaDon> hdctArrayList;
    Localstorage localstorage;
    Gson gson;
    TextView titletoolbar,txtaddress,txttientong;
    RelativeLayout linearbackground;
    DaoHoaDon daoHDCT;
    DaoUser daoUser;
    DaoStore daoStore;
    Button btnthanhtoan;
    double tongtien=0;
    LinearLayout linearLayout;
    ScrollView scrollView;
    ArrayList<Order> orderArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentcart, container, false);
        rcvcart = view.findViewById(R.id.rcvcart);
        localstorage = new Localstorage(getActivity());

        linearbackground = view.findViewById(R.id.linearbackground);

        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        gson = new Gson();
        hdctArrayList = new ArrayList<>();
        orderArrayList = new ArrayList<>();
        orderArrayList = getCartList();
        if (orderArrayList.size() != 0) {
            cartAdapter = new CartAdapter(orderArrayList, getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rcvcart.setLayoutManager(linearLayoutManager);
            rcvcart.setAdapter(cartAdapter);

        }else {
            linearbackground.setBackgroundResource( R.drawable.catwithshoppingcart );
        }
        return view;


    }
    public ArrayList<Order> getCartList () {
        if (localstorage.getCart() != null) {
            String jsonCart = localstorage.getCart();
            Log.d("CART : ", jsonCart);
            Type type = new TypeToken<List<Order>>() {
            }.getType();
            orderArrayList = gson.fromJson(jsonCart, type);

            return orderArrayList;
        }
        return orderArrayList;
    }
}
