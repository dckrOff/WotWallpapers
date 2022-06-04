package com.a1tech.wotwallpapers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
//                    Log.e(TAG, "1) " + childDataSnapshot.getKey()); //displays the key for the node
//                    Log.e(TAG, "2) " + childDataSnapshot.child("img").getValue());   //gives the value for given keyname
                    tanks.add(new Tanks(childDataSnapshot.child("name").getValue().toString(), childDataSnapshot.child("img").getValue().toString(), childDataSnapshot.getKey()));
                    setAdapter();
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        tanksAdapter = new TanksAdapter(this, tanks);
        recyclerView.setAdapter(tanksAdapter); // set the Adapter to RecyclerView
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("wallpapers");
    }
}