package com.gift.gemift.Ui.DashBoard.Fragments.Friends;

import static com.gift.gemift.Utils.Constant.NO_INTERNET_CHECK;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gift.gemift.Model.FriendDataSearchModel;
import com.gift.gemift.Model.FriendSearchModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.SearchFriendListAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.Utils.NetworkUtil;
import com.gift.gemift.databinding.ContactListBinding;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class SearchFriend extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ContactListBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.snow), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Search Friend");

        LoadUi();
    }

    private void LoadUi() {
        compositeDisposable.add(RxTextView.textChangeEvents(binding.editSearchItem)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
                        if (!NetworkUtil.getConnectivityStatusString(SearchFriend.this).equals(NO_INTERNET_CHECK)) {
                            if (!textViewTextChangeEvent.text().toString().isEmpty())
                                searchFriendDetail(textViewTextChangeEvent.text().toString());
                            else
                                LoadRecycle(new FriendDataSearchModel[0]);
                        } else {
                            AppUtils.showMessage(SearchFriend.this, "No internet connection");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }


    private void searchFriendDetail(String name) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(this);
        String token = "Bearer " + appPreferences.getTokenId();
        Observable<Response<FriendSearchModel>> friendDetail = apiInterface.getFriendDetailSearch(token, name);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getData().length > 0) {
                            LoadRecycle(response.body().getData());
                        } else {
                            LoadRecycle(new FriendDataSearchModel[0]);
                        }
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }

    private void LoadRecycle(FriendDataSearchModel[] data) {
        SearchFriendListAdaptor searchFriendListAdaptor = new SearchFriendListAdaptor(this, binding);
        binding.recycleContactList.setAdapter(searchFriendListAdaptor);
        searchFriendListAdaptor.updateFriendSearch(Arrays.asList(data));

    }


    @Override
    protected void onDestroy() {
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
