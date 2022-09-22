package com.gift.gemift.Ui.Adaptor;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.CategoryItem;
import com.gift.gemift.Model.DataModel;
import com.gift.gemift.Model.GetNewCategoryResponse;
import com.gift.gemift.Model.GetNewCategoryResponseItem;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.InvitationGroup;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.FriendCircleDetail;
import com.gift.gemift.databinding.CategoryTabItemsBinding;
import com.gift.gemift.databinding.NotificationListViewBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {

    private final Context mContext;
    int selectedPosition=0;
    onClickPerformed onClickPerformed;
    List<GetNewCategoryResponseItem> category;

    public CategoryAdaptor(Context mContext, onClickPerformed onClickPerformed, List<GetNewCategoryResponseItem> category) {
        this.mContext = mContext;
        this.onClickPerformed = onClickPerformed;
        this.category = category;
    }



    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryTabItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.ViewHolder holder, int position) {
        if(selectedPosition==position){
            holder.binding.viewCategory.setVisibility(View.VISIBLE);
        }else {
            holder.binding.viewCategory.setVisibility(View.GONE);
        }

        holder.binding.txtCategory.setText(category.get(position).getWebCategoryName());

        holder.itemView.setOnClickListener(view -> {
            holder.binding.viewCategory.setVisibility(View.VISIBLE);
            selectedPosition=holder.getAdapterPosition();
            notifyDataSetChanged();
            onClickPerformed.onItemClick(position, category.get(position));
        });

        holder.itemView.setOnLongClickListener(view -> {
            Toast.makeText(mContext, ""+category.get(position).getWebCategoryName(), Toast.LENGTH_SHORT).show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryTabItemsBinding binding;

        public ViewHolder(@NonNull CategoryTabItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


   public interface onClickPerformed {
        public void onItemClick(int position, GetNewCategoryResponseItem categoryResponseItem);
    }
}
