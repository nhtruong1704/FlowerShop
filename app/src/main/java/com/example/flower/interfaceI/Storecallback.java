package com.example.flower.interfaceI;

import com.example.flower.model.Store;

import java.util.ArrayList;

public interface Storecallback {
    void onSuccess(ArrayList<Store> lists);
    void onError(String message);
}
