package com.gift.gemift.Ui.Adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.AddFriendModel;
import com.gift.gemift.Model.DataModel;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.InvitationGroupViewBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ContributorInvitesAdaptor extends RecyclerView.Adapter<ContributorInvitesAdaptor.ViewHolder> {
    private final ArrayList<DataModel> contributorInvites;
    private final Context mContext;
    private final CompositeDisposable compositeDisposable;
    private final ApiInterface apiInterface;
    private final AppPreferences appPreferences;

    public ContributorInvitesAdaptor(ArrayList<DataModel> contributorInvites, Context mContext, CompositeDisposable compositeDisposable, ApiInterface apiInterface, AppPreferences appPreferences) {
        this.contributorInvites = contributorInvites;
        this.mContext = mContext;
        this.compositeDisposable = compositeDisposable;
        this.apiInterface = apiInterface;
        this.appPreferences = appPreferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(InvitationGroupViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (contributorInvites.size()!=0){
            holder.binding.txtFriendCircle.setText(contributorInvites.get(position).getFriend_circle_name());

            holder.binding.txtRelationType.setText(contributorInvites.get(position).getSecret_first_name()+" "+contributorInvites.get(position).getSecret_last_name());

            holder.binding.btnDecline.setOnClickListener(v -> InsertDeclineGroup(contributorInvites.get(holder.getAbsoluteAdapterPosition()).getFriend_circle_id(), contributorInvites.get(position).getReferred_user_id(),contributorInvites.get(position).getReferrer_user_id()));

            holder.binding.btnJoin.setOnClickListener(v -> InsertJoinGroup(contributorInvites.get(holder.getAbsoluteAdapterPosition()).getFriend_circle_id(), contributorInvites.get(position).getReferred_user_id(),contributorInvites.get(position).getReferrer_user_id()));

        }
            }

    private void InsertJoinGroup(String friend_circle_id, String referred_user_id, String referrer_user_id) {
        ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        AddFriendModel joinGroup = new AddFriendModel();
        joinGroup.setRequest_id(7);
        joinGroup.setFriend_circle_id(friend_circle_id);
        joinGroup.setReferred_user_id(referred_user_id);
        joinGroup.setReferrer_user_id(referrer_user_id);
        joinGroup.setPhone_number(appPreferences.getPhoneNumber());
        joinGroup.setSignal("1");
        joinGroup.setCountry_code(appPreferences.getCountryCode());
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.insert_joingroup(joinGroup);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(mContext, "Joined group");
                        mContext.startActivity(new Intent(mContext, DashBoard.class));
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(mContext, throwable.getMessage());
                }));
    }

    private void InsertDeclineGroup(String friend_circle_id, String referred_user_id, String referrer_user_id) {
        ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        AddFriendModel joinGroup = new AddFriendModel();
        joinGroup.setRequest_id(7);
        joinGroup.setFriend_circle_id(friend_circle_id);
        joinGroup.setReferred_user_id(referred_user_id);
        joinGroup.setReferrer_user_id(referrer_user_id);
        joinGroup.setPhone_number(appPreferences.getPhoneNumber());
        joinGroup.setSignal("-1");
        joinGroup.setCountry_code(appPreferences.getCountryCode());

        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.insert_joingroup(joinGroup);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(mContext, "Declined group");
                        mContext.startActivity(new Intent(mContext, DashBoard.class));
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(mContext, throwable.getMessage());
                }));
    }

    @Override
    public int getItemCount() {

        return contributorInvites.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final InvitationGroupViewBinding binding;

        public ViewHolder(@NonNull InvitationGroupViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
