package com.gift.gemift.Ui.DashBoard.Fragments.Friends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.FriendCircleListResponse;
import com.gift.gemift.Model.FriendDataModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.FriendListAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.FriendCircleListBinding;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class FriendsFragment extends Fragment {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FriendCircleListBinding binding;
    private AppPreferences appPreferences;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        binding = FriendCircleListBinding.inflate(inflater, container, false);
        binding.appbar.relativeAppBar.setVisibility(View.GONE);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appPreferences = new AppPreferences(getContext());
        binding.addFriend.setOnClickListener(v -> startActivity(new Intent(getContext(), CreateFriendCircle.class)));


        binding.refresh.setOnRefreshListener(() -> {
            getFriendList();
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getFriendList();
    }

    private void getFriendList() {

        if(binding.refresh.isRefreshing())
        {
            binding.refresh.setRefreshing(false);
        }

        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(getContext());
        Observable<Response<FriendDataModel>> friendDetail = apiInterface.getFriendCircleList(appPreferences.getUserId(), 2);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        appPreferences.setFriendCircleSize(response.body().getData().length);

                        loadRecycle(response.body().getData());

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
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

    @SuppressLint("NotifyDataSetChanged")
    private void loadRecycle(FriendCircleListResponse[] friendListEntities) {
        if(friendListEntities.length !=0){
            FriendListAdaptor friendListAdaptor = new FriendListAdaptor(friendListEntities, getContext(), friendListEntities.length, true);
            binding.recyleFriendLits.setAdapter(friendListAdaptor);
            binding.recyleFriendLits.setHasFixedSize(true);
            binding.recyleFriendLits.getDrawingCache(true);
            friendListAdaptor.notifyDataSetChanged();
        }else {
            binding.recyleFriendLits.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}