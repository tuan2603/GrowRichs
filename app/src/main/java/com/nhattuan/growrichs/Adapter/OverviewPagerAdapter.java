package com.nhattuan.growrichs.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nhattuan.growrichs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OverviewPagerAdapter extends PagerAdapter {

    private Activity context;
    private LayoutInflater layoutInflater;
    private List<String> listImage;
    Click click;

    public OverviewPagerAdapter(Activity context, List<String> listImage, Click click) {
        this.context = context;
        this.listImage = listImage;
        this.click = click;
    }

    public interface Click {
        void Click(int position);
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_over_view, null);

        final ImageView imageSlide = view.findViewById(R.id.image_slide_intro);

        Picasso.with(context)
                .load(listImage.get(position))
                .fit()
                .centerInside()
                .into(imageSlide);


        imageSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.Click(position);
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}