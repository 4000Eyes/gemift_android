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

import java.util.List;

public class MyInterestAddAdapter extends RecyclerView.Adapter<MyInterestAddAdapter.ViewHolder> {

    private final Context mContext;

    List<Subcategory> data;

    onClickPerformed onClickPerformed;

    public MyInterestAddAdapter(Context mContext, onClickPerformed onClickPerformed, List<Subcategory> data) {
        this.mContext = mContext;
        this.data = data;
        this.onClickPerformed = onClickPerformed;

    }




    @NonNull
    @Override
    public MyInterestAddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyInterestAddAdapter.ViewHolder holder, int position) {
        if(data.size() != 0){
            holder.binding.txtName.setText(data.get(position).getWeb_subcategory_name());
            holder.binding.txtDescription.setText(data.get(position).getWeb_subcategory_desc());

            RequestOptions myOptions = new RequestOptions().centerCrop();
            Glide.with(mContext)
                    .asBitmap()
                    .apply(myOptions)
                    .error(R.drawable.ic_shoes)
                    .load(data.get(position).getImage_path())
                    .into(holder.binding.imageViewSub);

            holder.binding.llSelect.setOnClickListener(view -> {
                if(holder.binding.txtSelect.getText().toString().equals("Add")){
                    holder.binding.llSelect.setBackground(mContext.getDrawable(R.drawable.rounded_corner_red_50));
                    holder.binding.txtSelect.setText("Added");
                    onClickPerformed.onItemClick(data.get(position));
                }
            });
        }
    }


    @Override
    public int getItemCount() {

        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryListItemsBinding binding;

        public ViewHolder(@NonNull CategoryListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onClickPerformed {
        public void onItemClick(Subcategory categoryResponseItem);
    }
}
