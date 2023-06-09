package com.example.flower.interfaceI;


import com.example.flower.model.Categories;

import java.util.ArrayList;

public interface Categoriescallback {
    void onSuccess(ArrayList<Categories> lists);
    void onError(String message);
}
