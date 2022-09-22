package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gift.gemift.Model.Subcategory;
import com.gift.gemift.R;
import com.gift.gemift.databinding.CategoryListItemsBinding;
import com.gift.gemift.databinding.FriendCircleSubCategoryListItemsBinding;

public class SubCategoryAdaptorFriendCircleDetails extends RecyclerView.Adapter<SubCategoryAdaptorFriendCircleDetails.ViewHolder> {

    private final Context mContext;

    Subcategory[] data;


    public SubCategoryAdaptorFriendCircleDetails(Context mContext, Subcategory[] subcategory) {
        this.mContext = mContext;
        this.data = subcategory;

    }


    @NonNull
    @Override
    public SubCategoryAdaptorFriendCircleDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FriendCircleSubCategoryListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdaptorFriendCircleDetails.ViewHolder holder, int position) {

        if(data.length != 0){
            holder.binding.txtName.setText(data[position].getWeb_subcategory_name());
            holder.binding.txtDescription.setText(data[position].getWeb_subcategory_desc());

            RequestOptions myOptions = new RequestOptions().centerCrop();
            Glide.with(mContext)
                    .asBitmap()
                    .apply(myOptions)
                    .error(R.drawable.ic_shoes)
                    .load(data[position].getImage_path())
                    .into(holder.binding.imageViewSub);




        }
    }

    @Override
    public int getItemCount() {

        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FriendCircleSubCategoryListItemsBinding binding;

        public ViewHolder(@NonNull FriendCircleSubCategoryListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
