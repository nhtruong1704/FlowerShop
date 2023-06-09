package com.example.flower;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flower.adapter.CartAdapter;
import com.example.flower.interfaceI.Usercallback;
import com.example.flower.firebase.DaoHoaDon;
import com.example.flower.firebase.DaoStore;
import com.example.flower.firebase.DaoUser;
import com.example.flower.local.Localstorage;
import com.example.flower.model.HoaDon;
import com.example.flower.model.Order;
import com.example.flower.model.Token;
import com.example.flower.model.User;
import com.example.flower.notification.DataHoaDon;
import com.example.flower.notification.SenderHoaDon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {
    RecyclerView rcvcart;
    CartAdapter cartAdapter =null;
    ArrayList<HoaDon> hdctArrayList;
    Localstorage localstorage;
    Gson gson;
    Toolbar toolbar;
    TextView titletoolbar,txtaddress,txttientong;
    RelativeLayout linearbackground;
    DaoHoaDon daoHDCT;
    DaoUser daoUser;
    DaoStore daoStore;
    Button btnthanhtoan;
    double tongtien=0;
    LinearLayout linearLayout;
    ScrollView scrollView;
    FirebaseUser user;
    ArrayList<Order> orderArrayList;
    private RequestQueue requestQueue;
    String namefood,nameuser,namestore;
    int slm;
    double gia;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinhtien );
        rcvcart=findViewById(R.id.rcvcart);
        localstorage = new Localstorage(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(ThanhToanActivity.this, R.color.Xanhbaemin ));
        toolbar = findViewById(R.id.toolbar);
        scrollView = findViewById(R.id.scrollView);
        linearLayout = findViewById(R.id.linear1);
        txtaddress = findViewById(R.id.txtaddress);
        txttientong = findViewById(R.id.txttientong);
        btnthanhtoan = findViewById(R.id.btn_insertcart);
        linearbackground = findViewById(R.id.linearbackground);
        titletoolbar = findViewById(R.id.toolbar_title);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titletoolbar.setText("Thanh toán");
        titletoolbar.setTextSize(30);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        gson = new Gson();
        hdctArrayList=new ArrayList<>();
        orderArrayList=new ArrayList<>();
        orderArrayList= getCartList();
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HoaDon");
        String keyhdct = databaseReference.push().getKey();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        SimpleDateFormat tg = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String thoigian = tg.format(new Date());
        hdctArrayList.clear();


        for (Order order: getCartList()){
         namestore=  order.getStore().getTokenstore();
            nameuser=  order.getUser().getEmail();
        }
        HoaDon hoadonchitiet = new HoaDon(keyhdct,keyhdct,currentDateandTime,thoigian,false,user.getUid(),getCartList());
        hdctArrayList.add(hoadonchitiet);
        Log.i("size", String.valueOf(hdctArrayList.size()));
        if (getCartList().size() != 0){
            cartAdapter = new CartAdapter(orderArrayList,ThanhToanActivity.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThanhToanActivity.this,LinearLayoutManager.VERTICAL,false);
            rcvcart.setLayoutManager(linearLayoutManager);
            rcvcart.setAdapter(cartAdapter);
            for (Order order: getCartList()){
                tongtien = tongtien+order.getSoluongmua() * order.getFood().getGia();
            }

            txttientong.setText(decimalFormat.format(tongtien)+" VNĐ");
            btnthanhtoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    daoHDCT = new DaoHoaDon(ThanhToanActivity.this);
                    for (HoaDon hdct: hdctArrayList){

                        daoHDCT.insert(hdct);
                        sendNotifiaction(namestore,nameuser,"Xác nhận đơn hàng",user.getUid(),hdct.getIdhct());
                    }
                    localstorage.deleteCart();
                    rcvcart.setVisibility(View.GONE);
                    linearbackground.setBackgroundResource( R.drawable.catwithshoppingcart );
                    scrollView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }
            });
        }else {

            rcvcart.setVisibility(View.GONE);
            linearbackground.setBackgroundResource( R.drawable.catwithshoppingcart );
            scrollView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);

        }

        daoUser = new DaoUser( ThanhToanActivity.this);
        daoUser.getAll(new Usercallback() {
            @Override
            public void onSuccess(ArrayList<User> lists) {
                for (int i = 0; i<lists.size();i++){
                    if (lists.get(i).getToken().matches(user.getUid())){
                        txtaddress.setText(lists.get(i).getDiachi());
                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ThanhToanActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                finish();
            }
        });
    }
    public ArrayList<Order> getCartList() {
        if (localstorage.getCart() != null) {
            String jsonCart = localstorage.getCart();
            Log.d("CART : ", jsonCart);
            Type type = new TypeToken<List<Order>>() {
            }.getType();
            orderArrayList = gson.fromJson(jsonCart, type);

            return orderArrayList;
        }
        return orderArrayList;
    }
    private void sendNotifiaction(String receiver, final String username, final String message, final String tokensStore, String listhdct){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Toast.makeText(ThanhToanActivity.this, "Đã gửi"+receiver, Toast.LENGTH_SHORT).show();
                    DataHoaDon data = new DataHoaDon(user.getUid(), R.mipmap.ic_launcher_round, username+": "+message, "Đơn Đặt Hàng",
                            tokensStore,listhdct);
                    SenderHoaDon sender = new SenderHoaDon(data,token.getToken());
                    try {
                        JSONObject senderJsonObj = new JSONObject(new Gson().toJson(sender));
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", senderJsonObj,
                                new com.android.volley.Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //response of the request
                                        Log.d("JSON_RESPONSE", "onResponse: " + response.toString());

                                    }
                                }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("JSON_RESPONSE", "onResponse: " + error.toString());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                //put params
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Content-Type", "application/json");
                                headers.put("Authorization", "key=AAAAxb_KheE:APA91bEXt6uisGWw6BEkNNV0OQ_bYydpwHjkZvXsE2CDauZU6DfiSlqjN0lL8dA1iwKk63iEybaxfS0pJ8atgUee49JfeDRSYzcwTQ15P6OHunaV7ZlKS9YB-4TfJtlp3UgjR6bsaLgI");


                                return headers;
                            }
                        };

                        //add this request to queue
                        requestQueue.add(jsonObjectRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}