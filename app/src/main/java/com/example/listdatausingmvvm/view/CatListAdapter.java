package com.example.listdatausingmvvm.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.listdatausingmvvm.R;
import com.example.listdatausingmvvm.database.CatDataEntry;
import com.example.listdatausingmvvm.databinding.CatListItemBinding;

import java.util.List;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ViewHolder> {

    private List<CatDataEntry> catListItems;
    private ItemOnclickListener itemOnclickListener;

    CatListAdapter(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    void setCatListItems(List<CatDataEntry> catListItems) {
        this.catListItems = catListItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CatListItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cat_list_item, parent, false);
        return new ViewHolder(binding, itemOnclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CatDataEntry catListItem = catListItems.get(position);
        holder.itemBinding.titleTextView.setText(catListItem.getTitle());
        holder.itemBinding.descriptionTextView.setText(catListItem.getDescription());

        Glide.with(holder.itemBinding.catImageView.getContext())
                .load(catListItem.getImageUrl()).dontAnimate().fitCenter().diskCacheStrategy(
                DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder).into(holder.itemBinding.catImageView);
        holder.setCatListItem(catListItem);
    }

    public interface ItemOnclickListener {
        void onItemClicked(CatDataEntry catDataEntry);
    }

    @Override
    public int getItemCount() {
        return catListItems != null ? catListItems.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CatListItemBinding itemBinding;
        ItemOnclickListener itemOnclickListener;
        CatDataEntry catDataEntry;

        public void setCatListItem(CatDataEntry catDataEntry) {
            this.catDataEntry = catDataEntry;
        }

        ViewHolder(@NonNull CatListItemBinding itemBinding, final ItemOnclickListener itemOnclickListener) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
            this.itemOnclickListener = itemOnclickListener;
            itemBinding.contentContainer.setOnClickListener(view -> itemOnclickListener.onItemClicked(catDataEntry));
        }
    }
}
