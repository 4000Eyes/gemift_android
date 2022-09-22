package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_IMAGE;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_TITLE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.FriendCircleListResponse;
import com.gift.gemift.Model.FriendDataModel;
import com.gift.gemift.Model.OccasionInsertResponse;
import com.gift.gemift.Model.OccasionListResponse;
import com.gift.gemift.Model.OccasionModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.OccasionListBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AddOccasionList extends AppCompatActivity {

    private final String[] frequency_list = {"Every Year", "Every Month", "Every Week"};
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ArrayList<String> occasionList = new ArrayList<>();
    private final HashMap<String, String> occasionIdHashMap = new HashMap<>();
    private OccasionListBinding binding;
    private String frequency_selected = "", occasion_selected = "", occasion_id, circle_id;
    private AppPreferences appPreferences;
    String friendCircleName;
    String friendCircleImage;
    boolean isList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OccasionListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Add Ocassion");

        LoadUi();
    }

    private void LoadUi() {
        Intent dataIntent = getIntent();
        appPreferences = new AppPreferences(this);

        isList = dataIntent.getBooleanExtra("isList", false);

        if(isList){
            binding.llSpinner.setVisibility(View.GONE);
            circle_id = dataIntent.getStringExtra(CIRCLE_ID);
            friendCircleName = dataIntent.getStringExtra(FRIEND_CIRCLE_TITLE);
            friendCircleImage = dataIntent.getStringExtra(FRIEND_CIRCLE_IMAGE);
            binding.txtRelationType.setText(friendCircleName);
            if (friendCircleImage != null) {
                RequestOptions myOptions = new RequestOptions().centerCrop();
                Glide.with(this)
                        .asBitmap()
                        .apply(myOptions)
                        .load(friendCircleImage)
                        .into(binding.imgFriends2);
            }


            getFrequencyList(circle_id);
        }else {
            getFriendList();
        }

        binding.btnProceed.setOnClickListener(v -> {
            if (binding.txtOccasionName.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Occasion name");
            } else {
                int month = binding.datePicker.getMonth() + 1;
                String dayOfMonth = "";
                String selectedMonth = "" + month;
                String selectedDay = "" + binding.datePicker.getDayOfMonth();
                if (month < 10) {
                    selectedMonth = "0" + month;
                }
                if (binding.datePicker.getDayOfMonth() < 10) {
                    selectedDay = "0" + selectedDay;
                }

                String dateSelected = selectedDay + "/" + selectedMonth + "/" + binding.datePicker.getYear();
                if (binding.frequency.getVisibility() == View.VISIBLE) {
                    if (binding.txtFrequency.getText().toString().isEmpty()) {
                        AppUtils.showMessage(this, "Enter frequency");
                    } else {
                        OccasionInsertResponse customOccasion = new OccasionInsertResponse(appPreferences.getUserId(), "", dateSelected, binding.txtOccasionName.getText().toString(), circle_id, 4, frequency_selected);
                        InsertOccasion(customOccasion);
                    }
                } else {
                    OccasionInsertResponse standardOccasion = new OccasionInsertResponse(appPreferences.getUserId(), "", dateSelected, circle_id, 1, occasion_id);
                    InsertOccasion(standardOccasion);
                }
            }
        });

        binding.txtCustomInterest.setOnClickListener(v -> startActivity(new Intent(this, CustomOccasionList.class)));
    }

    private void getFriendList() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Observable<Response<FriendDataModel>> friendDetail = apiInterface.getFriendCircleList(appPreferences.getUserId(), 2);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {

                        appPreferences.setFriendCircleSize(response.body().getData().length);

                        List<String> circleName = new ArrayList<>();
                        for(FriendCircleListResponse friendCircleListResponse : response.body().getData()){
                            circleName.add(friendCircleListResponse.getFriend_circle_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_dropdown_item, circleName);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        binding.spinner.setAdapter(adapter);

                        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FriendCircleListResponse friendCircleListResponse= response.body().getData()[position];
                                circle_id = friendCircleListResponse.getFriend_circle_id();
                                friendCircleName = friendCircleListResponse.getFriend_circle_name();
                                friendCircleImage = friendCircleListResponse.getImage_url();
                                binding.txtRelationType.setText(friendCircleName);
                                if (friendCircleImage != null) {
                                    RequestOptions myOptions = new RequestOptions().centerCrop();
                                    Glide.with(AddOccasionList.this)
                                            .asBitmap()
                                            .apply(myOptions)
                                            .load(friendCircleImage)
                                            .into(binding.imgFriends2);
                                }


                                getFrequencyList(circle_id);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


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


    private void getFrequencyList(String circle_id) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<OccasionModel>> friendDetail = apiInterface.getFrequencyList(3, circle_id, appPreferences.getUserId());
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {

                        OccasionListResponse[] occasionDetailResponse = response.body().getOccasion_name();
                        for (OccasionListResponse occasionName : occasionDetailResponse) {
                            occasionList.add(occasionName.getOccasion_name());
                            occasionIdHashMap.put(occasionName.getOccasion_name(), occasionName.getOccasion_id());
                        }
                        loadAdaptor(occasionList);
                    } else if (response.code() == 400) {
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

    private void loadAdaptor(ArrayList<String> occasionList) {
        ArrayAdapter<String> frequency_adaptor = new ArrayAdapter<>(AddOccasionList.this, android.R.layout.simple_spinner_dropdown_item, frequency_list);
        binding.txtFrequency.setAdapter(frequency_adaptor);
        binding.txtFrequency.setOnItemClickListener((parent, view, position, id) -> {
            frequency_selected = parent.getItemAtPosition(position).toString();

        });

        ArrayAdapter<String> occasion_adaptor = new ArrayAdapter<>(AddOccasionList.this, android.R.layout.simple_spinner_dropdown_item, occasionList);
        binding.txtOccasionName.setAdapter(occasion_adaptor);
        binding.txtOccasionName.setOnItemClickListener((parent, view, position, id) -> {
            occasion_selected = parent.getItemAtPosition(position).toString();
            occasion_id = occasionIdHashMap.get(occasion_selected);
            binding.frequency.setVisibility(View.GONE);
        });


        binding.txtOccasionName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                frequency_selected = "";
                occasion_id = "";
                binding.frequency.setVisibility(View.VISIBLE);
            }
        });
    }

    private void InsertOccasion(OccasionInsertResponse occasionInsertResponse) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Observable<Response<ResponseBody>> friendDetail = apiInterface.insertOccasion(occasionInsertResponse);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
//                        ConfirmationDialog();
                        AppUtils.showMessage(this, "Occasion added successfully");
                        startActivity(new Intent(this, DashBoard.class));
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
//                            errorDialog(error);
                            AppUtils.showMessage(this, error);
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

    private void ConfirmationDialog() {
//        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//                .setTitleText("Success")
//                .setContentText("Occasion added successfully")
//                .setConfirmText("Ok")
//                .setConfirmClickListener(sweetAlertDialog -> startActivity(new Intent(this, DashBoard.class)))
//                .show();


    }

    private void errorDialog(String error) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Failure")
                .setContentText(error)
                .setConfirmText("Ok")
                .setConfirmClickListener(sweetAlertDialog -> startActivity(new Intent(this, DashBoard.class)))
                .show();
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
