package com.example.flower.daofirebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.flower.callback.Categoriescallback;
import com.example.flower.model.Categories;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DaoCategories {
    Context context;
    DatabaseReference mRef;
    String key;

    public DaoCategories(Context context) {
        this.context = context;
        this.mRef = FirebaseDatabase.getInstance().getReference("Categories");
    }
    public void getAll(final Categoriescallback callback) {
        final ArrayList<Categories> dataloai = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dataloai.clear();

                    for (DataSnapshot data : snapshot.getChildren()){
                        Categories categories = data.getValue(Categories.class);
                        dataloai.add(categories);
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
    public void insert(Categories item){
        // push cây theo mã tự tạo
        // string key lấy mã push
        key = mRef.push().getKey();
        //insert theo child mã key setvalue theo item
        mRef.child(key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Insert Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Insert Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean update(final Categories item){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("matheloai").getValue(String.class).equalsIgnoreCase(item.getMatheloai())){
                        key=dataSnapshot.getKey();
                        mRef.child(key).setValue(item);
                        Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();


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
                    if(dataSnapshot.child("matheloai").getValue(String.class).equalsIgnoreCase(matheloai)){
                        key=dataSnapshot.getKey();
                        mRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Delete Thành Công", Toast.LENGTH_SHORT).show();
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
