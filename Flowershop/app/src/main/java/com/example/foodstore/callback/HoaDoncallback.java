package com.example.flowerstore.callback;
import com.example.flowerstore.model.HoaDon;


import java.util.ArrayList;

public interface HoaDoncallback {
    void onSuccess(ArrayList<HoaDon> lists);
    void onError(String message);
}
