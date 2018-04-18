package com.enixma.sample.mobile.presentation.alldevice;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.databinding.LayoutMobileListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobileListAdapter extends RecyclerView.Adapter<MobileListAdapter.ViewHolder> {

    private List<MobileListItem> items = new ArrayList<>();
    private LayoutMobileListItemBinding binding;
    private OnItemListener onItemListener;

    public interface OnItemListener {
        void onItemClick(int pos);
        void onMarkFavorite(int pos);
    }

    public MobileListAdapter(@NonNull List<MobileListItem> mobileListItems, OnItemListener listener) {
        items.addAll(mobileListItems);
        this.onItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_mobile_list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MobileListItem item = items.get(position);
        holder.bind(item, onItemListener);
        markItemFavorite(holder, item);
        setItemThumbnail(holder, position);
        setViewListener(holder, item, position);

    }

    private void markItemFavorite(ViewHolder holder, MobileListItem item){
        if(item.isFavorite()){
            holder.binding.imageFavorite.setImageDrawable(ContextCompat.getDrawable(holder.binding.buttonFavorite.getContext(), R.drawable.ic_mark_favorite));

        } else{
            holder.binding.imageFavorite.setImageDrawable(ContextCompat.getDrawable(holder.binding.buttonFavorite.getContext(),R.drawable.ic_unmark_favorite));
        }
    }

    private void setItemThumbnail(ViewHolder holder, int position){
        Glide.with(holder.binding.imageView.getContext())
                .load(items.get(position).getThumbImageURL())
                .fitCenter()
                .into(holder.binding.imageView);
    }

    private void setViewListener(final ViewHolder holder, final MobileListItem item, final int position){
        holder.binding.buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.onMarkFavorite(position);
            }
        });

        holder.binding.buttonGoToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutMobileListItemBinding binding;
        public ViewHolder(LayoutMobileListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final @NonNull MobileListItem item, final OnItemListener onItemListener) {
            binding.setModel(item);
            binding.executePendingBindings();
        }
    }
}
