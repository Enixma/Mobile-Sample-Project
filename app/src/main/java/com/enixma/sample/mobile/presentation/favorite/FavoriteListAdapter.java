package com.enixma.sample.mobile.presentation.favorite;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.enixma.sample.mobile.R;
import com.enixma.sample.mobile.databinding.LayoutFavoriteListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {

    private List<FavoriteListItem> items = new ArrayList<>();
    private LayoutFavoriteListItemBinding binding;
    private FavoriteListAdapter.OnItemListener onItemListener;

    public interface OnItemListener {
        void onItemClick(int pos);
    }

    public FavoriteListAdapter(@NonNull List<FavoriteListItem> mobileListItems,FavoriteListAdapter.OnItemListener listener) {
        items.addAll(mobileListItems);
        this.onItemListener = listener;
    }

    @Override
    public FavoriteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_favorite_list_item, parent, false);
        return new FavoriteListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FavoriteListAdapter.ViewHolder holder, int position) {
        FavoriteListItem item = items.get(position);
        holder.bind(item, onItemListener);
        setItemThumbnail(holder, position);
        setViewListener(holder, item, position);

    }

    private void setItemThumbnail(FavoriteListAdapter.ViewHolder holder, int position){
        Glide.with(holder.binding.imageView.getContext())
                .load(items.get(position).getThumbImageURL())
                .fitCenter()
                .into(holder.binding.imageView);
    }

    private void setViewListener(final FavoriteListAdapter.ViewHolder holder, final FavoriteListItem item, final int position){

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
        private LayoutFavoriteListItemBinding binding;
        public ViewHolder(LayoutFavoriteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final @NonNull FavoriteListItem item, final FavoriteListAdapter.OnItemListener onItemListener) {
            binding.setModel(item);
            binding.executePendingBindings();
        }
    }
}