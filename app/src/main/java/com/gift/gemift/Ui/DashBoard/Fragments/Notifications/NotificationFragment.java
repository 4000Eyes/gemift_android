package com.gift.gemift.Ui.DashBoard.Fragments.Notifications;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.DataModel;
import com.gift.gemift.Model.NotificationResponse;
import com.gift.gemift.Model.Root;
import com.gift.gemift.Model.StatsModel;
import com.gift.gemift.Model.StatusItem;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.NotificationAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.FragmentNotificationBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    private static int firstVisibleInListview;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FragmentNotificationBinding binding;
    private String phone_number;
    NotificationAdaptor.onClickPerformed onClickPerformed;



    public NotificationFragment(
            NotificationAdaptor.onClickPerformed onClickPerformed) {
        this.onClickPerformed = onClickPerformed;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getInvites();

    }

    private void getInvites() {
        final ArrayList<StatusItem> interests = new ArrayList<>();

        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        AppPreferences appPreferences = new AppPreferences(getContext());
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<NotificationResponse>>notify = apiInterface.getNotification(10, appPreferences.getUserId());
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body() != null) {

                            if(response.body().getStatus().size()!=0){
                                interests.addAll(response.body().getStatus());
                                loadRecycle(interests);
                            }else {
                                binding.noData.setVisibility(View.VISIBLE);
                            }

                        }


                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            String error = response.errorBody().toString();
                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(getContext(), throwable.getMessage());
                }));
    }

    private void loadRecycle(ArrayList<StatusItem> interests) {
        binding.rvNotificationlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NotificationAdaptor draftFormCollapse_adaptor = new NotificationAdaptor(interests, getContext(), onClickPerformed);
        binding.rvNotificationlist.setAdapter(draftFormCollapse_adaptor);
        draftFormCollapse_adaptor.notifyDataSetChanged();

    }


    @Override
    public void onResume() {
        super.onResume();
        getInvites();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}