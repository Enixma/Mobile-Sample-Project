package com.enixma.sample.mobile.presentation.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.databinding.LayoutDetailListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */
public class DetailImageListAdapter extends RecyclerView.Adapter<DetailImageListAdapter.ViewHolder> {

    private List<DetailImageItem> items = new ArrayList<>();
    private LayoutDetailListItemBinding binding;

    public DetailImageListAdapter(@NonNull List<DetailImageItem> mobileListItems) {
        items.addAll(mobileListItems);
    }

    @Override
    public DetailImageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_detail_list_item, parent, false);
        return new DetailImageListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DetailImageListAdapter.ViewHolder holder, int position) {
        DetailImageItem item = items.get(position);
        holder.bind(item);
        setItemThumbnail(holder, position);
    }

    private void setItemThumbnail(DetailImageListAdapter.ViewHolder holder, int position) {
        Glide.with(holder.binding.imageView.getContext())
                .load(items.get(position).getImageURL())
                .fitCenter()
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image)
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutDetailListItemBinding binding;

        public ViewHolder(LayoutDetailListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final @NonNull DetailImageItem item) {
            binding.executePendingBindings();
        }
    }
}