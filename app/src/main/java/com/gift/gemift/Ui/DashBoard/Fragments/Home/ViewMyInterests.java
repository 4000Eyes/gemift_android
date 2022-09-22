package com.gift.gemift.Ui.DashBoard.Fragments.Home;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.gift.gemift.Model.MyInterestAdded;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.MyInterestViewAdapter;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.MyIntrestBinding;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ViewMyInterests extends AppCompatActivity  {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MyIntrestBinding binding;
    private AppPreferences appPreferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyIntrestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LoadUi();
    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {

        binding.actionBack.setOnClickListener(view -> finish());
        appPreferences = new AppPreferences(this);
        Glide.with(this)
                .load(R.raw.gif_blink)
                .into(binding.progressImage);


        binding.progress.setVisibility(View.VISIBLE);

        binding.llAddMore.setOnClickListener(view -> startActivity(new Intent(this, AddInterestOwn.class)));

    }


    private void getSubInterestList() {
        binding.rvSubCategory.setVisibility(View.GONE);


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<MyInterestAdded>> getInterest = apiInterface.getSubInterest("get_subcategory",appPreferences.getUserId());
        compositeDisposable.add(getInterest
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {

                    binding.progress.setVisibility(View.GONE);

                    if((response.body() != null ? response.body().getMyInterests().size() : 0) !=0){
                        binding.llNoData.setVisibility(View.GONE);
                        binding.rvSubCategory.setVisibility(View.VISIBLE);
                        binding.rvSubCategory.setAdapter(new MyInterestViewAdapter(this, response.body().getMyInterests()));

                    }else {
                        binding.llNoData.setVisibility(View.VISIBLE);
                        binding.rvSubCategory.setVisibility(View.GONE);
                    }




                }, throwable -> {
                    binding.progress.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);

                    AppUtils.showMessage(ViewMyInterests.this, throwable.getMessage());
                }));
    }


    @Override
    protected void onResume() {
        super.onResume();

        getSubInterestList();

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
