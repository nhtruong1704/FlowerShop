package com.example.flower;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.flower.firebase.DaoUser;
import com.example.flower.fragment.FragmentCart;
import com.example.flower.fragment.FragmentEditProfile;
import com.example.flower.fragment.FragmentFavorite;
import com.example.flower.fragment.FragmentHome;
import com.example.flower.fragment.FragmentProfile;
import com.example.flower.local.Localstorage;
import com.example.flower.model.Order;
import com.example.flower.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.flower.MapActivity.REQUEST_LOCATION_CODE;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
   public static MeowBottomNavigation bottommeo;
    TextView titletoolbar;
    public static String emailuser = "";
    public static int cart_count =0;
    Localstorage localstorage;
    Gson gson;
    ArrayList<Order> orderArrayList;
    DaoUser daoUser;
    ArrayList<User> userArrayList;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottommeo = (MeowBottomNavigation) findViewById(R.id.bottommeo);
        toolbar = findViewById(R.id.toolbar);
        titletoolbar = findViewById(R.id.toolbar_title);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.Xanhbaemin));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titletoolbar.setText("Best seller");
        titletoolbar.setTextSize(30);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if (user.getDiachi()==null || user.getImage()==null || user.getName()==null || user.getPhone()==null){
                        toolbar.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentEditProfile()).commit();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        gson = new Gson();
        orderArrayList = new ArrayList<>();
        localstorage = new Localstorage(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon( R.drawable.ic_favorite_border );
        bottommeo.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_local_florist_24 ));
        bottommeo.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_shopping_basket_24 ));
        bottommeo.add(new MeowBottomNavigation.Model(3, R.drawable.ic_heart_liked));
        bottommeo.add(new MeowBottomNavigation.Model(4, R.drawable.ic_menu_black ));
        Intent intent = getIntent();
//        cart_count = cartCount();
//        Toast.makeText(this, "size"+cartCount(), Toast.LENGTH_SHORT).show();
//        cart_count++;
//        invalidateOptionsMenu();
        emailuser = intent.getStringExtra("email");
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentHome()).commit();

        }

        bottommeo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

//                Toast.makeText(MainActivity.this, "Click"+item.getId(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(MainActivity.this, "Click"+item.getIcon(), Toast.LENGTH_SHORT).show();

            }
        });
        bottommeo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        toolbar.setVisibility(View.VISIBLE);
                        titletoolbar.setText("Best seller");
                        fragment = new FragmentHome();
                        break;
                    case 2:
                        toolbar.setVisibility(View.VISIBLE);
                        titletoolbar.setText("Cart");
                        fragment = new FragmentCart();
                        break;
                    case 3:
                        toolbar.setVisibility(View.VISIBLE);
                        titletoolbar.setText("Favourite");
                        fragment = new FragmentFavorite();
                        break;
                    case 4:
                        toolbar.setVisibility(View.GONE);
                        fragment = new FragmentProfile();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, fragment).commit();
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbars, menu);
        MenuItem menuItem = menu.findItem(R.id.cart);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.ic_baseline_shopping_basket_24 ));

//        }
        return true;
    }
//    public int cartCount() {
//
//        gson = new Gson();
//        if (localstorage.getCart() != null) {
//            String jsonCart = localstorage.getCart();
//            Log.d("CART : ", jsonCart);
//            Type type = new TypeToken<List<HDCT>>() {
//            }.getType();
//            orderArrayList = gson.fromJson(jsonCart, type);
//
//            //Toast.makeText(getContext(),remedyList.size()+"",Toast.LENGTH_LONG).show();
//            return orderArrayList.size();
//        }
//        return 0;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) {
            startActivity(new Intent(MainActivity.this, ThanhToanActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
    }

    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }
}