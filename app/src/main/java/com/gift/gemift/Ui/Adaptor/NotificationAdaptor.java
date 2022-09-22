package com.gift.gemift.Ui.Adaptor;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.DataModel;
import com.gift.gemift.Model.StatusItem;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.InvitationGroup;
import com.gift.gemift.databinding.NotificationListViewBinding;

import java.util.ArrayList;

public class NotificationAdaptor extends RecyclerView.Adapter<NotificationAdaptor.ViewHolder> {

    private final ArrayList<StatusItem> interests;
    private final Context mContext;

    onClickPerformed onClickPerformed;

    public NotificationAdaptor(ArrayList<StatusItem> interests, Context mContext,onClickPerformed onClickPerformed) {
        this.interests = interests;
        this.mContext = mContext;
        this.onClickPerformed = onClickPerformed;
    }

    @NonNull
    @Override
    public NotificationAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(NotificationListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationAdaptor.ViewHolder holder, int position) {

        holder.binding.tvNotification.setText(interests.get(position).getDescription());

        holder.binding.txtPhoneNumber.setText(interests.get(position).getLast_created_date());

        holder.itemView.setOnClickListener(v -> {
            if(interests.get(position).getMessage_type().equals("social")){
                onClickPerformed.onClick(interests.get(position));
            }else {
                onClickPerformed.onClickWallet(interests.get(position));

            }
        });
    }


    @Override
    public int getItemCount() {
        return interests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final NotificationListViewBinding binding;

        public ViewHolder(@NonNull NotificationListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onClickPerformed{
        public void onClick(StatusItem statusItem);
        void onClickWallet(StatusItem statusItem);
    }
}
