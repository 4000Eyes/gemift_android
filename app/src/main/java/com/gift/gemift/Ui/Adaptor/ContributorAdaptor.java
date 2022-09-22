package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.ContactsInfo;
import com.gift.gemift.Model.ContributorsModel;
import com.gift.gemift.Model.FriendDataSearchModel;
import com.gift.gemift.Model.FriendSearchModel;
import com.gift.gemift.R;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.CreateFriendCircle;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.PhoneContacts_Activity;
import com.gift.gemift.databinding.ContactListViewBinding;
import com.gift.gemift.databinding.ContributorsListViewBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ContributorAdaptor extends RecyclerView.Adapter<ContributorAdaptor.ViewHolder> {

    private final List<ContributorsModel> contactsInfos;
    private final Context context;


    public ContributorAdaptor(List<ContributorsModel> contactsInfos, Context context) {
        this.contactsInfos = contactsInfos;
        this.context = context;
    }

    @NonNull
    @Override
    public ContributorAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ContributorsListViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ContributorAdaptor.ViewHolder holder, int position) {

        holder.binding.txtName.setText(contactsInfos.get(position).getFull_name());

        holder.itemView.setOnLongClickListener(view -> {
            holder.binding.imgRemove.setVisibility(View.VISIBLE);
            return true;
        });

        holder.itemView.setOnClickListener(view -> holder.binding.imgRemove.setVisibility(View.GONE));


        holder.binding.imgRemove.setOnClickListener(view -> {

            int pos = holder.getAbsoluteAdapterPosition();
            contactsInfos.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos,contactsInfos.size());

        });


    }

    @Override
    public int getItemCount() {
        return contactsInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ContributorsListViewBinding binding;

        public ViewHolder(@NonNull ContributorsListViewBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
