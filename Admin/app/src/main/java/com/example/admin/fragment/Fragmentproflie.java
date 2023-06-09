package com.example.admin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.admin.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragmentproflie extends Fragment {
    CircleImageView logoanh;
    TextView logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentprofile,container,false);
        logoanh = view.findViewById(R.id.logoanh);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent endMain = new Intent(Intent.ACTION_MAIN);
                endMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(endMain);
                getActivity().finish();
                Toast.makeText(getActivity(), "Hẹn gặp lại", Toast.LENGTH_SHORT).show();

            }
        });
        Picasso.get().load("https://media.istockphoto.com/vectors/vector-hand-drawn-cute-cats-face-saying-hello-isolated-illustration-vector-id1133603780?k=20&m=1133603780&s=612x612&w=0&h=-aweT5QVkPVQVeqewvWrvZ5fEpYmcWhnGG0uTI0JK5c=").into(logoanh);
        return view;
    }
}
