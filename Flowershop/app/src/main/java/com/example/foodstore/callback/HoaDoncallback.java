package com.example.flower.callback;
import com.example.flower.model.HoaDon;


import java.util.ArrayList;

public interface HoaDoncallback {
    void onSuccess(ArrayList<HoaDon> lists);
    void onError(String message);
}
