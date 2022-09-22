package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gift.gemift.Model.MyInterestsItem;
import com.gift.gemift.R;
import com.gift.gemift.databinding.CategoryListItemsBinding;

import java.util.List;

public class MyInterestViewAdapter extends RecyclerView.Adapter<MyInterestViewAdapter.ViewHolder> {

    private final Context mContext;
    List<MyInterestsItem> category;

    public MyInterestViewAdapter(Context mContext, List<MyInterestsItem> category) {
        this.mContext = mContext;
        this.category = category;
    }

    @NonNull
    @Override
    public MyInterestViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyInterestViewAdapter.ViewHolder holder, int position) {

        holder.binding.txtName.setText(category.get(position).getWebSubcategoryName());

        holder.binding.txtDescription.setVisibility(View.GONE);

        holder.binding.llSelect.setVisibility(View.GONE);

        holder.itemView.setOnLongClickListener(view -> {
            Toast.makeText(mContext, ""+category.get(position).getWebSubcategoryName(), Toast.LENGTH_SHORT).show();
            return true;
        });

        RequestOptions myOptions = new RequestOptions().centerCrop();
        Glide.with(mContext)
                .asBitmap()
                .apply(myOptions)
                .error(R.drawable.ic_shoes)
                .load(category.get(position).getImageUrl())
                .into(holder.binding.imageViewSub);
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryListItemsBinding binding;

        public ViewHolder(@NonNull CategoryListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
