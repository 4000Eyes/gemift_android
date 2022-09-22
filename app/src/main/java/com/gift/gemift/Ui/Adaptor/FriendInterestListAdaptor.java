package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.gift.gemift.Model.FriendCircleDataItem;
import com.gift.gemift.databinding.InterestMatchListItemBinding;

import java.util.List;

public class FriendInterestListAdaptor extends RecyclerView.Adapter<FriendInterestListAdaptor.ViewHolder> {
    private final Context mContext;
    RequestOptions myOptions = new RequestOptions().centerCrop();
    int size;

    List<FriendCircleDataItem> data;

    public FriendInterestListAdaptor(Context mContext, List<FriendCircleDataItem> data, int size) {
        this.mContext = mContext;
        this.size = size;
        this.data = data;

    }

    @NonNull
    @Override
    public FriendInterestListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(InterestMatchListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FriendInterestListAdaptor.ViewHolder holder, int position) {
        FriendCircleDataItem dataItem =  data.get(position);
        holder.binding.txtFriendCircleScore.setText(dataItem.getFriendCircleScore()+"%");
        holder.binding.txtYourScore.setText(dataItem.getCurrentUserScore()+"%");
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final InterestMatchListItemBinding binding;

        public ViewHolder(@NonNull InterestMatchListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
