package com.a1tech.wotwallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private DatabaseReference myRef;
    private ArrayList<Tanks> tanks = new ArrayList<Tanks>();

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("wallpapers");
    }
}