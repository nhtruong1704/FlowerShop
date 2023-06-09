package com.example.flower.interfaceI;
import com.example.flower.model.User;

import java.util.ArrayList;

public interface Usercallback {
    void onSuccess(ArrayList<User> lists);
    void onError(String message);
}
