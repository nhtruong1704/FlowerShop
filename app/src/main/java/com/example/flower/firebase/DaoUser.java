package com.example.flower.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.flower.interfaceI.Usercallback;
import com.example.flower.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaoUser {
    Context context;
    DatabaseReference mRef;
    String key;

    public DaoUser(Context context) {
        this.context = context;
        this.mRef = FirebaseDatabase.getInstance().getReference("User");
    }
    public void getAll(final Usercallback callback) {
        final ArrayList<User> dataloai = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dataloai.clear();

                    for (DataSnapshot data : snapshot.getChildren()){
                       User user = data.getValue(User.class);
                        dataloai.add(user);
                    }
                    callback.onSuccess(dataloai);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toString());
            }
        });
    }
    public void insert(User item){
        // push cây theo mã tự tạo
        // string key lấy mã push
        key = mRef.push().getKey();
        //insert theo child mã key setvalue theo item
        mRef.child(key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Sign up failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean update(final User item){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue(String.class).equalsIgnoreCase(item.getEmail())){
                        key=dataSnapshot.getKey();
                        mRef.child(key).setValue(item);
                        Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return true;
    }
    public void delete(final String matheloai){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue(String.class).equalsIgnoreCase(matheloai)){
                        key=dataSnapshot.getKey();
                        mRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
