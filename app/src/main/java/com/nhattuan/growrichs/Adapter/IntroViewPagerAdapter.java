package com.nhattuan.growrichs.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.nhattuan.growrichs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    private Activity context;
    private LayoutInflater layoutInflater;
    private List<String> list;
    PhotoView imageSlide;
    public IntroViewPagerAdapter(Activity context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = layoutInflater.inflate(R.layout.slide_intro_view,null);

        imageSlide = view.findViewById(R.id.image_slide_intro);

        Picasso.with(context)
                .load(list.get(position).toString())
                .fit()
                .centerInside()
                .into(imageSlide);
        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return  view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
