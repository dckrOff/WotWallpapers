package com.a1tech.wotwallpapers.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.a1tech.wotwallpapers.Model.Country;
import com.a1tech.wotwallpapers.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Country> country;

    public CategoryAdapter(Context context, ArrayList<Country> country) {
        this.inflater = LayoutInflater.from(context);
        this.country = country;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Country country = this.country.get(position);

        Glide.with(inflater.getContext()).load(country.getCountryImage()).into(holder.countryImage);

        holder.countryName.setText(country.getCountryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "category-> " + country.getCountryName(), Toast.LENGTH_SHORT).show();
//                //passing data through intent on below line.
//                Intent i = new Intent(inflater.getContext(), WallpaperActivity.class);
//                i.putExtra("imgUrl", tanks.getImgPhone());
//                inflater.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return country.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        ImageView countryImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.tv_country);
            countryImage = itemView.findViewById(R.id.iv_country);
        }
    }
}
