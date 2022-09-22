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
import com.gift.gemift.Model.UnapprovedOccasion;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.OccasionInvitesViewBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OccasionInvitesAdaptor extends RecyclerView.Adapter<OccasionInvitesAdaptor.ViewHolder> {


    private final ArrayList<UnapprovedOccasion> unapprovedOccasions;
    private final Context mContext;
    private final CompositeDisposable compositeDisposable;
    private final ApiInterface apiInterface;
    private final AppPreferences appPreferences;

    public OccasionInvitesAdaptor(ArrayList<UnapprovedOccasion> unapprovedOccasions, Context mContext, CompositeDisposable compositeDisposable, ApiInterface apiInterface, AppPreferences appPreferences) {
        this.unapprovedOccasions = unapprovedOccasions;
        this.mContext = mContext;
        this.compositeDisposable = compositeDisposable;
        this.apiInterface = apiInterface;
        this.appPreferences = appPreferences;

    }

    @NonNull
    @Override
    public OccasionInvitesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OccasionInvitesAdaptor.ViewHolder(OccasionInvitesViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OccasionInvitesAdaptor.ViewHolder holder, int position) {
        if(unapprovedOccasions.size()!=0){
            holder.binding.txtFriendCircle.setText(unapprovedOccasions.get(position).getOccasion_name());
            holder.binding.txtRelationType.setText(unapprovedOccasions.get(position).getSecret_first_name() + " " + unapprovedOccasions.get(position).getSecret_last_name());
//
            holder.binding.btnDecline.setOnClickListener(v -> RejectInvite(unapprovedOccasions.get(holder.getAbsoluteAdapterPosition()).getFriend_circle_id(), unapprovedOccasions.get(holder.getAbsoluteAdapterPosition()).getOccasion_id(), holder.getAbsoluteAdapterPosition()));
            holder.binding.btnJoin.setOnClickListener(v -> ApproveInvite(unapprovedOccasions.get(holder.getAbsoluteAdapterPosition()).getFriend_circle_id(), unapprovedOccasions.get(holder.getAbsoluteAdapterPosition()).getOccasion_id(), holder.getAbsoluteAdapterPosition()));

        }

    }

    private void ApproveInvite(String circle_id, String occasionId, int pos) {
        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        UnapprovedOccasion occasion = new UnapprovedOccasion();
        occasion.setRequest_id(3);
        occasion.setFriend_circle_id(circle_id);
        occasion.setCreator_user_id(appPreferences.getUserId());
        occasion.setOccasion_id(occasionId);
        occasion.setFlag("1");
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.approve_occasion(occasion);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(mContext, "Occasion approved");
                        removeAt(pos);
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

    private void RejectInvite(String circle_id, String occasionid, int pos) {
        ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        UnapprovedOccasion occasion = new UnapprovedOccasion();
        occasion.setRequest_id(5);
        occasion.setOccasion_id(occasionid);
        occasion.setFriend_circle_id(circle_id);

        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.approve_occasion(occasion);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(mContext, "Occasion deactivated");
                        removeAt(pos);
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
        return unapprovedOccasions.size();

    }

    public void removeAt(int position) {
        unapprovedOccasions.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, unapprovedOccasions.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final OccasionInvitesViewBinding binding;

        public ViewHolder(@NonNull OccasionInvitesViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}

