package com.gift.gemift.Ui.DashBoard.Fragments.Profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.DataBase.Gemift_DataBase;
import com.gift.gemift.Storage.DB.ViewModel.UserDetailViewModel;
import com.gift.gemift.Ui.SplashScreen.SplashScreen;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.ProfileActivityBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProfileActivityBinding binding;
    private UserDetailViewModel userDetailViewModel;

    public ProfileFragment() {

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProfileActivityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);

        binding.signout.setOnClickListener(v -> SignOutDialog());

        compositeDisposable.add(userDetailViewModel.
                getUserDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDetailEntities -> {
                    if (userDetailEntities != null) {
                        if (userDetailEntities.length > 0) {
                            binding.txtMerchantName.setText(userDetailEntities[0].getFirst_name() + " " + userDetailEntities[0].getLast_name());
                            binding.txtPhoneNumber.setText(userDetailEntities[0].getPhone_number());
                            binding.txtEmail.setText(userDetailEntities[0].getEmail_address());
                            RequestOptions myOptions = new RequestOptions().centerCrop();
                            Glide.with(getContext())
                                    .asBitmap()
                                    .apply(myOptions)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .load(userDetailEntities[0].getImage_url())
                                    .into(binding.imgProfileImage);
                        }
                    }
                }));

        binding.imgProfileUpdate.setOnClickListener(v -> {
            ImagePicker.Companion.with(getActivity())
                    .crop()
                    .saveDir(getContext().getExternalFilesDir("images"))
                    .compress(512)
                    .galleryOnly()
                    .maxResultSize(1080, 1080)
                    .start();
        });

    }


    private void SignOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set a title for alert dialog
        builder.setTitle("Sign Out!!");
        // Show a message on alert dialog
        builder.setMessage("Are you sure you want to sign out?");
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
        int ThemeId = AppUtils.getThemeId(getContext());
        String color;
        if (ThemeId == 1)
            color = "#ffffff";
        else
            color = "#000000";
        // Change the alert dialog buttons text and background color
        positiveButton.setTextColor(Color.parseColor(color));

        negativeButton.setTextColor(Color.parseColor(color));

        positiveButton.setOnClickListener(v -> {
            Thread t = new Thread(() -> {
                Gemift_DataBase noteDatabase = Gemift_DataBase.getInstance(getContext());
                AppPreferences appPreference = new AppPreferences(getContext());
                appPreference.clearPreference();
                noteDatabase.clearAllTables();
                startActivity(new Intent(getContext(), SplashScreen.class));
            });
            t.setPriority(10);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        negativeButton.setOnClickListener(v -> dialog.cancel());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
