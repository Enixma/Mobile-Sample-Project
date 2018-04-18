package com.enixma.sample.mobile.presentation.favorite;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.data.di.MobileDataModule;
import com.enixma.sample.mobile.data.di.ServiceFactoryModule;
import com.enixma.sample.mobile.data.entity.MobileEntity;
import com.enixma.sample.mobile.databinding.LayoutFavoriteListFragmentBinding;
import com.enixma.sample.mobile.presentation.detail.DetailActivity;
import com.enixma.sample.mobile.presentation.detail.mapper.ListItemToModelMapper;
import com.enixma.sample.mobile.presentation.favorite.di.DaggerFavoriteListComponent;
import com.enixma.sample.mobile.presentation.favorite.di.FavoriteListModule;
import com.enixma.sample.mobile.presentation.favorite.mapper.EntityToFavoriteListItemMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class FavoriteListFragment extends Fragment implements FavoriteListContract.View {

    private static final String SORT_BY = "sortBy";
    private LayoutFavoriteListFragmentBinding binding;
    private FavoriteListViewModel viewModel;
    private FavoriteListAdapter adapter;
    private ArrayList<FavoriteListItem> items;
    private String sortBy;

    @Inject
    FavoriteListContract.Action presenter;

    public static FavoriteListFragment newInstance(String sortBy) {
        FavoriteListFragment fragment = new FavoriteListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SORT_BY, sortBy);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FavoriteListViewModel.class);
        this.sortBy = getArguments().getString(SORT_BY);
        DaggerFavoriteListComponent.builder()
                .serviceFactoryModule(new ServiceFactoryModule(getContext()))
                .mobileDataModule(new MobileDataModule(getContext()))
                .favoriteListModule(new FavoriteListModule(this))
                .build().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_favorite_list_fragment, container, false);
        binding.setModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLifecycle().addObserver((FavoriteListPresenter) presenter);
        updateSortCriteria(sortBy);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            presenter.getFavoriteList();
        }
    }

    @Override
    public void populateList(List<MobileEntity> mobileEntityList) {

        viewModel.getHasData().set(true);

        if (items == null) {
            items = new ArrayList<>();
        }
        items.clear();
        items.addAll(getMobileListItems(mobileEntityList));

        adapter = new FavoriteListAdapter(items, new FavoriteListAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position) {
                goToDetailPage(position);
            }

        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.listView.setLayoutManager(layoutManager);
        binding.listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void goToDetailPage(int position) {
        Intent intent = DetailActivity.getIntent(getActivity());
        intent.putExtra(DetailActivity.MOBILE, ListItemToModelMapper.map(items.get(position)));
        startActivity(intent);
    }

    private ArrayList<FavoriteListItem> getMobileListItems(List<MobileEntity> mobileEntityList) {

        ArrayList<FavoriteListItem> listItem = new ArrayList();
        for (MobileEntity mobileEntity : mobileEntityList) {
            listItem.add(EntityToFavoriteListItemMapper.map(getContext(), mobileEntity));
        }

        return listItem;
    }

    @Override
    public void displayNoData() {
        viewModel.getHasData().set(false);
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
