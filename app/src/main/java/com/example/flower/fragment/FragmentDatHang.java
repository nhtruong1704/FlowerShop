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
import com.example.flower.interfaceI.HoaDoncallback;
import com.example.flower.firebase.DaoHoaDon;
import com.example.flower.model.HoaDon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FragmentDatHang extends Fragment {
    XacnhanAdapter xacnhanAdapter;
    DaoHoaDon daoHDCT;
    RecyclerView rcvthanhcong;
    ArrayList<HoaDon> arrayList;
    FirebaseUser firebaseUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmentgdd,container,false);
        rcvthanhcong= view.findViewById(R.id.rcvthanhcong);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvthanhcong.setLayoutManager(linearLayoutManager);
        daoHDCT = new DaoHoaDon(getActivity());
        arrayList=new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        daoHDCT.getAll(new HoaDoncallback() {
            @Override
            public void onSuccess(ArrayList<HoaDon> lists) {
                arrayList.clear();
                for (int i =0;i<lists.size();i++){
                    if (lists.get(i).getUiduser().equalsIgnoreCase(firebaseUser.getUid()) && lists.get(i).isCheck() == false){
                        arrayList.add(lists.get(i));
                        xacnhanAdapter = new XacnhanAdapter(arrayList,getActivity());
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
