package com.enixma.sample.mobile.presentation.alldevice;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.enixma.sample.mobile.presentation.alldevice.mapper.EntityToMobileListItemMapper;
import com.enixma.sample.mobile.presentation.detail.DetailActivity;
import com.enixma.sample.mobile.presentation.detail.mapper.ListItemToModelMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileListFragment extends Fragment implements MobileListContract.View {

    private static final String SORT_BY = "sortBy";
    private LayoutMobileListFragmentBinding binding;
    private MobileListViewModel viewModel;
    private MobileListAdapter adapter;
    private ArrayList<MobileListItem> items;
    private String sortBy;
    private Parcelable recyclerViewState;
    private boolean canRestore;

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

        viewModel = ViewModelProviders.of(this).get(MobileListViewModel.class);
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
        binding.setModel(viewModel);
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
        presenter.getMobileList();
    }

    @Override
    public void populateList(List<MobileEntity> mobileEntityList) {

        saveRecyclerViewState();

        viewModel.getHasData().set(true);

        if (items == null) {
            items = new ArrayList<>();
        }
        items.clear();
        items.addAll(getMobileListItems(mobileEntityList));

        adapter = new MobileListAdapter(items, new MobileListAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position) {
                goToDetailPage(position);
            }

            @Override
            public void onMarkFavorite(int position) {
                items.get(position).setFavorite(!items.get(position).isFavorite());
                adapter.notifyItemChanged(position);
                presenter.setFavorite(position);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.listView.setLayoutManager(layoutManager);
        binding.listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        restoreRecyclerViewState();
    }

    private void saveRecyclerViewState(){
        if(adapter != null){
            recyclerViewState = binding.listView.getLayoutManager().onSaveInstanceState();
            canRestore = true;
        }
    }
    private void restoreRecyclerViewState(){
        if(recyclerViewState != null && canRestore) {
            canRestore = false;
            binding.listView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    private void goToDetailPage(int position) {
        Intent intent = DetailActivity.getIntent(getActivity());
        intent.putExtra(DetailActivity.MOBILE, ListItemToModelMapper.map(items.get(position)));
        startActivity(intent);
    }

    private ArrayList<MobileListItem> getMobileListItems(List<MobileEntity> mobileEntityList) {

        ArrayList<MobileListItem> listItem = new ArrayList();
        for (MobileEntity mobileEntity : mobileEntityList) {
            listItem.add(EntityToMobileListItemMapper.map(getContext(), mobileEntity));
        }
        return listItem;
    }

    @Override
    public void displayNoData() {
        viewModel.getHasData().set(false);
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
