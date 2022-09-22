package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Storage.DB.Entity.FriendListEntity;
import com.gift.gemift.databinding.ContributorListViewBinding;

import java.util.ArrayList;

public class ContributorListAdaptor extends RecyclerView.Adapter<ContributorListAdaptor.ViewHolder> {

    private final ArrayList<FriendListEntity> contributorList;
    private final Context mContext;

    public ContributorListAdaptor(ArrayList<FriendListEntity> contributorList, Context mContext) {
        this.contributorList = contributorList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ContributorListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ContributorListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContributorListAdaptor.ViewHolder holder, int position) {

        holder.binding.txtId.setText((position + 1) + ".");
        holder.binding.txtFullName.setText(contributorList.get(position).getFirst_name() + " " + contributorList.get(position).getLast_name());
        holder.binding.txtPhoneNumber.setVisibility(View.VISIBLE);
        holder.binding.txtMailId.setVisibility(View.GONE);
        holder.binding.txtStatus.setVisibility(View.VISIBLE);
        holder.binding.txtMailId.setText(contributorList.get(position).getEmail_address());
        holder.binding.txtPhoneNumber.setText(contributorList.get(position).getPhone_number());

        String  s = ""+contributorList.get(position).getApproval_status();

        if(s.equals("-1")){
            holder.binding.txtStatus.setText("Status : Rejected");
        }else if(s.equals("1")){
            holder.binding.txtStatus.setText("Status : Approved");
        }else {
            holder.binding.txtStatus.setText("Status : Pending");
        }
    }

    @Override
    public int getItemCount() {
        return contributorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ContributorListViewBinding binding;

        public ViewHolder(@NonNull ContributorListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
