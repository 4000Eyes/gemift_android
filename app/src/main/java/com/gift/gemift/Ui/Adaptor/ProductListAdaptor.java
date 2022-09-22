package com.gift.gemift.Ui.Adaptor;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;
import static com.gift.gemift.Utils.Constant.PROD_DESCRIPTION;
import static com.gift.gemift.Utils.Constant.PROD_ID;
import static com.gift.gemift.Utils.Constant.PROD_PRICE;
import static com.gift.gemift.Utils.Constant.PROD_TITLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.ProductDatum;
import com.gift.gemift.Ui.DashBoard.Fragments.Product.OccasionProducts;
import com.gift.gemift.Ui.DashBoard.Fragments.Product.ProductDetails;
import com.gift.gemift.databinding.ProductlistViewBinding;

import java.util.ArrayList;

public class ProductListAdaptor extends RecyclerView.Adapter<ProductListAdaptor.ViewHolder> {

    private final ArrayList<ProductDatum> productData;
    private final Context mContext;
    String occasionId, occasionName, occasionDate, friendcircleId;


    public ProductListAdaptor(ArrayList<ProductDatum> productData, Context mContext, String friendcircleId, String occasionId, String occasionName, String occasionDate) {
        this.productData = productData;
        this.mContext = mContext;
        this.friendcircleId = friendcircleId;
        this.occasionId = occasionId;
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductListAdaptor.ViewHolder(ProductlistViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductListAdaptor.ViewHolder holder, int position) {


        holder.binding.prodName.setText(productData.get(position).getProduct_title());
        holder.binding.prodPrice.setText("$" + productData.get(position).getPrice());
        holder.binding.prodDesc.setText(productData.get(position).getProduct_description());

        holder.binding.Recommend.setOnClickListener(view -> {
            ((OccasionProducts)mContext).voteProduct(String.valueOf(productData.get(position).getProduct_id()),productData.get(position).getProduct_title(), String.valueOf(productData.get(position).getPrice()),friendcircleId);
        });

        holder.binding.view.setOnClickListener(view -> {
           Intent intent = new Intent(mContext, ProductDetails.class);
            intent.putExtra(FRIEND_CIRCLE_ID, friendcircleId);
            intent.putExtra(OCCASION_NAME, occasionName);
            intent.putExtra(OCCASION_DATE, occasionDate);
            intent.putExtra(PROD_TITLE, productData.get(position).getProduct_title());
            intent.putExtra(PROD_DESCRIPTION, productData.get(position).getProduct_description());
            intent.putExtra(PROD_PRICE, String.valueOf(productData.get(position).getPrice()));
            intent.putExtra(PROD_ID, String.valueOf(productData.get(position).getProduct_id()));
            mContext.startActivity(intent);
        });

        holder.binding.imgLike.setOnClickListener(view -> {
//            ((OccasionProducts)mContext).productLike(String.valueOf(productData.get(position).getProduct_id()),productData.get(position).getProduct_title(), String.valueOf(productData.get(position).getPrice()),friendcircleId);

        });
    }


    @Override
    public int getItemCount() {
        return productData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ProductlistViewBinding binding;

        public ViewHolder(@NonNull ProductlistViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

