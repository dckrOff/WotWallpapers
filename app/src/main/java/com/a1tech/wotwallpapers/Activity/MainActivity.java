package com.a1tech.wotwallpapers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.a1tech.wotwallpapers.Adapter.CategoryAdapter;
import com.a1tech.wotwallpapers.Adapter.TanksAdapter;
import com.a1tech.wotwallpapers.Model.Country;
import com.a1tech.wotwallpapers.R;
import com.a1tech.wotwallpapers.Model.Tanks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private DatabaseReference myRef, dbCountries;
    private ArrayList<Tanks> tanks = new ArrayList<Tanks>();
    private ArrayList<Country> countries = new ArrayList<Country>();
    private ArrayList<Tanks> selectedCategory = new ArrayList<Tanks>();
    private TanksAdapter tanksAdapter;
    private CategoryAdapter categoryAdapter;

    private ColorStateList def;
    private TextView item1, item2, select;

    private int category = 1;

    private RecyclerView rvAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getAllWallapers();
        onClick();
    }

    private void getAllWallapers() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tanks.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                    Log.e(TAG, "1) " + childDataSnapshot.getKey()); //displays the key for the node
//                    Log.e(TAG, "2) " + childDataSnapshot.child("img").getValue());   //gives the value for given keyname
                    try {
                        tanks.add(new Tanks(childDataSnapshot.child("name").getValue().toString(), childDataSnapshot.child("img").getValue().toString(), childDataSnapshot.child("img-phone").getValue().toString(), childDataSnapshot.child("country").getValue().toString(), childDataSnapshot.getKey()));
                    } catch (Exception e) {
                        Log.e(TAG, "exception in catch()-> " + e);
                    }
                }
                setAdapter();
                Log.e(TAG, "list size=> " + tanks.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void getCategory() {
        dbCountries.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countries.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                    Log.e(TAG, "1) " + childDataSnapshot.getKey()); //displays the key for the node
//                    Log.e(TAG, "2) " + childDataSnapshot.child("img").getValue());   //gives the value for given keyname
                    try {
                        countries.add(new Country(childDataSnapshot.child("name").getValue().toString(), childDataSnapshot.child("img").getValue().toString(), childDataSnapshot.child("country_code").getValue().toString()));
                    } catch (Exception e) {
                    }
                }
                setAdapter();
                Log.e(TAG, "list size=> " + countries.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setAdapter() {
        if (category == 1) {
            rvAll.setLayoutManager(new GridLayoutManager(this, 1));
            tanksAdapter = new TanksAdapter(this, tanks);
            rvAll.setAdapter(tanksAdapter); // set the Adapter to RecyclerView
//            recyclerView.setNestedScrollingEnabled(false);
        } else if (category == 2) {
            rvAll.setLayoutManager(new GridLayoutManager(this, 2));
            categoryAdapter = new CategoryAdapter(this, countries);
            rvAll.setAdapter(categoryAdapter);
        }
    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("wallpapers");
        dbCountries = database.getReference("country");

        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);

        select = findViewById(R.id.select);
        def = item2.getTextColors();

        rvAll = findViewById(R.id.rv_all);
//        rvCategory = findViewById(R.id.rv_category);
    }

    private void onClick() {
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTabs(view);
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTabs(view);
            }
        });
    }

    private void changeTabs(View view) {
        if (view.getId() == R.id.item1) {
            category = 1;

            select.animate().x(0).setDuration(100);
            item1.setTextColor(Color.WHITE);
            item2.setTextColor(def);

            getAllWallapers();
        } else if (view.getId() == R.id.item2) {
            category = 2;

            item1.setTextColor(def);
            item2.setTextColor(Color.WHITE);
            int size = item2.getWidth();
            select.animate().x(size).setDuration(100);
            getCategory();
        }
    }
}