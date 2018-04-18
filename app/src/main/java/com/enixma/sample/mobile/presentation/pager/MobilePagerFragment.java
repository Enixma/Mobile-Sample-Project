package com.enixma.sample.mobile.presentation.pager;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.data.di.AppSharedPreferencesModule;
import com.enixma.sample.mobile.data.repository.datasource.sharedprefs.AppSharedPreferences;
import com.enixma.sample.mobile.databinding.LayoutMobilePagerFragmentBinding;
import com.enixma.sample.mobile.presentation.alldevice.MobileListFragment;
import com.enixma.sample.mobile.presentation.favorite.FavoriteListFragment;
import com.enixma.sample.mobile.presentation.pager.di.DaggerMobilePagerComponent;

import javax.inject.Inject;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobilePagerFragment extends Fragment {

    private LayoutMobilePagerFragmentBinding binding;
    private AlertDialog alertDialog;
    private CharSequence[] values = {"Price low to high", " Price high to low", "Rating 5 - 1"};
    private MobileListFragment mobileListFragment;
    private FavoriteListFragment favoriteListFragment;

    @Inject
    AppSharedPreferences appSharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerMobilePagerComponent.builder()
                .appSharedPreferencesModule(new AppSharedPreferencesModule(getContext()))
                .build().inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_mobile_pager_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPager();
    }

    private void initPager() {
        mobileListFragment = MobileListFragment.newInstance(getSortByCriteria());
        favoriteListFragment = FavoriteListFragment.newInstance(getSortByCriteria());
        binding.viewPager.setAdapter(new MobilePagerAdapter(getFragmentManager(), mobileListFragment, favoriteListFragment));
        binding.tablayout.setupWithViewPager(binding.viewPager);
        binding.tablayout.getTabAt(0).setText("Mobile");
        binding.tablayout.getTabAt(1).setText("Favorite");
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void displaySortOptions() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(values, getSelectedIndex(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        saveSortByCriteria(getString(R.string.SORTBY_PRICE_LOW_TO_HIGH));
                        mobileListFragment.updateSortCriteria(getString(R.string.SORTBY_PRICE_LOW_TO_HIGH));
                        favoriteListFragment.updateSortCriteria(getString(R.string.SORTBY_PRICE_LOW_TO_HIGH));
                        break;
                    case 1:
                        saveSortByCriteria(getString(R.string.SORTBY_PRICE_HIGH_TO_LOW));
                        mobileListFragment.updateSortCriteria(getString(R.string.SORTBY_PRICE_HIGH_TO_LOW));
                        favoriteListFragment.updateSortCriteria(getString(R.string.SORTBY_PRICE_HIGH_TO_LOW));
                        break;
                    case 2:
                        saveSortByCriteria(getString(R.string.SORTBY_RATING_FIVE_TO_ONE));
                        mobileListFragment.updateSortCriteria(getString(R.string.SORTBY_RATING_FIVE_TO_ONE));
                        favoriteListFragment.updateSortCriteria(getString(R.string.SORTBY_RATING_FIVE_TO_ONE));
                        break;
                }
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }

    private int getSelectedIndex(){
        int selectedIndex = 0;
        if(getSortByCriteria().equals(getString(R.string.SORTBY_PRICE_HIGH_TO_LOW))){
            selectedIndex = 1;
        } else if(getSortByCriteria().equals(getString(R.string.SORTBY_RATING_FIVE_TO_ONE))){
            selectedIndex =2;
        }
        return selectedIndex;
    }

    private String getSortByCriteria(){
        return appSharedPreferences.getString(getString(R.string.SORTBY_KEY), getString(R.string.SORTBY_PRICE_LOW_TO_HIGH));
    }

    private void saveSortByCriteria(String sortBy){
        appSharedPreferences.putString(getString(R.string.SORTBY_KEY), sortBy);
    }
}
