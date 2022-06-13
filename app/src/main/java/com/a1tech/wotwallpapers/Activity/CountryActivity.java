package com.a1tech.wotwallpapers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.a1tech.wotwallpapers.Adapter.TanksAdapter;
import com.a1tech.wotwallpapers.Model.Tanks;
import com.a1tech.wotwallpapers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CountryActivity extends AppCompatActivity {

    private final String TAG = "CountryActivity";
    private DatabaseReference myRef;
    private ArrayList<Tanks> tanks = new ArrayList<Tanks>();
    private TanksAdapter tanksAdapter;
    private RecyclerView rvCategory;

    String countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        init();
        getWallpapers();
    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("wallpapers");

        rvCategory = findViewById(R.id.rv_category);

        //initializing all variables on below line.
        countryCode = getIntent().getStringExtra("country");
    }

    private void getWallpapers() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tanks.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                    Log.e(TAG, "1) " + childDataSnapshot.getKey()); //displays the key for the node
//                    Log.e(TAG, "2) " + childDataSnapshot.child("img").getValue());   //gives the value for given keyname
                    try {
                        if (childDataSnapshot.child("country").getValue().toString().equals(countryCode))
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

    private void setAdapter() {
        rvCategory.setLayoutManager(new GridLayoutManager(this, 1));
        tanksAdapter = new TanksAdapter(this, tanks);
        rvCategory.setAdapter(tanksAdapter); // set the Adapter to RecyclerView
    }
}