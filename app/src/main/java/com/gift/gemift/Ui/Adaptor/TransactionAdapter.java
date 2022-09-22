package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.Wallet.DetailItem;
import com.gift.gemift.Model.Wallet.MessageItem;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.TransactionListItemsBinding;
import com.gift.gemift.databinding.TransactionUserListItemsBinding;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<DetailItem> dataItem;
    private final Context mContext;

    AppPreferences appPreferences ;

    public TransactionAdapter(List<DetailItem> dataItem, Context mContext) {
        this.dataItem = dataItem;
        this.mContext = mContext;
        appPreferences = new AppPreferences(mContext);
    }


    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TransactionUserListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {

        String initialDate  ;

        String expiryDate;
        DetailItem data = dataItem.get(position);

        if(appPreferences.getUserId().equals(data.getUserId())){
            holder.binding.title.setText(data.getFirstName()+" "+ data.getLastName()+" (you)");
        }else {
            holder.binding.title.setText(data.getFirstName()+" "+ data.getLastName());

        }
        holder.binding.txtShare.setText("Share Amount : "+data.getPerUserShare());
        holder.binding.txtPaidAmount.setText("Paid Amount : "+data.getPaidAmount());



    }




    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TransactionUserListItemsBinding binding;

        public ViewHolder(@NonNull TransactionUserListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
