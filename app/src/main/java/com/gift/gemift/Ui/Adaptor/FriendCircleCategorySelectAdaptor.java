package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.InterestList;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.databinding.CategoryLisItemsBinding;

import java.util.ArrayList;

public class FriendCircleCategorySelectAdaptor extends RecyclerView.Adapter<FriendCircleCategorySelectAdaptor.ViewHolder> {

    private final Context mContext;
    int selectedPosition=0;
    onClickPerformed onClickPerformed;
    ArrayList<InterestList> category;

    AppPreferences appPreferences ;

    public FriendCircleCategorySelectAdaptor(Context mContext, onClickPerformed onClickPerformed, ArrayList<InterestList> category) {
        this.mContext = mContext;
        this.onClickPerformed = onClickPerformed;
        this.category = category;
    }

    @NonNull
    @Override
    public FriendCircleCategorySelectAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryLisItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FriendCircleCategorySelectAdaptor.ViewHolder holder, int position) {



        holder.binding.textView.setText(category.get(position).getWeb_category_name());
        holder.itemView.setOnClickListener(view -> {

            if (holder.binding.llSelectBg.getVisibility() == View.VISIBLE) {
                holder.binding.llSelectBg.setVisibility(View.GONE);
                onClickPerformed.onItemClick(false, category.get(position));
            } else {
                holder.binding.llSelectBg.setVisibility(View.VISIBLE);
                onClickPerformed.onItemClick(true, category.get(position));
            }
        });

        holder.itemView.setOnLongClickListener(view -> {
            Toast.makeText(mContext, ""+category.get(position).getWeb_category_name(), Toast.LENGTH_SHORT).show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryLisItemsBinding binding;

        public ViewHolder(@NonNull CategoryLisItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


   public interface onClickPerformed {
        public void onItemClick(boolean position, InterestList categoryResponseItem);
    }
}
