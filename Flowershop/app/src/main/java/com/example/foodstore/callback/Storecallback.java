package com.example.flowerstore.callback;
import com.example.flowerstore.model.Store;

import java.util.ArrayList;

public interface Storecallback {
    void onSuccess(ArrayList<Store> lists);
    void onError(String message);
}
