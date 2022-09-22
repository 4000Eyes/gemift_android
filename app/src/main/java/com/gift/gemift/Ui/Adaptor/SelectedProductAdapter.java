package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.GetOccasionDetailResponse;
import com.gift.gemift.Model.InsertVoteOccasion;
import com.gift.gemift.Model.VonageOTPModel;
import com.gift.gemift.Model.VotedProductResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.OccasionDetails;
import com.gift.gemift.Ui.DashBoard.Fragments.Product.OccasionProducts;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.OccasionListViewBinding;
import com.gift.gemift.databinding.SelectedProductViewBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;

public class SelectedProductAdapter extends RecyclerView.Adapter<SelectedProductAdapter.ViewHolder> {

    private ArrayList<VotedProductResponse> votedProductResponses;
    private Context mContext;
    private ListItemClickListener mOnClickListener;


    public SelectedProductAdapter(ArrayList<VotedProductResponse> votedProductResponse, Context mContext, ListItemClickListener mOnClickListener) {
        this.votedProductResponses = votedProductResponse;
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(SelectedProductViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.tvProdname.setText(votedProductResponses.get(position).getProduct_title());
        holder.binding.tvProdprice.setText("$" + votedProductResponses.get(position).getPrice());
        holder.binding.btnFund.setOnClickListener(view -> mOnClickListener.onItemClick(holder.getAbsoluteAdapterPosition(),votedProductResponses.get(holder.getAbsoluteAdapterPosition())));

        holder.binding.totalUser.setText(votedProductResponses.get(position).getTotal_users()+" Liked");
        holder.binding.like.setOnClickListener(v->{
            ((OccasionDetails)mContext).actionLike(votedProductResponses.get(position));
        } );

        holder.binding.dislike.setOnClickListener(v->{
            ((OccasionDetails)mContext).actionDisLike(votedProductResponses.get(position));
        } );
    }

    public interface ListItemClickListener {
        void onItemClick(int position, VotedProductResponse votedProductResponse);
    }

    @Override
    public int getItemCount() {
        return votedProductResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SelectedProductViewBinding binding;

        public ViewHolder(@NonNull SelectedProductViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

