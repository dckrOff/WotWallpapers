package com.a1tech.wotwallpapers.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.a1tech.wotwallpapers.Activity.WallpaperActivity;
import com.a1tech.wotwallpapers.Model.Tanks;
import com.a1tech.wotwallpapers.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TanksAdapter extends RecyclerView.Adapter<TanksAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Tanks> tanks;

    public TanksAdapter(Context context, ArrayList<Tanks> tanks) {
        this.inflater = LayoutInflater.from(context);
        this.tanks = tanks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_tank, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Tanks tanks = this.tanks.get(position);

        Glide.with(inflater.getContext()).load(tanks.getImg()).into(holder.tankImage);

        holder.tankName.setText(tanks.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //passing data through intent on below line.
                Intent i = new Intent(inflater.getContext(), WallpaperActivity.class);
                i.putExtra("imgUrl", tanks.getImgPhone());
                inflater.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tanks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tankName;
        ImageView tankImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            tankName = itemView.findViewById(R.id.tvName);
            tankImage = itemView.findViewById(R.id.ivTank);

        }
    }
}
