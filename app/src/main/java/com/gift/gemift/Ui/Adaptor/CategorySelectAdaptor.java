package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.GetNewCategoryResponseItem;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.databinding.CategoryLisItemsBinding;
import com.gift.gemift.databinding.CategoryTabItemsBinding;

import java.util.List;

public class CategorySelectAdaptor extends RecyclerView.Adapter<CategorySelectAdaptor.ViewHolder> {

    private final Context mContext;
    int selectedPosition=0;
    onClickPerformed onClickPerformed;
    List<GetNewCategoryResponseItem> category;

    AppPreferences appPreferences ;

    public CategorySelectAdaptor(Context mContext, onClickPerformed onClickPerformed, List<GetNewCategoryResponseItem> category) {
        this.mContext = mContext;
        this.onClickPerformed = onClickPerformed;
        this.category = category;
    }

    @NonNull
    @Override
    public CategorySelectAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryLisItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategorySelectAdaptor.ViewHolder holder, int position) {
//        appPreferences = new AppPreferences(mContext);
//        List<GetNewCategoryResponseItem> data =  appPreferences.getCategory();
//
//        if(data != null){
//            for(GetNewCategoryResponseItem responseItem : data){
//                if(responseItem.getWebCategoryId().equals(category.get(position).getWebCategoryId())){
//                    Log.i("lllllllllllllllllllll",responseItem.getWebCategoryName());
//                    holder.binding.llSelectBg.setVisibility(View.VISIBLE);
//                    onClickPerformed.onItemClick(true, category.get(position));
//                }
//            }
//        }


        holder.binding.textView.setText(category.get(position).getWebCategoryName());
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
            Toast.makeText(mContext, ""+category.get(holder.getAbsoluteAdapterPosition()).getWebCategoryName(), Toast.LENGTH_SHORT).show();

            return true;
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        public void onItemClick(boolean position, GetNewCategoryResponseItem categoryResponseItem);
    }
}
