package com.example.flowerstore.callback;
import com.example.flowerstore.model.Categories;

import java.util.ArrayList;

public interface Categoriescallback {
    void onSuccess(ArrayList<Categories> lists);
    void onError(String message);
}
