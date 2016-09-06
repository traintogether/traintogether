package com.codepath.traintogether.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by ameyapandilwar on 8/18/16 at 5:00 AM.
 */
public class StylishTabLayout extends TabLayout {
    public StylishTabLayout(Context context) {
        super(context);
    }

    public StylishTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StylishTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setupWithViewPager(ViewPager viewPager) {
        super.setupWithViewPager(viewPager);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto_light.ttf");
        this.removeAllTabs();

        ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);

        PagerAdapter adapter = viewPager.getAdapter();

        if (adapter != null) {
            for (int i = 0, count = adapter.getCount(); i < count; i++) {
                Tab tab = this.newTab();
                this.addTab(tab.setText(adapter.getPageTitle(i)));
                AppCompatTextView view = (AppCompatTextView) ((ViewGroup) slidingTabStrip.getChildAt(i)).getChildAt(1);
                view.setTypeface(typeface);
            }
        }
    }
}