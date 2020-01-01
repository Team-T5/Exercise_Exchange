package com.example.exerciseexchange;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> fotoURLs;

    public ViewPagerAdapter(Context context, List<String> fotoURLs) {
        this.context = context;
        this.fotoURLs = fotoURLs;
    }

    @Override
    public int getCount() {
        return fotoURLs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView image = new ImageView(context);
        Picasso.get().load(fotoURLs.get(position)).placeholder(R.drawable.loading).into(image);
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
