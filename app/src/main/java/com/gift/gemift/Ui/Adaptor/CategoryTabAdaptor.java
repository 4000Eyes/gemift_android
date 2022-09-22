package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.SubcategoriesItem;
import com.gift.gemift.Model.Subcategory;
import com.gift.gemift.Model.getCategoryDetail;
import com.gift.gemift.databinding.CategoryLisItemsBinding;

import java.util.List;

public class CategoryTabAdaptor extends RecyclerView.Adapter<CategoryTabAdaptor.ViewHolder> {

    private final Context mContext;
    List<SubcategoriesItem> category;
    int size;



    public CategoryTabAdaptor(Context mContext, List<SubcategoriesItem>  categories,int size) {
        this.mContext = mContext;
        this.category = categories;
        this.size = size;
    }


    @NonNull
    @Override
    public CategoryTabAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryLisItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryTabAdaptor.ViewHolder holder, int position) {


        holder.binding.textView.setText(category.get(position).getSubcategoryName());



        holder.itemView.setOnLongClickListener(view -> {
            Toast.makeText(mContext, ""+ category.get(position).getSubcategoryName(), Toast.LENGTH_SHORT).show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
      return  size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryLisItemsBinding binding;

        public ViewHolder(@NonNull CategoryLisItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



}
