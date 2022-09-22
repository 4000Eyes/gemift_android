package com.gift.gemift.Ui.DashBoard.Fragments.Friends;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gift.gemift.Model.AddFriendModel;
import com.gift.gemift.Model.ContributorsModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.AddFriendBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AddFriend extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private AddFriendBinding binding;
    private String gender = "";
    private AppPreferences appPreferences;
    ArrayList<ContributorsModel> newcontactdetailslist = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AddFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Add Friend");

        LoadUi();
    }

    private void LoadUi() {
        binding.ccp.registerCarrierNumberEditText(binding.edtMobileno);
        appPreferences = new AppPreferences(this);
        binding.radioGenderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.radioMale.isChecked()) {
                gender = "M";
            } else {
                gender = "F";
            }
        });
        binding.btnProceed.setOnClickListener(v -> {

            if (binding.edtFirstName.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter First Name");
                binding.edtFirstName.requestFocus();
            } else if (binding.edtLastName.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Last Name");
                binding.edtLastName.requestFocus();
            } else if (binding.edtMobileno.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Mobile Number");
                binding.edtMobileno.requestFocus();
            } else if (!binding.ccp.isValidFullNumber()) {
                AppUtils.showMessage(this, "Enter Valid Mobile Number");
                binding.edtMobileno.requestFocus();
            } else if (gender.isEmpty()) {
                AppUtils.showMessage(this, "Select Gender");
            } else if (binding.edtEmail.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Mail Id");
                binding.edtEmail.requestFocus();
            } else if (!binding.edtEmail.getText().toString().matches(emailPattern)) {
                AppUtils.showMessage(this, "Enter Valid Mail Id");
                binding.edtEmail.requestFocus();
            }
//            else if (binding.edtGroupName.getText().toString().isEmpty()) {
//                AppUtils.showMessage(this, "Enter Group Name");
//                binding.edtGroupName.requestFocus();
//            }
            else {
                String MobileNumber = binding.edtMobileno.getText().toString();
                String number = MobileNumber.replaceAll("[^0-9]", "");
                ContributorsModel contributorsModel = new ContributorsModel("",binding.edtFirstName.getText().toString().trim() +" "+ binding.edtLastName.getText().toString().trim(),binding.edtEmail.getText().toString().trim(),number,"",binding.edtFirstName.getText().toString().trim(),binding.edtLastName.getText().toString().trim(),gender,"India","new_contact");
                contributorsModel.setCountry_code(binding.ccp.getSelectedCountryCode());
                newcontactdetailslist.add(contributorsModel);
//                addFriend();
                Intent objIntent = new Intent();
                objIntent.putExtra("data", newcontactdetailslist);
                setResult(RESULT_OK, objIntent);
                finish();

            }
        });
    }

    private void addFriend() {
        AddFriendModel addFriendModel = new AddFriendModel(binding.edtEmail.getText().toString().trim(), gender, appPreferences.getUserId(), binding.edtLastName.getText().toString().trim(), binding.ccp.getFullNumber(), 4, binding.edtFirstName.getText().toString().trim(), binding.edtGroupName.getText().toString().trim(),"","");
        ProgressDialog pDialog = new ProgressDialog(AddFriend.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<AddFriendModel>> responseObservable = apiInterface.addFriend(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getStatus() != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().getStatus());
                                String friend_circle_id = jsonObject.getString("friend_circle_id");
                                String friend_circle_name = jsonObject.getString("friend_circle_name");
//                                Contributor(friend_circle_id);
                                startActivity(new Intent(this, DashBoard.class));
                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    } else {
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

    private void Contributor(String friend_circle_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set a title for alert dialog
        builder.setTitle("Confirm");
        // Show a message on alert dialog
        builder.setMessage("Are you sure you want add him as contributor");
        // Set the positive button
        builder.setPositiveButton("Confirm", null);
        // Set the negative button
        builder.setNegativeButton("Cancel", null);
        // Create the alert dialog
        AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        int ThemeId = AppUtils.getThemeId(this);
        String color;
        if (ThemeId == 1)
            color = "#ffffff";
        else
            color = "#000000";
        // Change the alert dialog buttons text and background color
        positiveButton.setTextColor(Color.parseColor(color));

        negativeButton.setTextColor(Color.parseColor(color));

        positiveButton.setOnClickListener(v -> {
            dialog.dismiss();
            addContributor(friend_circle_id);
        });

        negativeButton.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(this, DashBoard.class));
        });
    }

    private void addContributor(String friend_circle_id) {


        AddFriendModel addFriendModel = new AddFriendModel(binding.edtEmail.getText().toString().trim(), gender, appPreferences.getUserId(), binding.edtLastName.getText().toString().trim(), binding.ccp.getFullNumber(), 2, binding.edtFirstName.getText().toString().trim(), binding.edtGroupName.getText().toString().trim(),"","");
        addFriendModel.setFriend_circle_id(friend_circle_id);
        ProgressDialog pDialog = new ProgressDialog(AddFriend.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<AddFriendModel>> responseObservable = apiInterface.addFriend(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getStatus() != null) {
                            try {
                                startActivity(new Intent(this, DashBoard.class));
                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            AppUtils.showMessage(this, response.body().getStatus());
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

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
