package com.gift.gemift.Ui.DashBoard.Fragments.Friends;


import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.GEMIFT_CONTRIBUTOR;
import static com.gift.gemift.Utils.Constant.GEMIFT_SECRETFRIEND;
import static com.gift.gemift.Utils.Constant.NEWCONTACT_CONTRIBUTOR;
import static com.gift.gemift.Utils.Constant.NEWCONTACT_SECRET;
import static com.gift.gemift.Utils.Constant.PH_CONTRIBUTOR;
import static com.gift.gemift.Utils.Constant.PH_SECRETFRIEND;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_LAST_NAME;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gift.gemift.Model.AddFriendModel;
import com.gift.gemift.Model.ContributorsModel;
import com.gift.gemift.Model.FriendDataSearchModel;
import com.gift.gemift.Model.InsertCategory;
import com.gift.gemift.Model.InsertSubCategoryResponse;
import com.gift.gemift.Model.InterestList;
import com.gift.gemift.Model.List_SubCategory_Id;
import com.gift.gemift.Model.List_category_id;
import com.gift.gemift.Model.OccasionInsertResponse;
import com.gift.gemift.Model.OccasionListResponse;
import com.gift.gemift.Model.OccasionModel;
import com.gift.gemift.Model.StandardOccasion;
import com.gift.gemift.Model.Subcategory;
import com.gift.gemift.Model.SubcategoryResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.ContributorAdaptor;
import com.gift.gemift.Ui.Adaptor.FriendCircleCategorySelectAdaptor;
import com.gift.gemift.Ui.Adaptor.FriendCircleSubCategoryAdaptor;
import com.gift.gemift.Ui.Adaptor.InterestAdaptor;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.AddInterest;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.AddOccasionList;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.CreatefriendCircleBinding;
import com.google.android.material.chip.Chip;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CreateFriendCircle extends AppCompatActivity implements FriendCircleCategorySelectAdaptor.onClickPerformed, FriendCircleSubCategoryAdaptor.onClickPerformed {
    private final String[] frequency_list = {"Every Year", "Every Month", "Every Week", "One time"};
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final HashMap<String, String> interestIdHashMap = new HashMap<>();
    private final HashMap<String, String> subInterestIdHashMap = new HashMap<>();
    private final HashMap<String, String> occasionIdHashMap = new HashMap<>();
    CreatefriendCircleBinding binding;
    AppPreferences appPreferences;
    ArrayList<FriendDataSearchModel> arrayList = new ArrayList<>();
    ArrayList<AddFriendModel> NewContact_arrayList = new ArrayList<>();
    ActivityResultLauncher<Intent> activityResultLauncher;
    ArrayList<ContributorsModel> contriarraylist = new ArrayList<>();
    ArrayList<ContributorsModel> fullcontriarraylist = new ArrayList<>();
    ArrayList<InterestList> interestDataModel = new ArrayList<>();
    ArrayList<String> occasionList = new ArrayList<>();
    ArrayList<String> occasionIdList = new ArrayList<>();
    //    5a99469a-de31-4645-96df-358b9615e51c
    private String gender = "", friendCircleId = "", secretFriendId = "", occasionId = "0";
    private InterestAdaptor interestAdaptor;
    private String frequency_selected, occasion_selected = "", secretFriendAge = "";
    private boolean isInterestSubmitted = false;
    private List<Subcategory> interestSubCategory  = new ArrayList<>();
    private List<InterestList> interestCategory  = new ArrayList<>();



    boolean isFirstCome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreatefriendCircleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        appPreferences = new AppPreferences(this);
        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Create friend circle");

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                contriarraylist = (ArrayList<ContributorsModel>) bundle.get("data");

            } else if (result.getResultCode() == 101 && result.getData() != null) {
                Bundle bundle1 = result.getData().getExtras();

            }
            LoadUI();
        });

        LoadUI();


        binding.linearGroup.setOnClickListener(v -> {
            if (binding.expandableView.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, new AutoTransition());
                binding.expandableView.setVisibility(View.VISIBLE);
                binding.arrowBtn.setBackgroundResource(R.drawable.ic_arrow_down);
                hideViews(1);
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, new AutoTransition());
                binding.expandableView.setVisibility(View.GONE);
                binding.arrowBtn.setBackgroundResource(R.drawable.ic_arrow_right);
            }
        });

        binding.linearContri.setOnClickListener(v -> {
            if (!friendCircleId.equals("")) {
                if (binding.expandableView2.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(binding.cardView2, new AutoTransition());
                    binding.expandableView2.setVisibility(View.VISIBLE);
                    binding.arrowContributors.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.scrollView.setSmoothScrollingEnabled(true);
                    hideViews(2);

                } else {
                    TransitionManager.beginDelayedTransition(binding.cardView2, new AutoTransition());
                    binding.expandableView2.setVisibility(View.GONE);
                    binding.arrowContributors.setBackgroundResource(R.drawable.ic_arrow_right);
                }
            } else {
                AppUtils.showMessage(this, "Please create secret friend");
            }
        });

        binding.linearInterest.setOnClickListener(v -> {
            if (!friendCircleId.equals("")) {
//                if (binding.expandableView3.getVisibility() == View.GONE) {
//                    TransitionManager.beginDelayedTransition(binding.cardView3, new AutoTransition());
//                    binding.txtInterest.setText(binding.txtName.getText().toString() + " interested on?");
//                    getInterestList(friendCircleId);
//                    binding.expandableView3.setVisibility(View.VISIBLE);
//                    binding.arrowInterest.setBackgroundResource(R.drawable.ic_arrow_down);
//                    binding.scrollView.setSmoothScrollingEnabled(true);
//                    hideViews(3);
//
//                } else {
//                    TransitionManager.beginDelayedTransition(binding.cardView3, new AutoTransition());
//                    binding.expandableView3.setVisibility(View.GONE);
//                    binding.arrowInterest.setBackgroundResource(R.drawable.ic_arrow_right);
//                }

                Intent intent = new Intent(this, AddInterest.class);
                intent.putExtra("isList",true);
                intent.putExtra(CIRCLE_ID, friendCircleId);
                intent.putExtra(FRIEND_CIRCLE_NAME, binding.edtgroupName.getText().toString());

                startActivity(intent);
            } else {
                AppUtils.showMessage(this, "Please create secret friend");
            }
        });
        binding.linearOccasion.setOnClickListener(v -> {
            if (!friendCircleId.equals("")) {
                if (binding.expandableView4.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(binding.cardView4, new AutoTransition());
                    getAllOccasion(friendCircleId);
//                getAllOccasion("a9398c8a-c102-4593-b4ac-efd28e483268");
                    binding.expandableView4.setVisibility(View.VISIBLE);
                    binding.arrowOccasion.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.scrollView.setSmoothScrollingEnabled(true);

                    hideViews(4);

                } else {
                    TransitionManager.beginDelayedTransition(binding.cardView4, new AutoTransition());
                    binding.expandableView4.setVisibility(View.GONE);
                    binding.arrowOccasion.setBackgroundResource(R.drawable.ic_arrow_right);
                }
            } else {
                AppUtils.showMessage(this, "Please create secret friend");
            }
        });

        binding.btnSubmitoccasion.setOnClickListener(view -> {
            if (!friendCircleId.equals("")) {
                if (binding.txtOccasionName.getText().toString().isEmpty()) {
                    AppUtils.showMessage(CreateFriendCircle.this, "Please enter occasion name");
                } else {
                    String occasion = binding.txtOccasionName.getText().toString();
                    occasionId = occasionIdHashMap.get(occasion);
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
                            AppUtils.showMessage(CreateFriendCircle.this, "Select frequency");
                        } else {
                            InsertCustomOccasion(friendCircleId, dateSelected);
                        }
                    } else {
                        InsertStandardOccasion(friendCircleId, dateSelected);
                    }
                }
            } else {
                AppUtils.showMessage(CreateFriendCircle.this, "Create secret friend");

            }
        });

        binding.linearOther.setOnClickListener(v -> {
            if (binding.expandableView5.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView5, new AutoTransition());
                binding.expandableView5.setVisibility(View.VISIBLE);
                binding.arrowOther.setBackgroundResource(R.drawable.ic_arrow_down);
                binding.scrollView.setSmoothScrollingEnabled(true);
                hideViews(5);
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView5, new AutoTransition());
                binding.expandableView5.setVisibility(View.GONE);
                binding.arrowOther.setBackgroundResource(R.drawable.ic_arrow_right);
            }


        });


        binding.gemContact.setOnClickListener(view -> {
            arrayList.clear();
            appPreferences.setIdentifier(GEMIFT_SECRETFRIEND);
            Intent intent = new Intent(this, SearchFriend.class);
            activityResultLauncher.launch(intent);

        });
        binding.phoneContacts.setOnClickListener(view -> {
//            Phone_arrayList.clear();
            appPreferences.setIdentifier(PH_SECRETFRIEND);
            Intent intent = new Intent(this, PhoneContacts_Activity.class);
            activityResultLauncher.launch(intent);

        });
        binding.contriPhonecontacts.setOnClickListener(view -> {
            contriarraylist.clear();
            appPreferences.setIdentifier(PH_CONTRIBUTOR);
            Intent intent = new Intent(this, PhoneContacts_Activity.class);
            activityResultLauncher.launch(intent);

        });

        binding.contriGemcontact.setOnClickListener(view -> {
            contriarraylist.clear();
            appPreferences.setIdentifier(GEMIFT_CONTRIBUTOR);
            Intent intent = new Intent(this, SearchFriend.class);
            activityResultLauncher.launch(intent);

        });
        binding.newcontactSecret.setOnClickListener(view -> {
            appPreferences.setIdentifier(NEWCONTACT_SECRET);
            Intent intent = new Intent(this, AddFriend.class);
            activityResultLauncher.launch(intent);


        });

        binding.imgRemove.setOnClickListener(view -> {
            arrayList.clear();
            binding.layoutSecretfriend.setVisibility(View.GONE);
            binding.edtgroupName.setEnabled(true);
            binding.edtAge.setEnabled(true);
            binding.radioMale.setEnabled(true);
            binding.radioFemale.setEnabled(true);
            binding.btnProceed.setVisibility(View.VISIBLE);

        });

        binding.btnProceed.setOnClickListener(view -> {

            if (binding.edtgroupName.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Group name");
            } else if (binding.edtAge.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Age");
            } else {
                secretFriendAge = binding.edtAge.getText().toString();
                if (appPreferences.getIdentifier().equals(NEWCONTACT_SECRET)) {
                    addNewContactFriend(contriarraylist);
                } else if (appPreferences.getIdentifier().equals(GEMIFT_SECRETFRIEND)) {
                    if (gender.isEmpty()) {
                        AppUtils.showMessage(this, "Select Gender");
                    } else {
                        addGemiftCircle(contriarraylist.get(0).getUser_id(), appPreferences.getUserId());
                        secretFriendId = contriarraylist.get(0).getUser_id();
                    }
                } else {
                    if (gender.isEmpty()) {
                        AppUtils.showMessage(this, "Select Gender");
                    } else {
                        addPhoneContactFriend(contriarraylist);
                    }

                }

            }
        });

        binding.radioGenderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.radioMale.isChecked()) {
                gender = "M";
            } else {
                gender = "F";
            }
        });


        binding.contriNewcontact.setOnClickListener(view -> {
            contriarraylist.clear();
            appPreferences.setIdentifier(NEWCONTACT_CONTRIBUTOR);
            Intent intent = new Intent(CreateFriendCircle.this, AddFriend.class);
            activityResultLauncher.launch(intent);

        });

        binding.submitContributors.setOnClickListener(view -> {

            for (int i = 0; i < fullcontriarraylist.size(); i++) {
                String identity = fullcontriarraylist.get(i).getContact_identity();
                String referred_id = fullcontriarraylist.get(i).getUser_id();
                if (identity.equals("gemift_contacts")) {
                    addGemiftContributor(friendCircleId, referred_id);
                } else if (identity.equals("new_contact")) {
                    addNewContactContributor(friendCircleId, fullcontriarraylist.get(i));
                } else {
                    addPhoneContactContributor(friendCircleId, fullcontriarraylist.get(i));
                }
            }
            binding.submitContributors.setVisibility(View.GONE);

        });

        binding.submitInterest.setOnClickListener(view -> {

            if(!isInterestSubmitted){
                ArrayList<List_category_id> listCategoryIdArrayList = new ArrayList<>();

                if(interestCategory.size()!=0){
                    for(InterestList interestList:interestCategory){
                        List_category_id list_category_id = new List_category_id(interestList.getWeb_category_id(), 1);
                        listCategoryIdArrayList.add(list_category_id);
                    }
                    addInterest(friendCircleId, listCategoryIdArrayList.toArray(new List_category_id[0]));

                }else {
                    AppUtils.showMessage(this, "Select any Interest");

                }
            }else {
                ArrayList<List_SubCategory_Id> listSubCategoryIdArrayList = new ArrayList<>();
                if(interestSubCategory.size()!=0){
                    for (Subcategory subcategory : interestSubCategory) {
                        String id = subcategory.getWeb_subcategory_id();
                        List_SubCategory_Id list_subCategory_id = new List_SubCategory_Id(id, 1);
                        listSubCategoryIdArrayList.add(list_subCategory_id);

                    }
                    insertSubInterest(friendCircleId, listSubCategoryIdArrayList.toArray(new List_SubCategory_Id[0]));
                }else {
                    AppUtils.showMessage(this, "Select any Sub Interest");
                }
            }

         /*   ArrayList<List_category_id> listCategoryIdArrayList = new ArrayList<>();
            for (InterestList interestList : interestDataModel) {
                if (interestList.isSelected()) {
                    List_category_id list_category_id = new List_category_id(interestList.getWeb_category_id(), 1);
                    listCategoryIdArrayList.add(list_category_id);
                }
            }
            addInterest(friendCircleId, listCategoryIdArrayList.toArray(new List_category_id[0]));
*/
            /*ArrayList<String> list = new ArrayList<>();
            for (int id : binding.chipGroup.getCheckedChipIds()) {
                Chip chip = findViewById(id);
                list.add(chip.getText().toString());
            }
            if (list.size() > 0) {
                if (!isInterestSubmitted) {
                    ArrayList<List_category_id> listCategoryIdArrayList = new ArrayList<>();
                    for (String chipName : list) {
                        String id = interestIdHashMap.get(chipName);
                        List_category_id list_category_id = new List_category_id(id, 1);
                        listCategoryIdArrayList.add(list_category_id);

                    }
                    addInterest(friendCircleId, listCategoryIdArrayList.toArray(new List_category_id[0]));
                } else {
                    ArrayList<List_SubCategory_Id> listSubCategoryIdArrayList = new ArrayList<>();
                    for (String chipName : list) {
                        String id = subInterestIdHashMap.get(chipName);
                        List_SubCategory_Id list_subCategory_id = new List_SubCategory_Id(id, 1);
                        listSubCategoryIdArrayList.add(list_subCategory_id);

                    }
                    insertSubInterest(friendCircleId, listSubCategoryIdArrayList.toArray(new List_SubCategory_Id[0]));
                }
            } else {
                if (!isInterestSubmitted)
                    AppUtils.showMessage(this, "Select any Interest");
                else
                    AppUtils.showMessage(this, "Select any Sub Interest");
            }*/
        });


        binding.txtCustomInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CreateFriendCircle.this, AddOccasionList.class);
                intent.putExtra("isList", true);
                intent.putExtra(FRIEND_CIRCLE_ID, friendCircleId);
                activityResultLauncher.launch(intent);
            }

        });

    }


    @SuppressLint("SetTextI18n")
    private void LoadUI() {
        Intent dataIntent = getIntent();

        String friendDetail = dataIntent.getStringExtra("friendDetail");

        if (appPreferences.getIdentifier() != null) {
            if (appPreferences.getIdentifier().equals(GEMIFT_SECRETFRIEND) || appPreferences.getIdentifier().equals(PH_SECRETFRIEND) || appPreferences.getIdentifier().equals(NEWCONTACT_SECRET)) {
                if (contriarraylist.isEmpty()) {
                    binding.linearNewcontact.setVisibility(View.VISIBLE);
                } else {
                    binding.layoutSecretfriend.setVisibility(View.VISIBLE);
                    if (appPreferences.getIdentifier().equals(NEWCONTACT_SECRET)) {
                        binding.edtAge.setText("");
                        binding.linearGender.setVisibility(View.GONE);
                        binding.txtName.setText(contriarraylist.get(0).getFull_name());
                        binding.txtPhoneNumber.setText(contriarraylist.get(0).getCountry_code()+" "+contriarraylist.get(0).getPhone_number());

                    } else {
                        binding.edtAge.setText("");
                        binding.radioGenderGroup.clearCheck();
                        binding.linearGender.setVisibility(View.VISIBLE);
                        binding.txtName.setText(contriarraylist.get(0).getFull_name());
                        binding.txtPhoneNumber.setText(contriarraylist.get(0).getPhone_number());

                    }
                }
            } else if (appPreferences.getIdentifier().equals(GEMIFT_CONTRIBUTOR) || appPreferences.getIdentifier().equals(PH_CONTRIBUTOR) || appPreferences.getIdentifier().equals(NEWCONTACT_CONTRIBUTOR)) {
                if (contriarraylist.isEmpty()) {
                    binding.linearNewcontact.setVisibility(View.VISIBLE);
                } else {
                    binding.linearAllcontri.setVisibility(View.VISIBLE);

                    fullcontriarraylist.addAll(contriarraylist);
                    if (fullcontriarraylist.size() > 0) {
                        binding.submitContributors.setVisibility(View.VISIBLE);
                    }
                    ContributorAdaptor contributorAdaptor = new ContributorAdaptor(fullcontriarraylist, CreateFriendCircle.this);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(CreateFriendCircle.this, LinearLayoutManager.HORIZONTAL, false));
                    binding.recyclerview.setAdapter(contributorAdaptor);
                }

            }
//
        }

        ArrayAdapter<String> frequency_adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, frequency_list);
        binding.txtFrequency.setAdapter(frequency_adaptor);
        binding.txtFrequency.setOnItemClickListener((parent, view, position, id) -> {
            frequency_selected = parent.getItemAtPosition(position).toString();

        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, occasionList);

        compositeDisposable.add(RxTextView.textChangeEvents(binding.txtOccasionName)
                        .skipInitialValue()
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                            @Override
                            public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
//
                                binding.txtOccasionName.setThreshold(2);
                                binding.txtOccasionName.setAdapter(adapter);
                                binding.txtOccasionName.setOnItemClickListener((parent, view, position, id) -> {
                                    occasion_selected = parent.getItemAtPosition(position).toString();
                                    binding.frequency.setVisibility(View.GONE);

                                });

                                if (occasion_selected.equals(binding.txtOccasionName.getText().toString())) {

                                    binding.frequency.setVisibility(View.GONE);
                                } else {
                                    binding.frequency.setVisibility(View.VISIBLE);

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

    private void getInterestList(String circleId) {
        ProgressDialog pDialog = new ProgressDialog(CreateFriendCircle.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<ArrayList<InterestList>>> friendDetail = apiInterface.getInterest(1, circleId);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
//                        interestDataModel.clear();
//                        interestDataModel = response.body();
//                        loadRecycle(response.body());
                        binding.chipGroup.removeAllViews();
                        if (response.body().size() > 0)
//                            addInterestChip(response.body());
                            binding.gvCategories.setAdapter(new  FriendCircleCategorySelectAdaptor(this, this, response.body()));
                        else {
                            isInterestSubmitted = true;
                            binding.submitInterest.setText("Add Sub Interest");
                            getSubInterestList(friendCircleId);
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(CreateFriendCircle.this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(CreateFriendCircle.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
//                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = response.errorBody().toString();
                            Toast.makeText(CreateFriendCircle.this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(CreateFriendCircle.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(CreateFriendCircle.this, throwable.getMessage());
                }));
    }

//    private void loadRecycle(ArrayList<InterestList> interestDataModel) {
//
//        binding.interestRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
//        interestAdaptor = new InterestAdaptor(interestDataModel, CreateFriendCircle.this);
//        binding.interestRecyclerview.setAdapter(interestAdaptor);
//        interestAdaptor.notifyDataSetChanged();
//
//    }

    private void addPhoneContactFriend(ArrayList<ContributorsModel> phone_arrayList) {
        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(4);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setEmail_address("abcc@gmail.com");
        addFriendModel.setPhone_number(phone_arrayList.get(0).getPhone_number());
        addFriendModel.setFirst_name(phone_arrayList.get(0).getFirst_name());
        addFriendModel.setLast_name(phone_arrayList.get(0).getLast_name());
        addFriendModel.setGender(gender);
        addFriendModel.setLocation("India");
        addFriendModel.setGroup_name(binding.edtgroupName.getText().toString().trim());
        addFriendModel.setImage_url("");
        addFriendModel.setAge(binding.edtAge.getText().toString());
        addFriendModel.setCountry_code(phone_arrayList.get(0).getCountry_code());

        ProgressDialog pDialog = new ProgressDialog(CreateFriendCircle.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<ResponseBody>> responseObservable = apiInterface.addFriendCircle(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject statusjson = jsonObject.getJSONObject("status");
//                        friendCircleId = statusjson.getString("friend_circle_id");
                        friendCircleId = (String) statusjson.get("friend_circle_id");
                        binding.edtgroupName.setEnabled(false);
                        binding.edtAge.setEnabled(false);
                        binding.radioMale.setEnabled(false);
                        binding.radioFemale.setEnabled(false);
                        binding.btnProceed.setVisibility(View.GONE);
                        binding.imgRemove.setVisibility(View.GONE);

                        binding.phoneContacts.setEnabled(false);
                        binding.gemContact.setEnabled(false);
                        binding.newcontactSecret.setEnabled(false);
//
                        Toast.makeText(this, "Secret Friend Added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }


    private void addNewContactFriend(ArrayList<ContributorsModel> newContact_arrayList) {
        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(4);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setEmail_address(newContact_arrayList.get(0).getEmail_address());
        addFriendModel.setPhone_number(newContact_arrayList.get(0).getPhone_number());
        addFriendModel.setFirst_name(newContact_arrayList.get(0).getFirst_name());
        addFriendModel.setLast_name(newContact_arrayList.get(0).getLast_name());
        addFriendModel.setGender(newContact_arrayList.get(0).getGender());
        addFriendModel.setLocation("India");
        addFriendModel.setGroup_name(binding.edtgroupName.getText().toString().trim());
        addFriendModel.setImage_url("");
        addFriendModel.setAge(binding.edtAge.getText().toString());
        addFriendModel.setCountry_code(contriarraylist.get(0).getCountry_code());

        ProgressDialog pDialog = new ProgressDialog(CreateFriendCircle.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<ResponseBody>> responseObservable = apiInterface.addFriendCircle(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject statusjson = jsonObject.getJSONObject("status");
//                        friendCircleId = statusjson.getString("friend_circle_id");
                        friendCircleId = (String) statusjson.get("friend_circle_id");
                        binding.edtgroupName.setEnabled(false);
                        binding.edtAge.setEnabled(false);
                        binding.radioMale.setEnabled(false);
                        binding.radioFemale.setEnabled(false);
                        binding.btnProceed.setVisibility(View.GONE);
                        binding.imgRemove.setVisibility(View.GONE);
                        binding.phoneContacts.setEnabled(false);
                        binding.gemContact.setEnabled(false);
                        binding.newcontactSecret.setEnabled(false);
                        Toast.makeText(this, "Secret Friend Added successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }

    private void addGemiftCircle(String friend_id, String userId) {
        ProgressDialog pDialog = new ProgressDialog(CreateFriendCircle.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(3);
        addFriendModel.setReferrer_user_id(userId);
        addFriendModel.setReferred_user_id(friend_id);
        addFriendModel.setGroup_name(binding.edtgroupName.getText().toString().trim());
        addFriendModel.setImage_url("");
        addFriendModel.setAge(binding.edtAge.getText().toString());
        addFriendModel.setGender(gender);

        Observable<Response<ResponseBody>> responseObservable = apiInterface.addFriendCircle(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject statusjson = jsonObject.getJSONObject("status");
//                        friendCircleId = statusjson.getString("friend_circle_id");
                        friendCircleId = (String) statusjson.get("friend_circle_id");

                        binding.edtgroupName.setEnabled(false);
                        binding.edtAge.setEnabled(false);
                        binding.radioMale.setEnabled(false);
                        binding.radioFemale.setEnabled(false);
                        binding.btnProceed.setVisibility(View.GONE);
                        binding.imgRemove.setVisibility(View.GONE);
                        binding.phoneContacts.setEnabled(false);
                        binding.gemContact.setEnabled(false);
                        binding.newcontactSecret.setEnabled(false);
//                        friendCircleId = jsonObject.getString("friend_circle_id");
//                        String circle_name = jsonObject.getString("friend_circle_name");

                        Toast.makeText(this, "Secret Friend Added successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));
    }

    private void addGemiftContributor(String friend_circle_id, String referred_userid) {

        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(1);
        addFriendModel.setFriend_circle_id(friend_circle_id);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setReferred_user_id(referred_userid);
        Log.e("referrerId :", appPreferences.getUserId());
        Log.e("referredId :", referred_userid);
        ProgressDialog pDialog = new ProgressDialog(CreateFriendCircle.this);
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
                                String asdf = response.body().toString();
//                                startActivity(new Intent(this, DashBoard.class));
                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }

    private void addNewContactContributor(String friend_circle_id, ContributorsModel fullcontriarraylist) {

        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(2);
        addFriendModel.setFriend_circle_id(friend_circle_id);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setEmail_address(fullcontriarraylist.getEmail_address());
        addFriendModel.setPhone_number(fullcontriarraylist.getPhone_number());
        addFriendModel.setFirst_name(fullcontriarraylist.getFirst_name());
        addFriendModel.setLast_name(fullcontriarraylist.getLast_name());
        addFriendModel.setGender(fullcontriarraylist.getGender());
        addFriendModel.setLocation(fullcontriarraylist.getLocation());
        addFriendModel.setCountry_code(fullcontriarraylist.getCountry_code());


        ProgressDialog pDialog = new ProgressDialog(CreateFriendCircle.this);
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
                                String asdf = response.body().toString();

                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }

    private void addPhoneContactContributor(String friend_circle_id, ContributorsModel fullcontriarraylist) {

        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(2);
        addFriendModel.setFriend_circle_id(friend_circle_id);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setEmail_address(" ");
        addFriendModel.setPhone_number(fullcontriarraylist.getPhone_number());
        addFriendModel.setFirst_name(fullcontriarraylist.getFirst_name());
        addFriendModel.setLast_name(fullcontriarraylist.getLast_name());
        addFriendModel.setGender("M");
        addFriendModel.setLocation("India");
        addFriendModel.setCountry_code(fullcontriarraylist.getCountry_code());


        ProgressDialog pDialog = new ProgressDialog(CreateFriendCircle.this);
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
                                String asdf = response.body().toString();

                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            AppUtils.showMessage(this, response.body().getStatus());
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }

    private void addInterest(String circle_id, List_category_id[] list_category_ids) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        InsertCategory insertCategory = new InsertCategory(list_category_ids, appPreferences.getUserId(), circle_id, 1);
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.addCategoryUser(insertCategory);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(this, "Interest added successfully");
                        isInterestSubmitted = true;
                        binding.submitInterest.setText("Add Sub Interest");
                        getSubInterestList(friendCircleId);
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(CreateFriendCircle.this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(CreateFriendCircle.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(CreateFriendCircle.this, throwable.getMessage());
                }));
    }

    private void getSubInterestList(String circleId) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<SubcategoryResponse>> friendDetail = apiInterface.getSubInterest(2, circleId, Integer.valueOf(secretFriendAge));
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getSubcategory().length > 0) {
                            binding.gvCategories.setLayoutManager(new GridLayoutManager(this,2));
//                            binding.gvCategories.setAdapter(new FriendCircleSubCategoryAdaptor(this,this, response.body().getSubcategory()));
                        }
                    } else if (response.code() == 400 || response.code() == 401) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }


    private void getAllOccasion(String circle_id) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
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
                        occasionList.clear();
                        OccasionListResponse[] occasionDetailResponse = response.body().getOccasion_name();
                        for (OccasionListResponse occasionName : occasionDetailResponse) {
                            occasionList.add(occasionName.getOccasion_name());
                            occasionIdHashMap.put(occasionName.getOccasion_name(), occasionName.getOccasion_id());
                        }
//                        Loadspinner(occasionList);
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    private void InsertStandardOccasion(String circle_id, String dateSelected) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(this);
        StandardOccasion standardOccasion = new StandardOccasion(1, circle_id, appPreferences.getUserId(), occasionId, dateSelected, "India");

        Observable<Response<ResponseBody>> friendDetail = apiInterface.insertStandardOccasion(standardOccasion);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
//                        startActivity(new Intent(this, DashBoard.class));
                        String sss = response.body().string();
                        binding.txtOccasionName.setEnabled(false);
                        binding.txtOccasionName.setClickable(false);
                        binding.txtFrequency.setClickable(false);
                        binding.txtFrequency.setEnabled(false);
                        binding.datePicker.setEnabled(false);
                        binding.btnSubmitoccasion.setVisibility(View.GONE);
                        Toast.makeText(this, "Occasion Added successfully", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    private void InsertCustomOccasion(String circle_id, String dateSelected) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(this);
        OccasionInsertResponse occasionInsertResponse = new OccasionInsertResponse(appPreferences.getUserId(), "", dateSelected, binding.txtOccasionName.getText().toString(), circle_id, 4, frequency_selected);

        Observable<Response<ResponseBody>> friendDetail = apiInterface.insertOccasion(occasionInsertResponse);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        binding.txtOccasionName.setEnabled(false);
                        binding.txtOccasionName.setClickable(false);
                        binding.txtFrequency.setClickable(false);
                        binding.txtFrequency.setEnabled(false);
                        binding.datePicker.setEnabled(false);
                        binding.btnSubmitoccasion.setVisibility(View.GONE);
                        Toast.makeText(this, "Occasion Added successfully", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    private void Loadspinner(ArrayList<String> occasion_name) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, occasion_name);
        binding.txtOccasionName.setAdapter(adapter);
        binding.txtOccasionName.setOnItemClickListener((parent, view, position, id) -> {
            occasion_selected = parent.getItemAtPosition(position).toString();

        });


    }

    private void addInterestChip(ArrayList<InterestList> interestDataModel) {
        for (int i = 0; i < interestDataModel.size(); i++) {
            interestIdHashMap.put(interestDataModel.get(i).getWeb_category_name(), interestDataModel.get(i).getWeb_category_id());
            Chip chip = new Chip(this);
            chip.setId(i);
            chip.setTag(i);
            chip.setText(interestDataModel.get(i).getWeb_category_name());
            chip.setCheckable(true);

            chip.setOnCheckedChangeListener((compoundButton, b) -> {
                int tag = (int) compoundButton.getTag();

            });
            binding.chipGroup.addView(chip);
        }

        binding.chipGroup.invalidate();
        binding.chipGroup.setOnCheckedChangeListener((chipGroup, i) -> {
            Chip chip = chipGroup.findViewById(i);

            if (chip != null)
                Toast.makeText(getApplicationContext(), "Chip is " + chip.getText().toString(), Toast.LENGTH_SHORT).show();

        });

        binding.chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
        });
    }

    private void addSubInterestChip(Subcategory[] subcategory) {
        binding.chipGroup.removeAllViews();
        for (int i = 0; i < subcategory.length; i++) {
            subInterestIdHashMap.put(subcategory[i].getWeb_subcategory_name(), subcategory[i].getWeb_subcategory_id());
            Chip chip = new Chip(this);
            chip.setId(i);
            chip.setTag(i);
            chip.setText(subcategory[i].getWeb_subcategory_name());
            chip.setCheckable(true);

            chip.setOnCheckedChangeListener((compoundButton, b) -> {
                int tag = (int) compoundButton.getTag();
            });
            binding.chipGroup.addView(chip);
        }

        binding.chipGroup.invalidate();
        binding.chipGroup.setOnCheckedChangeListener((chipGroup, i) -> {
            Chip chip = chipGroup.findViewById(i);
            if (chip != null)
                Toast.makeText(getApplicationContext(), "Chip is " + chip.getText().toString(), Toast.LENGTH_SHORT).show();

        });

        binding.chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
        });
    }

    private void insertSubInterest(String circle_id, List_SubCategory_Id[] list_category_ids) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        InsertSubCategoryResponse insertCategory = new InsertSubCategoryResponse(list_category_ids, appPreferences.getUserId(), circle_id, 1);
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.addSubCategoryUser(insertCategory);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        binding.submitInterest.setVisibility(View.GONE);
                        binding.chipGroup.setEnabled(false);
                        Toast.makeText(this, "Sub interest Added successfully", Toast.LENGTH_SHORT).show();

                    } else if (response.code() == 400 || response.code() == 401) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            next();
            return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void next() {
        if(friendCircleId.equals("")){
            finish();
        }else {
            startActivity(new Intent(this, DashBoard.class));
            finishAffinity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            next();

        }
        return true;
    }


    private void hideViews(int view){

      if(view!=5){
          TransitionManager.beginDelayedTransition(binding.cardView5, new AutoTransition());
          binding.expandableView5.setVisibility(View.GONE);
          binding.arrowOther.setBackgroundResource(R.drawable.ic_arrow_right);

      }

        if(view!=4){
            TransitionManager.beginDelayedTransition(binding.cardView4, new AutoTransition());
            binding.expandableView4.setVisibility(View.GONE);
            binding.arrowOccasion.setBackgroundResource(R.drawable.ic_arrow_right);
        }

        if(view !=3){
            TransitionManager.beginDelayedTransition(binding.cardView3, new AutoTransition());
            binding.expandableView3.setVisibility(View.GONE);
            binding.arrowInterest.setBackgroundResource(R.drawable.ic_arrow_right);
        }

        if(view !=2){
            TransitionManager.beginDelayedTransition(binding.cardView2, new AutoTransition());
            binding.expandableView2.setVisibility(View.GONE);
            binding.arrowContributors.setBackgroundResource(R.drawable.ic_arrow_right);
        }

        if(view!=1){
            TransitionManager.beginDelayedTransition(binding.cardView, new AutoTransition());
            binding.expandableView.setVisibility(View.GONE);
            binding.arrowBtn.setBackgroundResource(R.drawable.ic_arrow_right);
        }


        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                binding.scrollView.smoothScrollTo(0,500);
                handler.postDelayed(this, 1000);
            }
        };



    }

    @Override
    public void onItemClick(boolean position, InterestList categoryResponseItem) {
        if(position){
            interestCategory.add(categoryResponseItem);
        }else {
            interestCategory.remove(categoryResponseItem);
        }
    }

    @Override
    public void onItemClick(boolean status, Subcategory categoryResponseItem) {
        if(status){
            interestSubCategory.add(categoryResponseItem);
        }else {
            interestSubCategory.remove(categoryResponseItem);
        }
    }
}
