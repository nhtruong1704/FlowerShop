package com.example.flower.interfaceI;

import com.example.flower.model.ChatUserStore;

import java.util.ArrayList;

public interface Chatuserstorecallback {
    void onSuccess(ArrayList<ChatUserStore> lists);
    void onError(String message);
}
