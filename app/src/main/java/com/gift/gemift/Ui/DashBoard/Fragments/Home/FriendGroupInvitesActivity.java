package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gift.gemift.Model.DataModel;
import com.gift.gemift.Model.Datum;
import com.gift.gemift.Model.MyOccasionResponse;
import com.gift.gemift.Model.NotificationModel;
import com.gift.gemift.Model.OccasionsModel;
import com.gift.gemift.Model.Root;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.ContributorInvitesAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.FriendCircleListBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FriendGroupInvitesActivity extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public FriendCircleListBinding binding;
    private AppPreferences appPreferences;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FriendCircleListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        LoadUi();
    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        appPreferences = new AppPreferences(this);
        binding.appbar.txtTitle.setText("Friend Group Invites");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        getFriendGroupInvites();
    }

    @SuppressLint("SetTextI18n")
    private void getFriendGroupInvites() {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        Observable<Response<MyOccasionResponse>> notify = apiInterface.getUpComingOccasions(2, appPreferences.getUserId(), appPreferences.getPhoneNumber(), appPreferences.getCountryCode());
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body() != null) {
                            ArrayList<OccasionsModel> occasionsModelArrayList = new ArrayList<>();
                            ArrayList<DataModel> contributorInvites = new ArrayList<>();

                            contributorInvites.addAll(response.body().getMyOccasionData().get(0).getContributor_invites());

                            if (contributorInvites.size() > 0) {
                                LoadFiendCircleRecycle(contributorInvites);
                            }
                            pDialog.dismiss();
                        }

                    } else if (response.code() == 400) {
                        pDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    private void LoadFiendCircleRecycle(ArrayList<DataModel> contributorInvites) {
        ContributorInvitesAdaptor contributorInvitesAdaptor = new ContributorInvitesAdaptor(contributorInvites, this, compositeDisposable, apiInterface, appPreferences);
        binding.recyleFriendLits.setAdapter(contributorInvitesAdaptor);
        contributorInvitesAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

        }
        return true;
    }
}
