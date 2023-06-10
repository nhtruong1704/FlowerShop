package com.example.flower.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.flower.R;
import com.example.flower.adapter.XacnhanAdapter;
import com.example.flower.callback.HoaDoncallback;
import com.example.flower.daofirebase.DaoHoaDon;
import com.example.flower.model.HoaDon;
import com.example.flower.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FragmentdsDonDatHang extends Fragment {
    XacnhanAdapter xacnhanAdapter;
    DaoHoaDon daoHDCT;
    RecyclerView rcvthanhcong;
    ArrayList<HoaDon> arrayList;
    FirebaseUser firebaseUser;
    public static String uidstore1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmentdondathang,container,false);
        rcvthanhcong= view.findViewById(R.id.rcvthanhcong);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvthanhcong.setLayoutManager(linearLayoutManager);
        daoHDCT = new DaoHoaDon(getActivity());
        arrayList=new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        daoHDCT.getAll(new HoaDoncallback() {
            @Override
            public void onSuccess(ArrayList<HoaDon> lists) {
                String uidstore = "";
                arrayList.clear();
                ArrayList<Order> orderArrayList = new ArrayList<>();
                orderArrayList.clear();
                for (int i =0;i<lists.size();i++){
                    if ( lists.get(i).isCheck() == false) {

                        orderArrayList.addAll(lists.get(i).getOrderArrayList());
                    }
                    for (int j = 0; j < orderArrayList.size(); j++) {
                            if (orderArrayList.get(j).getStore().getTokenstore().equalsIgnoreCase(firebaseUser.getUid())) {
                                uidstore = orderArrayList.get(j).getStore().getTokenstore();
                            }
                        }
                }
                for (int k = 0; k < lists.size(); k++) {
                    if (uidstore.equalsIgnoreCase(firebaseUser.getUid()) && lists.get(k).isCheck() == false) {
                        arrayList.add(lists.get(k));
                        xacnhanAdapter = new XacnhanAdapter(arrayList, getActivity());
                        rcvthanhcong.setAdapter(xacnhanAdapter);
                    }
                }
            }
            @Override
            public void onError(String message) {

            }
        });
        return view;
    }
}
