package com.enixma.sample.mobile.presentation.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.enixma.sample.mobile.presentation.alldevice.MobileListFragment;
import com.enixma.sample.mobile.presentation.favorite.FavoriteListFragment;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobilePagerAdapter extends FragmentPagerAdapter {

    private MobileListFragment mobileListFragment;
    private FavoriteListFragment favoriteListFragment;

    public MobilePagerAdapter(FragmentManager fragmentManager, MobileListFragment mobileListFragment, FavoriteListFragment favoriteListFragment) {
        super(fragmentManager);
        this.mobileListFragment = mobileListFragment;
        this.favoriteListFragment = favoriteListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mobileListFragment;
        } else {
            return favoriteListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}