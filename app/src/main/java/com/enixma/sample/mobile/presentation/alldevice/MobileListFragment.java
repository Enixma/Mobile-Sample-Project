package com.enixma.sample.mobile.presentation.alldevice;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.data.di.MobileDataModule;
import com.enixma.sample.mobile.data.di.ServiceFactoryModule;
import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.databinding.LayoutMobileListFragmentBinding;
import com.enixma.sample.mobile.presentation.alldevice.di.DaggerMobileListComponent;
import com.enixma.sample.mobile.presentation.alldevice.di.MobileListModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileListFragment extends Fragment implements MobileListContract.View {

    private static final String SORT_BY = "sortBy";
    private LayoutMobileListFragmentBinding binding;
    private MobileListAdapter adapter;
    private ArrayList<MobileListItem> items;
    private String sortBy;

    @Inject
    MobileListContract.Action presenter;

    public static MobileListFragment newInstance(String sortBy) {
        MobileListFragment fragment = new MobileListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SORT_BY, sortBy);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sortBy = getArguments().getString(SORT_BY);
        DaggerMobileListComponent.builder()
                .serviceFactoryModule(new ServiceFactoryModule(getContext()))
                .mobileDataModule(new MobileDataModule(getContext()))
                .mobileListModule(new MobileListModule(this))
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_mobile_list_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLifecycle().addObserver((MobileListPresenter) presenter);
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.downloadMobileList();
            }
        });
        updateSortCriteria(sortBy);
    }

    @Override
    public void populateList(List<MobileEntity> mobileEntityList) {

        if (items == null) {
            items = new ArrayList<>();
        }
        items.clear();
        items.addAll(getMobileListItems(mobileEntityList));

        adapter = new MobileListAdapter(items, new MobileListAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position) {
                goToDetailPage();
            }

            @Override
            public void onMarkFavorite(int position) {
                items.get(position).setFavorite(false);
                adapter.notifyDataSetChanged();
                presenter.setFavorite(position);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.listView.setLayoutManager(layoutManager);
        binding.listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void goToDetailPage() {
        // go to detail page
    }

    private ArrayList<MobileListItem> getMobileListItems(List<MobileEntity> mobileEntityList) {

        ArrayList<MobileListItem> listItem = new ArrayList();
        for (MobileEntity mobileEntity : mobileEntityList) {
            MobileListItem mobileListItem = new MobileListItem();
            mobileListItem.setId(mobileEntity.getId());
            mobileListItem.setBrand(mobileEntity.getBrand());
            mobileListItem.setName(mobileEntity.getName());
            mobileListItem.setDescription(mobileEntity.getDescription());
            mobileListItem.setThumbImageURL(mobileEntity.getThumbImageURL());
            mobileListItem.setPrice("Price: $" + mobileEntity.getPrice());
            mobileListItem.setRating("Rating:" + mobileEntity.getRating());
            mobileListItem.setFavorite(mobileEntity.isFavorite());
            listItem.add(mobileListItem);
        }

        return listItem;
    }

    @Override
    public void displayNoData() {
        // display no data found
    }

    @Override
    public void stopRefreshLoading() {
        binding.swipe.setRefreshing(false);
    }

    public void updateSortCriteria(String sortBy) {
        if (sortBy.equals(getString(R.string.SORTBY_PRICE_LOW_TO_HIGH))) {
            presenter.sortPriceLowToHigh();
        } else if (sortBy.equals(getString(R.string.SORTBY_PRICE_HIGH_TO_LOW))) {
            presenter.sortPriceHighToLow();
        } else {
            presenter.sortRatingFiveToOne();
        }

    }

}
