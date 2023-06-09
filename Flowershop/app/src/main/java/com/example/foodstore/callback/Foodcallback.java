package com.example.flowerstore.callback;

import com.example.flowerstore.model.Food;

import java.util.ArrayList;

public interface Foodcallback {
    void onSuccess(ArrayList<Food> lists);
    void onError(String message);
}
