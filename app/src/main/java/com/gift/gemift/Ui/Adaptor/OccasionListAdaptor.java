package com.gift.gemift.Ui.Adaptor;

import static com.gift.gemift.Utils.Constant.CONTRIBUTOR_LIST;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
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
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.Entity.FriendListEntity;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.OccasionDetails;
import com.gift.gemift.Ui.DashBoard.Fragments.Product.OccasionProducts;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.OccasionListViewBinding;
import com.gift.gemift.databinding.UpcomingEventsBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class OccasionListAdaptor extends RecyclerView.Adapter<OccasionListAdaptor.ViewHolder> {

    private final CompositeDisposable compositeDisposable;
    private final GetOccasionDetailResponse[] getOccasionDetailResponses;
    private final Context mContext;
    private final String friendCircleId, friendAge, SecretFriendName;
    private final ArrayList<FriendListEntity> contributorList;
    private final HashMap<String, String> interestIdHashMap;

    public OccasionListAdaptor(GetOccasionDetailResponse[] getOccasionDetailResponses, CompositeDisposable compositeDisposable, String friendCircleId, Context mContext, String friendAge, ArrayList<FriendListEntity> contributorList, String SecretFriendName, HashMap<String, String> interestIdHashMap) {
        this.getOccasionDetailResponses = getOccasionDetailResponses;
        this.compositeDisposable = compositeDisposable;
        this.friendCircleId = friendCircleId;
        this.mContext = mContext;
        this.friendAge = friendAge;
        this.SecretFriendName = SecretFriendName;
        this.contributorList = contributorList;
        this.interestIdHashMap = interestIdHashMap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(UpcomingEventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String lastName = "";


        holder.binding.txtFriendName.setText(SecretFriendName);
        holder.binding.txtOccasionName.setText(getOccasionDetailResponses[position].getOccasion_name());
        holder.binding.txtOccasionDayRemains.setText(getOccasionDetailResponses[position].getDays_left()+" days away");
        holder.binding.txtOccasionDate.setText("On "+getOccasionDetailResponses[position].getOccasion_date());
        holder.binding.txtOccasionStatus.setText(""+getOccasionDetailResponses[position].getOccasionActiveStatus());

        if(getOccasionDetailResponses[position].getDays_left()>=29){
            holder.binding.txtGiftText.setText("It is not too late to find gifts.");
            holder.binding.llSelectionBtn.setVisibility(View.VISIBLE);

        }else if(getOccasionDetailResponses[position].getDays_left()>=15){
            holder.binding.txtGiftText.setText("Start preparing.");
            holder.binding.llSelectionBtn.setVisibility(View.VISIBLE);
        }else{
            holder.binding.txtGiftText.setVisibility(View.GONE);

        }

        if(getOccasionDetailResponses[position].getProduct_count()!=0){
            holder.binding.txtChoose.setVisibility(View.VISIBLE);
            holder.binding.txtChoose.setText("Voted gifts("+getOccasionDetailResponses[position].getProduct_count()+")");
        }

        holder.binding.txtChoose.setOnClickListener(view -> {

            Intent i = new Intent(mContext, OccasionDetails.class);
            i.putExtra(FRIEND_CIRCLE_ID, friendCircleId);
            i.putExtra(SECRET_FIRST_NAME, SecretFriendName);
            i.putExtra(OCCASION_ID, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_id());
            i.putExtra(OCCASION_NAME, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_name());
            i.putExtra(CONTRIBUTOR_LIST, contributorList);
            i.putExtra(OCCASION_DATE, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_date());
            mContext.startActivity(i);
        });

        holder.binding.txtPickup.setOnClickListener(view -> {
            Toast.makeText(mContext, "upcoming`", Toast.LENGTH_SHORT).show();

        });
        holder.binding.txtBuyGift.setOnClickListener(view -> {

            Gson gson = new Gson();
            Intent i = new Intent(mContext, OccasionProducts.class);
            i.putExtra(FRIEND_CIRCLE_ID, friendCircleId);
            i.putExtra(OCCASION_ID, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_id());
            i.putExtra(OCCASION_NAME, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_name());
            i.putExtra(OCCASION_DATE, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_date());
            i.putExtra("InterestList", gson.toJson(interestIdHashMap));
            mContext.startActivity(i);
        });




      /*  holder.binding.txtOccasionFrequency.setText("Frequency - " + getOccasionDetailResponses[position].getOccasion_frequency());

        if (""+getOccasionDetailResponses[position].getDays_left() != null)
            holder.binding.txtDaysRemaining.setText(getOccasionDetailResponses[position].getDays_left() + " days Remaining");
        else
            holder.binding.txtDaysRemaining.setText("0 days Remaining");

        holder.binding.imgLike.setOnClickListener(v -> voteOccasion(getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_id(), 1));

        holder.binding.imgUnlike.setOnClickListener(v -> {
            voteOccasion(getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_id(), -1);
        });

        holder.binding.linearProduct.setOnClickListener(view -> {
            Gson gson = new Gson();
            Intent i = new Intent(mContext, OccasionProducts.class);
            i.putExtra(FRIEND_CIRCLE_ID, friendCircleId);
            i.putExtra(OCCASION_ID, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_id());
            i.putExtra(OCCASION_NAME, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_name());
            i.putExtra(OCCASION_DATE, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_date());
            i.putExtra("InterestList", gson.toJson(interestIdHashMap));
            mContext.startActivity(i);
        });


        holder.binding.txtNewMsg.setText("Don't forget. " + SecretFriendName + " 's " + getOccasionDetailResponses[position].getOccasion_name() + " is on " + getOccasionDetailResponses[position].getOccasion_date() + " and it is " + getOccasionDetailResponses[position].getDays_left() + " days away.");
*/

        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(mContext, OccasionDetails.class);
            i.putExtra(FRIEND_CIRCLE_ID, friendCircleId);
            i.putExtra(SECRET_FIRST_NAME, SecretFriendName);
            i.putExtra(OCCASION_ID, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_id());
            i.putExtra(OCCASION_NAME, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_name());
            i.putExtra(CONTRIBUTOR_LIST, contributorList);
            i.putExtra(OCCASION_DATE, getOccasionDetailResponses[holder.getAbsoluteAdapterPosition()].getOccasion_date());
            mContext.startActivity(i);
        });

        if (!getOccasionDetailResponses[position].getOccasionActiveStatus().equals("Active")){
            holder.binding.llSelectionBtn.setVisibility(View.GONE);
        }
    }


    private void voteOccasion(String occasionId, int flag) {
        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(mContext);
        InsertVoteOccasion insertVoteOccasion = new InsertVoteOccasion(appPreferences.getUserId(), occasionId, "", flag, friendCircleId, 2, AppUtils.getCurrentDate());

        Observable<Response<VonageOTPModel>> friendDetail = apiInterface.insertVoteOccasion(insertVoteOccasion);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(mContext, response.body().getStatus());
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Failure");
                            Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(mContext, throwable.getMessage());
                }));
    }

    @Override
    public int getItemCount() {
        return getOccasionDetailResponses.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final UpcomingEventsBinding binding;

        public ViewHolder(@NonNull UpcomingEventsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
