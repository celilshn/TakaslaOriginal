package com.cengcelil.takaslaoriginal.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cengcelil.takaslaoriginal.Manager.Profile.ActiveProductFragment;
import com.cengcelil.takaslaoriginal.Manager.Profile.LikedFragment;
import com.cengcelil.takaslaoriginal.Manager.Profile.SoldFragment;
import com.cengcelil.takaslaoriginal.Manager.Profile.TimeoutProductFragment;
import com.cengcelil.takaslaoriginal.R;

public class MyProductsViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MyProductsViewPagerAdap";
    private Context context;
    public MyProductsViewPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new ActiveProductFragment();
        }
        else if (position == 1)
        {
            fragment = new TimeoutProductFragment();
        }
        else if (position == 2)
        {
            fragment = new SoldFragment();
        }
        else if (position == 3)
        {
            fragment = new LikedFragment();
        }
        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = context.getString(R.string.active_products);
        }
        else if (position == 1)
        {
            title = context.getString(R.string.timout_products);
        }
        else if (position == 2)
        {
            title = context.getString(R.string.sold_products);
        }        else if (position == 3)
        {
            title = context.getString(R.string.liked_products);
        }
        return title;
    }
}
