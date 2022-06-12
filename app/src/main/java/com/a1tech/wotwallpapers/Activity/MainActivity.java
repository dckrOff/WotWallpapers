package com.a1tech.wotwallpapers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.a1tech.wotwallpapers.Adapter.TanksAdapter;
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
    private DatabaseReference myRef;
    private ArrayList<Tanks> tanks = new ArrayList<Tanks>();
    private TanksAdapter tanksAdapter;

    private ColorStateList def;
    private TextView item1, item2, select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        connectToServer();
        onClick();
    }

    private void connectToServer() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tanks.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                    Log.e(TAG, "1) " + childDataSnapshot.getKey()); //displays the key for the node
//                    Log.e(TAG, "2) " + childDataSnapshot.child("img").getValue());   //gives the value for given keyname
                    try {
                        tanks.add(new Tanks(childDataSnapshot.child("name").getValue().toString(), childDataSnapshot.child("img").getValue().toString(), childDataSnapshot.child("img-phone").getValue().toString(), childDataSnapshot.child("country").getValue().toString(), childDataSnapshot.getKey()));
                        Log.e(TAG, "list size=> " + tanks.size());
                        setAdapter();
                    } catch (Exception e) {
                    }
                }
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
        RecyclerView recyclerView = findViewById(R.id.rvMain);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        tanksAdapter = new TanksAdapter(this, tanks);
        recyclerView.setAdapter(tanksAdapter); // set the Adapter to RecyclerView
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("wallpapers");

        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);

        select = findViewById(R.id.select);
        def = item2.getTextColors();
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
            select.animate().x(0).setDuration(100);
            item1.setTextColor(Color.WHITE);
            item2.setTextColor(def);
        } else if (view.getId() == R.id.item2) {
            item1.setTextColor(def);
            item2.setTextColor(Color.WHITE);
            int size = item2.getWidth();
            select.animate().x(size).setDuration(100);
        }
    }
}