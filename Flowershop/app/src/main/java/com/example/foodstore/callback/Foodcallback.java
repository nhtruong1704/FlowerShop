package com.example.flower.callback;

import com.example.flower.model.Food;

import java.util.ArrayList;

public interface Foodcallback {
    void onSuccess(ArrayList<Food> lists);
    void onError(String message);
}
