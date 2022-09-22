package com.gift.gemift.Ui.Adaptor;

import static com.gift.gemift.Utils.Constant.AGE;
import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_IMAGE;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_TITLE;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_LAST_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gift.gemift.Model.FriendCircleListResponse;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.AddInterest;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.AddOccasionList;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.FriendCircleDetail;
import com.gift.gemift.Ui.DashBoard.Fragments.Product.OccasionProducts;
import com.gift.gemift.databinding.FriendListViewBinding;

public class FriendListAdaptor extends RecyclerView.Adapter<FriendListAdaptor.ViewHolder> {
    private final FriendCircleListResponse[] friendListEntities;
    private final Context mContext;
    RequestOptions myOptions = new RequestOptions().centerCrop();
    boolean isNotHomeFragment;
    int size;

    public FriendListAdaptor(FriendCircleListResponse[] friendListEntities, Context mContext, int size, boolean b) {
        this.friendListEntities = friendListEntities;
        this.mContext = mContext;
        this.size = size;

        this.isNotHomeFragment = b;

    }

    @NonNull
    @Override
    public FriendListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FriendListViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FriendListAdaptor.ViewHolder holder, int position) {


        if(isNotHomeFragment){
            holder.binding.llAction.setVisibility(View.VISIBLE);
            holder.binding.txtFriendCircleName.setVisibility(View.VISIBLE);
            holder.binding.line.setVisibility(View.VISIBLE);
            holder.binding.txtFriendCircleName.setVisibility(View.GONE);
        }

        if (friendListEntities[position].getImage_url() != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .apply(myOptions)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .load(friendListEntities[position].getImage_url())
                    .into(holder.binding.imgFriendCircle);
        }


        holder.binding.txtFriendCircle.setText(friendListEntities[position].getFriend_circle_name());
        holder.binding.txtFriendCircleName.setText(friendListEntities[position].getFriend_circle_name());

//
        holder.binding.bnAddOccasion.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddOccasionList.class);
            intent.putExtra("isList", true);
            intent.putExtra(CIRCLE_ID, friendListEntities[holder.getAbsoluteAdapterPosition()].getFriend_circle_id());
            intent.putExtra(FRIEND_CIRCLE_TITLE, friendListEntities[holder.getAbsoluteAdapterPosition()].getFriend_circle_name());
            intent.putExtra(FRIEND_CIRCLE_IMAGE, friendListEntities[holder.getAbsoluteAdapterPosition()].getImage_url());
            mContext.startActivity(intent);
        });

        holder.binding.txtContributorSize.setText(friendListEntities[position].getTotal_contributors() + " Contributors");

        holder.binding.bnAddInterest.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddInterest.class);
            intent.putExtra("isList",true);
            intent.putExtra(CIRCLE_ID, friendListEntities[holder.getAbsoluteAdapterPosition()].getFriend_circle_id());
            intent.putExtra(FRIEND_CIRCLE_NAME, friendListEntities[holder.getAbsoluteAdapterPosition()].getFriend_circle_name());
            intent.putExtra(SECRET_FIRST_NAME, friendListEntities[holder.getAbsoluteAdapterPosition()].getSecret_first_name());
            intent.putExtra(SECRET_LAST_NAME, friendListEntities[holder.getAbsoluteAdapterPosition()].getSecret_last_name());
            mContext.startActivity(intent);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, FriendCircleDetail.class);
            intent.putExtra(FRIEND_CIRCLE_ID, friendListEntities[position].getFriend_circle_id());
            intent.putExtra(FRIEND_CIRCLE_NAME, friendListEntities[position].getFriend_circle_name());
            intent.putExtra(AGE, friendListEntities[position].getAge());
            mContext.startActivity(intent);
        });

        holder.binding.imgProd.setOnClickListener(view -> {
            Intent i = new Intent(mContext, OccasionProducts.class);
            i.putExtra(FRIEND_CIRCLE_ID, friendListEntities[holder.getAbsoluteAdapterPosition()].getFriend_circle_id());
            mContext.startActivity(i);

        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FriendListViewBinding binding;

        public ViewHolder(@NonNull FriendListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
