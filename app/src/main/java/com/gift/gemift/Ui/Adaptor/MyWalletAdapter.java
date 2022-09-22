package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.StatusItem;
import com.gift.gemift.Model.Wallet.MessageItem;
import com.gift.gemift.Ui.DashBoard.Fragments.Wallet.MoneyFragment;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.Utils;
import com.gift.gemift.databinding.NotificationListViewBinding;
import com.gift.gemift.databinding.TransactionListItemsBinding;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.ViewHolder> {

    private final List<MessageItem> dataItem;
    private final Context mContext;

    onClickPerformed onClickPerformed;

    public MyWalletAdapter(List<MessageItem> dataItem, Context mContext, onClickPerformed onClickPerformed) {
        this.dataItem = dataItem;
        this.mContext = mContext;
        this.onClickPerformed = onClickPerformed;
    }


    @NonNull
    @Override
    public MyWalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TransactionListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyWalletAdapter.ViewHolder holder, int position) {

        String initialDate  ;

        String expiryDate;
        MessageItem data = dataItem.get(position);
        holder.binding.title.setText(data.getOccasionName()+"");

        holder.binding.productAmount.setText(data.getProductPrice()+"");


        if (data.getUserType().equals("Admin")){
            holder.binding.llOpt.setVisibility(View.GONE);
        }else if (data.getUserType().equals("Contributor")){
            holder.binding.llOpt.setVisibility(View.VISIBLE);

            if(data.getOptInFlag().equals("Y")){
                holder.binding.switchOption.setChecked(true);
            }else {
                holder.binding.switchOption.setChecked(false);
            }
        }


        if(data.getInitiatedDate()==null || data.getInitiatedDate().isEmpty()){
            holder.binding.startDate.setText("Starts :"+AppUtils.getCurrentDateCopy());
        }else {
            holder.binding.startDate.setText("Starts :"+data.getInitiatedDate());

        }

        if(data.getExpirationDate()==null || data.getExpirationDate().isEmpty()){
            String date = AppUtils.addDayWithNoOfDays(AppUtils.getCurrentDateCopy(),5);
            holder.binding.endsDate.setText("Ends :"+date.replace(date.substring(10),""));

        }else {

            String start = data.getInitiatedDate();

            String date = data.getExpirationDate();
            holder.binding.endsDate.setText("Ends :"+date.replace(date.substring(10),""));

            Date started = AppUtils.convertDate(start.replace(start.substring(10),""));
            Date expired = AppUtils.convertDate(date.replace(date.substring(10),""));
            if (!data.getUserType().equals("Admin")){
                if (started.after(expired))
                {
                    Log.i("DateValidation", started+":"+expired);
                    holder.binding.llOpt.setVisibility(View.GONE);
                }else {
                    holder.binding.llOpt.setVisibility(View.VISIBLE);
                }
            }else {
                holder.binding.llOpt.setVisibility(View.GONE);
            }

        }


        if(data.getInitiatedDate()==null || data.getInitiatedDate().isEmpty()){
            String date = AppUtils.getCurrentDateCopy();
            holder.binding.startDate.setText("Starts :"+date.replace(date.substring(10),""));



        }else {
            String date = data.getInitiatedDate();
            holder.binding.startDate.setText("Starts :"+date.replace(date.substring(10),""));
        }


        holder.binding.userCount.setText("Total Contributors  : "+data.getTotalMembers());
        holder.binding.perRate.setText(data.getPerUserShare()+"/per");






        holder.itemView.setOnClickListener(v -> {
            onClickPerformed.onClick(data);
        });



        holder.binding.switchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             onClickPerformed.onSwitchAction(data,isChecked);
            }
        });

        holder.binding.txtPay.setOnClickListener(view -> {
            onClickPerformed.onClickPay(data);
        });



    }




    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TransactionListItemsBinding binding;

        public ViewHolder(@NonNull TransactionListItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onClickPerformed{
        public void onClick(MessageItem item);
        public void onSwitchAction(MessageItem item, boolean check);
        void onClickPay(MessageItem item);
    }
}
