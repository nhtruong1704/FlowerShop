package com.example.flower.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flower.MapActivity;
import com.example.flower.R;
import com.example.flower.interfaceI.Usercallback;
import com.example.flower.firebase.DaoUser;
import com.example.flower.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment {
    private Switch darkModeSwitch;
    CircleImageView profileCircleImageView;
    TextView usernameTextView, email, logout,history,txteditprofile,txtchangepassword,map;
    DaoUser daoUser;
    FirebaseUser firebaseUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentthontin, container, false);
        profileCircleImageView = view.findViewById(R.id.profileCircleImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        email=view.findViewById(R.id.email);
        logout = view.findViewById(R.id.logout);
        txtchangepassword = view.findViewById(R.id.txtchangepassword);
        map = view.findViewById(R.id.map);
        txteditprofile = view.findViewById(R.id.txteditprofile);
        history = view.findViewById(R.id.history);
        daoUser = new DaoUser(getActivity());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        daoUser.getAll(new Usercallback() {
            @Override
            public void onSuccess(ArrayList<User> lists) {
                for (int i =0 ; i<lists.size();i++){
                    if (lists.get(i).getToken().equalsIgnoreCase(firebaseUser.getUid())){
                        email.setText(lists.get(i).getEmail());
                        usernameTextView.setText(lists.get(i).getName());
                        Picasso.get()
                                .load(lists.get(i).getImage()).into(profileCircleImageView);

                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
//        if (new DarkModePrefManager(getActivity()).isNightMode()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//        }
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentHistory()).commit();            }
        });
        txteditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentEditProfile()).commit();            }
        });
        txtchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentChangePassword()).commit();            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent endMain = new Intent(Intent.ACTION_MAIN);
                endMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(endMain);
                getActivity().finish();
                Toast.makeText(getActivity(), "See you later", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }


}
