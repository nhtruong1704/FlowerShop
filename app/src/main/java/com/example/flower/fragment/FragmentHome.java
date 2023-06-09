package com.example.flower.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.R;
import com.example.flower.SearchActivity;
import com.example.flower.adapter.CategoryAdapter;
import com.example.flower.adapter.FoodAdapter;
import com.example.flower.Slide.SlideViewPayer;
import com.example.flower.Slide.Item;
import com.example.flower.interfaceI.Categoriescallback;
import com.example.flower.interfaceI.Foodcallback;
import com.example.flower.firebase.DaoCategories;
import com.example.flower.firebase.DaoFood;
import com.example.flower.model.Categories;
import com.example.flower.model.Food;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment {
    private SlideViewPayer adapter;
    SliderView sliderView;
    EditText edtsearch;
    RecyclerView rcvhome,rcvmonan;
    ArrayList<Categories> datacategories;
    DaoCategories daoCategories;
    TextView txtslogan;
    CategoryAdapter categoryAdapter;
    FoodAdapter foodAdapter;
    ArrayList<Food> foodArrayList;
    DaoFood daoFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmenthome,container,false);
        sliderView = view.findViewById(R.id.imageSlider);
        rcvhome = view.findViewById(R.id.rcvhome);
        rcvmonan = view.findViewById(R.id.rcvmonan);
        txtslogan = view.findViewById(R.id.txtslogan);
        daoCategories = new DaoCategories(getActivity());
        datacategories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(datacategories,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rcvhome.setLayoutManager(linearLayoutManager);
        rcvhome.setHasFixedSize(true);
        rcvhome.setAdapter(categoryAdapter);
        Date currentTime = Calendar.getInstance().getTime();
        if (currentTime.getTime() < 10) {
            txtslogan.setText("Khởi động ngày mới với món ngon nào !");
            foodArrayList = new ArrayList<>();
            foodAdapter = new FoodAdapter(foodArrayList,getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            rcvmonan.setLayoutManager(gridLayoutManager);
            rcvmonan.setHasFixedSize(true);
            rcvmonan.setAdapter(foodAdapter);
            daoFood = new DaoFood(getActivity());
            daoFood.getAll(new Foodcallback() {
                @Override
                public void onSuccess(ArrayList<Food> lists) {
                    foodArrayList.clear();
                    for (int i =0;i<lists.size();i++){
                        if (lists.get(i).getStatus().equalsIgnoreCase("Sáng")){
                            foodArrayList.add(lists.get(i));
                            foodAdapter.notifyDataSetChanged();
                        }
                    }

                }
                @Override
                public void onError(String message) {

                }
            });
        } else if (currentTime.getTime() >= 10 && currentTime.getTime() <= 15 ){
            txtslogan.setText("Nạp năng lượng cho buổi trưa nhé !");
            foodArrayList = new ArrayList<>();
            foodAdapter = new FoodAdapter(foodArrayList,getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            rcvmonan.setLayoutManager(gridLayoutManager);
            rcvmonan.setHasFixedSize(true);
            rcvmonan.setAdapter(foodAdapter);
            daoFood = new DaoFood(getActivity());
            daoFood.getAll(new Foodcallback() {
                @Override
                public void onSuccess(ArrayList<Food> lists) {
                    foodArrayList.clear();
                    for (int i =0;i<lists.size();i++){
                        if (lists.get(i).getStatus().equalsIgnoreCase("Trưa")){
                            foodArrayList.add(lists.get(i));
                            foodAdapter.notifyDataSetChanged();
                        }
                    }

                }
                @Override
                public void onError(String message) {

                }
            });
        }else if (currentTime.getTime() > 15){
            txtslogan.setText("Buổi chiều cùng những món ngon nè !");
            foodArrayList = new ArrayList<>();
            foodAdapter = new FoodAdapter(foodArrayList,getActivity());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            rcvmonan.setLayoutManager(gridLayoutManager);
            rcvmonan.setHasFixedSize(true);
            rcvmonan.setAdapter(foodAdapter);
            daoFood = new DaoFood(getActivity());
            daoFood.getAll(new Foodcallback() {
                @Override
                public void onSuccess(ArrayList<Food> lists) {
                    foodArrayList.clear();
                    for (int i =0;i<lists.size();i++){
                        if (lists.get(i).getStatus().equalsIgnoreCase("Chiều")){
                            foodArrayList.add(lists.get(i));
                            foodAdapter.notifyDataSetChanged();
                        }
                    }

                }
                @Override
                public void onError(String message) {

                }
            });
        }
        daoCategories.getAll(new Categoriescallback() {
            @Override
            public void onSuccess(ArrayList<Categories> lists) {
                datacategories.clear();
                datacategories.addAll(lists);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });
        edtsearch  = (EditText)view.findViewById(R.id.edtsearch);
        edtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });

        adapter = new SlideViewPayer(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        renewItems(view);
        removeLastItem(view);
        addNewItem(view);
        return view;
    }
    public void renewItems(View view) {
        List<Item> sliderItemList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Item sliderItem = new Item();
            if (i % 2 == 0) {
                sliderItem.setImageurl("https://images.squarespace-cdn.com/content/v1/53883795e4b016c956b8d243/1597821998048-538UNQI253SYL3KE9NGD/chup-anh-mon-an-breakfast-10.jpg");
            } else {
                sliderItem.setImageurl("https://toplist.vn/images/800px/-790915.jpg");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.ViewPagerAdapter(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteitem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        Item sliderItem = new Item();
//        sliderItem.setDescription("Re");
        sliderItem.setImageurl("https://eventusproduction.com/wp-content/uploads/2017/02/fresh-box.jpg");
        adapter.addItem(sliderItem);
    }

    @Override
    public void onResume() {
        super.onResume();
//        foodAdapter.notifyDataSetChanged();
//        categoryAdapter.notifyDataSetChanged();
    }
}
