package com.gift.gemift.Ui.Adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.ContributorApproval;
import com.gift.gemift.Model.ContributorApprovalInputModel;
import com.gift.gemift.Model.UnapprovedOccasion;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.Utils;
import com.gift.gemift.databinding.OccasionInvitesViewBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ContributorApprovalAdapter extends RecyclerView.Adapter<ContributorApprovalAdapter.ViewHolder> {


    private final ArrayList<ContributorApproval> contributorApprovals;
    private final Context mContext;
    private final CompositeDisposable compositeDisposable;
    private final ApiInterface apiInterface;
    private final AppPreferences appPreferences;

    public ContributorApprovalAdapter(ArrayList<ContributorApproval> contributorApprovals, Context mContext, CompositeDisposable compositeDisposable, ApiInterface apiInterface, AppPreferences appPreferences) {
        this.contributorApprovals = contributorApprovals;
        this.mContext = mContext;
        this.compositeDisposable = compositeDisposable;
        this.apiInterface = apiInterface;
        this.appPreferences = appPreferences;

    }

    @NonNull
    @Override
    public ContributorApprovalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContributorApprovalAdapter.ViewHolder(OccasionInvitesViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContributorApprovalAdapter.ViewHolder holder, int position) {
        if(contributorApprovals.size()!=0){

            ContributorApproval contributorApproval = contributorApprovals.get(position);
            holder.binding.txtFriendCircle.setText(contributorApprovals.get(position).getFriendCircleName());
            holder.binding.txtRelationType.setText(contributorApprovals.get(position).getFirstName() + " " + contributorApprovals.get(position).getLastName());
//
            holder.binding.btnDecline.setOnClickListener(v -> RejectInvite(contributorApproval.getFriendCircleId(),contributorApproval.getReferredUserId(), contributorApproval.getReferrerUserId(), position));
            holder.binding.btnJoin.setOnClickListener(v -> ApproveInvite(contributorApproval.getFriendCircleId(),contributorApproval.getReferredUserId(), contributorApproval.getReferrerUserId(), position));

        }

    }

    private void ApproveInvite(String friendCircleID, String referredUserId, String referrerUserId, int position) {
        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ContributorApprovalInputModel occasion = new ContributorApprovalInputModel();
        occasion.setRequestId(6);
        occasion.setListFriendCircleId(friendCircleID);
        occasion.setReferredUserId(referredUserId);
        occasion.setReferrerUserId(referrerUserId);
        occasion.setTimeZone(TimeZone.getDefault().getID());
        occasion.setApiCallTime(AppUtils.getCurrentDateCopy());
        occasion.setApprovalFlag(1);
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.contributorApproval(occasion);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(mContext, "Occasion approved");
                        removeAt(position);
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Failure:");
                            Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Failure:");
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

    private void RejectInvite(String friendCircleID, String referredUserId, String referrerUserId, int position) {
        ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ContributorApprovalInputModel occasion = new ContributorApprovalInputModel();
        occasion.setRequestId(6);
        occasion.setListFriendCircleId(friendCircleID);
        occasion.setReferredUserId(referredUserId);
        occasion.setReferrerUserId(referrerUserId);
        occasion.setTimeZone(TimeZone.getDefault().getID());
        occasion.setApiCallTime(AppUtils.getCurrentDateCopy());
        occasion.setApprovalFlag(-1);

        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.contributorApproval(occasion);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(mContext, "Occasion deactivated");
                        removeAt(position);
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
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
    public int getItemCount()

    {
        return contributorApprovals.size();

    }

    public void removeAt(int position) {
        contributorApprovals.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, contributorApprovals.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final OccasionInvitesViewBinding binding;

        public ViewHolder(@NonNull OccasionInvitesViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

