package com.enixma.sample.mobile.presentation.detail;

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
import com.enixma.sample.mobile.data.entity.MobileImageEntity;
import com.enixma.sample.mobile.databinding.LayoutDetailFragmentBinding;
import com.enixma.sample.mobile.presentation.detail.di.DaggerDetailComponent;
import com.enixma.sample.mobile.presentation.detail.di.DetailModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DetailFragment extends Fragment implements DetailContract.View {

    private static String MOBILE = "mobile";
    private LayoutDetailFragmentBinding binding;
    private DetailImageListAdapter adapter;
    private ArrayList<DetailImageItem> items;
    private DetailModel detailModel;

    @Inject
    DetailContract.Action presenter;

    public static DetailFragment newInstance(DetailModel detailModel) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOBILE, detailModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.detailModel = getArguments().getParcelable(MOBILE);
        DaggerDetailComponent.builder()
                .serviceFactoryModule(new ServiceFactoryModule(getContext()))
                .mobileDataModule(new MobileDataModule(getContext()))
                .detailModule(new DetailModule(this))
                .build().inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_detail_fragment, container, false);
        binding.setModel(detailModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLifecycle().addObserver((DetailPresenter) presenter);
        presenter.getMobileImages(detailModel.getId());
    }

    @Override
    public void populateList(List<MobileImageEntity> mobileImageEntities) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.clear();
        items.addAll(getDetailImageListItems(mobileImageEntities));

        adapter = new DetailImageListAdapter(items);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.listView.setLayoutManager(layoutManager);
        binding.listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private ArrayList<DetailImageItem> getDetailImageListItems(List<MobileImageEntity> mobileImageEntities) {
        ArrayList<DetailImageItem> imageItems = new ArrayList<>();
        for (MobileImageEntity mobileImageEntity : mobileImageEntities) {
            imageItems.add(new DetailImageItem(mobileImageEntity.getUrl()));
        }
        return imageItems;
    }
}
