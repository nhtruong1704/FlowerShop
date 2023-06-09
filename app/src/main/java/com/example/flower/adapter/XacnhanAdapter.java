package com.example.flower.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.R;
import com.example.flower.interfaceI.Usercallback;
import com.example.flower.firebase.DaoStore;
import com.example.flower.firebase.DaoUser;
import com.example.flower.model.HoaDon;
import com.example.flower.model.Order;
import com.example.flower.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class XacnhanAdapter extends RecyclerView.Adapter<XacnhanAdapter.MyViewHolder> {

    ArrayList<HoaDon> cartList;
    Context context;
    HoaDonAdapter cartAdapter;
    double tongtien=0;
    ArrayList<Order> orderArrayList;
    DaoUser daoUser;
    DaoStore daoStore;
    FirebaseUser firebaseUser;
    String tokkenstore ="";
    public XacnhanAdapter(ArrayList<HoaDon> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tinhtrangdonrow, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        orderArrayList = new ArrayList<>();
        daoStore = new DaoStore(context);
        daoUser = new DaoUser(context);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final HoaDon cart = cartList.get(position);
        if(cart.isCheck()){
            holder.txtxacnhan.setText("Đang giao hàng");
        }else{
            holder.txtxacnhan.setText("Đang giao hàng");
        }
        holder.txtday.setText(cart.getNgay());
        holder.txttime.setText(cart.getThoigian());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.bills );
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView txttongtien = (TextView) myDialog.findViewById(R.id.txttongtien);
                TextView  txtnguoimua =  myDialog.findViewById(R.id.txtnguoimua);
                final RecyclerView recyclerViewsp =  myDialog.findViewById(R.id.recyclesanpham);
                cartAdapter = new HoaDonAdapter(cart.getOrderArrayList(),context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
               recyclerViewsp.setLayoutManager(linearLayoutManager);
                recyclerViewsp.setAdapter(cartAdapter);
                daoUser.getAll(new Usercallback() {
                    @Override
                    public void onSuccess(ArrayList<User> lists) {
                        for (int i = 0;i<lists.size();i++){
                            if (lists.get(i).getToken().matches(firebaseUser.getUid())){
                                txtnguoimua.setText(lists.get(i).getEmail());
                            }
                        }
                        }

                    @Override
                    public void onError(String message) {

                    }
                });
                final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                decimalFormat.applyPattern("#,###,###,###");

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HoaDon");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        orderArrayList.clear();
                        double tongtien1 =0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            HoaDon hdct = dataSnapshot.getValue( HoaDon.class);
                            if (hdct.getIdhct().equalsIgnoreCase(cart.getIdhct())){
                                orderArrayList.addAll(hdct.getOrderArrayList());

                                for (Order order : orderArrayList){
                                    tongtien1 += order.getSoluongmua() * order.getFood().getGia();
                                    tokkenstore = order.getStore().getEmail();
                                }


                                txttongtien.setText("Tổng Tiền: \t" + decimalFormat.format(tongtien1)+"VNĐ");
                                break;

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                myDialog.show();
            }

        });


    }

    @Override
    public int getItemCount() {

        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView card_view;
        TextView txtxacnhan, txttime, txtday,txtnguoimua,txtnguoiban;
        TextView plus, minus;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtxacnhan =  itemView.findViewById(R.id.txtxacnhan);
            txttime =  itemView.findViewById(R.id.txttime);
            txtday =  itemView.findViewById(R.id.txtday);

            card_view =  itemView.findViewById(R.id.card_view);
        }
    }
}
