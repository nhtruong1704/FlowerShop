package com.example.flower.interfaceI;
import com.example.flower.model.HoaDon;


import java.util.ArrayList;

public interface HoaDoncallback {
    void onSuccess(ArrayList<HoaDon> lists);
    void onError(String message);
}
