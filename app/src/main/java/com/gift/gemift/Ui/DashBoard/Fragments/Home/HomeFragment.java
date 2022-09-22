package com.gift.gemift.Ui.DashBoard.Fragments.Home;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.ContributorApproval;
import com.gift.gemift.Model.DataModel;
import com.gift.gemift.Model.FriendCircleListResponse;
import com.gift.gemift.Model.FriendCircleMatch;
import com.gift.gemift.Model.FriendDataModel;
import com.gift.gemift.Model.MyOccasionDataItems;
import com.gift.gemift.Model.MyOccasionResponse;
import com.gift.gemift.Model.OccasionsModel;
import com.gift.gemift.Model.Root;
import com.gift.gemift.Model.StatsModel;
import com.gift.gemift.Model.UnapprovedOccasion;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.ViewModel.FriendListViewModel;
import com.gift.gemift.Storage.DB.ViewModel.UserDetailViewModel;
import com.gift.gemift.Ui.Adaptor.ContributorApprovalAdapter;
import com.gift.gemift.Ui.Adaptor.ContributorInvitesAdaptor;
import com.gift.gemift.Ui.Adaptor.FriendInterestListAdaptor;
import com.gift.gemift.Ui.Adaptor.FriendListAdaptor;
import com.gift.gemift.Ui.Adaptor.OccasionInvitesAdaptor;
import com.gift.gemift.Ui.Adaptor.UpcomingEventsAdaptor;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.Utils;
import com.gift.gemift.databinding.FragmentHomeBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FragmentHomeBinding binding;
    private SweetAlertDialog pDialog;
    private ApiInterface apiInterface;
    private AppPreferences appPreferences;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadUi();

    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        UserDetailViewModel userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);
        FriendListViewModel friendListViewModel = new ViewModelProvider(this).get(FriendListViewModel.class);
        appPreferences = new AppPreferences(getContext());
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);


        binding.scrollView.setSmoothScrollingEnabled(true);
        getFriendList();




        compositeDisposable.add(userDetailViewModel.getUserDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(friendListEntities -> {
                    if (friendListEntities.length > 0) {
                        binding.txtTitle.setText("Hi " + friendListEntities[0].getFirst_name() + " Welcome");
                    }
                }));

        binding.imgFriendCircleExpand.setOnClickListener(v -> startActivity(new Intent(getContext(), FriendCircleList.class)));

        binding.imgFriendCircleView.setOnClickListener(v -> startActivity(new Intent(getContext(), UpcomingEventList.class)));

        binding.imgFriendCircleInvites.setOnClickListener(v -> startActivity(new Intent(getContext(), FriendGroupInvitesActivity.class)));

        binding.llAddMyInterest.setOnClickListener(view -> {startActivity(new Intent(getContext(), AddInterestOwn.class));});

        binding.llAddOccasion.setOnClickListener(view -> {
            if(appPreferences.getFriendCircleSize()!=0){
                startActivity(new Intent(getContext(), AddOccasionList.class).putExtra("isList", false));
            }else {
                AppUtils.showMessage(getActivity(),"Please Add Friend Circle");
            }
        });
        binding.txtAddOccasion.setOnClickListener(view -> {
            if(appPreferences.getFriendCircleSize()!=0){
                startActivity(new Intent(getContext(), AddOccasionList.class).putExtra("isList", false));
            }else {
                AppUtils.showMessage(getActivity(),"Please Add Friend Circle");
            }
            });

        binding.llAddInterest.setOnClickListener(view -> {

            if(appPreferences.getFriendCircleSize()!=0){
                startActivity(new Intent(getContext(), AddInterest.class).putExtra("isList", false));
            }else {
                AppUtils.showMessage(getActivity(),"Please Add Friend Circle");
            }
        });

    }

    private void getFriendList() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Observable<Response<FriendDataModel>> friendDetail = apiInterface.getFriendCircleList(appPreferences.getUserId(), 2);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {

                        appPreferences.setFriendCircleSize(response.body().getData().length);
                       if(response.body().getData().length >=4){
                            loadRecycleFriendCircleList(response.body().getData(),4 );
                        }else {
                            loadRecycleFriendCircleList(response.body().getData(),response.body().getData().length);
                        }
//                        getOccasion(appPreferences.getUserId(), appPreferences.getPhoneNumber());
                        getFriendCircleMatch(appPreferences.getUserId(), appPreferences.getPhoneNumber());


                    } else if (response.code() == 400) {
                        pDialog.dismiss();
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

    private void getFriendCircleMatch(String logged_user_id, String phone_number) {
        Observable<Response<FriendCircleMatch>> friendDetail = apiInterface.getFriendCircleMatch("get_match_score",appPreferences.getUserId());
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    getMyOccasion(logged_user_id,phone_number);

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if(response.body().getData().size()==0){


                        } else  if(response.body().getData().size() >=4){
                            binding.llScoreMatch.setVisibility(View.VISIBLE);


                            binding.recyclerviewInterestList.setAdapter(new FriendInterestListAdaptor(getContext(),response.body().getData(),4));

                        }else {
                            binding.recyclerviewInterestList.setAdapter(new FriendInterestListAdaptor(getContext(), response.body().getData(), response.body().getData().size()));

                        }
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
                    getMyOccasion(logged_user_id,phone_number);
//                    Utils.hideProgressBar(getActivity());
//                    pDialog.dismiss();
                }));
    }

    private void getMyOccasion(String logged_user_id, String phone_number) {
        Observable<Response<MyOccasionResponse>>notify = apiInterface.getUpComingOccasions(2, logged_user_id, phone_number, appPreferences.getCountryCode());

//      Observable<Response<NotificationModel>> notify = apiInterface.getInvites(1, logged_user_id, phone_number);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {

                    getStats();

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body()!= null) {
                            ArrayList<DataModel> contributorInvites = new ArrayList<>();
                            ArrayList<UnapprovedOccasion> occasionInvites = new ArrayList<>();
                            ArrayList<MyOccasionDataItems> occasionsModelArrayList = new ArrayList<>();
                            ArrayList<ContributorApproval> contributorApprovals = new ArrayList<>();

                            if(response.body().getMyOccasionData().get(0).getOccasions().size()!=0){
                                occasionsModelArrayList.addAll(response.body().getMyOccasionData().get(0).getOccasions());

                            }
                            if(response.body().getMyOccasionData().get(1).getContributor_invites().size()!=0){
                                contributorInvites.addAll(response.body().getMyOccasionData().get(1).getContributor_invites());
                            }



                            if(response.body().getMyOccasionData().get(2).getContributor_approval().size()!=0){
                                contributorApprovals.addAll(response.body().getMyOccasionData().get(2).getContributor_approval());
                            }


                            if(response.body().getMyOccasionData().size()==4){
                                if(response.body().getMyOccasionData().get(3).getUnapproved_occasions()!=null){
                                    occasionInvites.addAll(response.body().getMyOccasionData().get(3).getUnapproved_occasions());
                                }
                            }





                            LoadFiendCircleRecycle(contributorInvites);
                            binding.txtFriendCircleInvites.setText("Friends Group Invites (" + contributorInvites.size() + ")");

                            binding.txtUpcomingEvent.setText("Upcoming Event (" + occasionsModelArrayList.size() + ")");
                            LoadOccasionRecycle(occasionsModelArrayList);



                            LoadOccasionInvites(occasionInvites);
                            binding.txtOccasionApprove.setText("Occasion Invites (" + occasionInvites.size() + ")");


                            binding.txtContributorApprove.setText("Contributor Approval (" + occasionsModelArrayList.size() + ")");
                            LoadContributorApproval(contributorApprovals);
                        }

                    }


                    else if (response.code() == 400) {
                        pDialog.dismiss();
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

    private void getStats() {

        Observable<Response<StatsModel>>notify = apiInterface.getSats("get_stats");

//        Observable<Response<NotificationModel>> notify = apiInterface.getInvites(1, logged_user_id, phone_number);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body() != null) {
                          binding.llStats.setVisibility(View.VISIBLE);
                          binding.txtFriendCircleNo.setText(response.body().getSatsData().get(2).getTotalFriendCircles()+"");
                            binding.txtFriendOccasionNo.setText(response.body().getSatsData().get(1).getTotalOccasions()+"");
                            binding.txtFriendInterestNo.setText(response.body().getSatsData().get(0).getTotalInterestCount()+"");

                        }

                    }


                    else if (response.code() == 400) {
                        pDialog.dismiss();
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




    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void loadRecycleFriendCircleList(FriendCircleListResponse[] friendListEntities, int length) {
        binding.txtFriendCircle.setText("My friends Circle (" + friendListEntities.length + ")");
        if (friendListEntities.length > 0) {
//            binding.cardFriendCircle.setVisibility(View.VISIBLE);
            binding.llMyFriendsCircle.setVisibility(View.VISIBLE);

            binding.recyleFriendLits.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            FriendListAdaptor friendListAdaptor = new FriendListAdaptor(friendListEntities, getContext(),length,false);
            binding.recyleFriendLits.setAdapter(friendListAdaptor);

            binding.recyleFriendLits.setHasFixedSize(true);
            binding.recyleFriendLits.getDrawingCache(true);
            friendListAdaptor.notifyDataSetChanged();
        } else {
            binding.noFriendCircle.setVisibility(View.VISIBLE);

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadFiendCircleRecycle(ArrayList<DataModel> contributorInvites) {
        if (contributorInvites.size() > 0) {
            binding.cardFriendInvites.setVisibility(View.VISIBLE);
            binding.llGroupInvites.setVisibility(View.VISIBLE);
            binding.recyleFriendCircleInvites.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            ContributorInvitesAdaptor contributorInvitesAdaptor = new ContributorInvitesAdaptor(contributorInvites, getContext(), compositeDisposable, apiInterface, appPreferences);
            binding.recyleFriendCircleInvites.setAdapter(contributorInvitesAdaptor);
            contributorInvitesAdaptor.notifyDataSetChanged();
        } else {

            binding.noGroupInvites.setVisibility(View.VISIBLE);



        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadOccasionRecycle(ArrayList<MyOccasionDataItems> occasionsModelArrayList) {
        if (occasionsModelArrayList.size() > 0) {

            IgnoreCaseComparator icc = new IgnoreCaseComparator();
            Collections.sort(occasionsModelArrayList, icc);
            binding.cardUpcomingEvents.setVisibility(View.VISIBLE);

            binding.recyleUpcomingEvent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            UpcomingEventsAdaptor upcomingEventsAdaptor = new UpcomingEventsAdaptor(occasionsModelArrayList, getContext());
            binding.recyleUpcomingEvent.setAdapter(upcomingEventsAdaptor);
            upcomingEventsAdaptor.notifyDataSetChanged();
        } else {
            binding.noOccasion.setVisibility(View.VISIBLE);

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadOccasionInvites(ArrayList<UnapprovedOccasion> unapprovedOccasionArrayList) {
        if (unapprovedOccasionArrayList.size() > 0) {
            binding.cardOccasionInvites.setVisibility(View.VISIBLE);
            binding.llOccasionInvites.setVisibility(View.VISIBLE);

            binding.recyleOccasionInvites.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            OccasionInvitesAdaptor occasionInvitesAdaptor = new OccasionInvitesAdaptor(unapprovedOccasionArrayList, getContext(), compositeDisposable, apiInterface, appPreferences);
            binding.recyleOccasionInvites.setAdapter(occasionInvitesAdaptor);
            occasionInvitesAdaptor.notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadContributorApproval(ArrayList<ContributorApproval> contributorApprovals) {
        if (contributorApprovals.size() > 0) {
            binding.contributorApproval.setVisibility(View.VISIBLE);
            binding.llContributorApproval.setVisibility(View.VISIBLE);

            binding.recyleContributorApproval.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            ContributorApprovalAdapter occasionInvitesAdaptor = new ContributorApprovalAdapter(contributorApprovals, getContext(), compositeDisposable, apiInterface, appPreferences);
            binding.recyleContributorApproval.setAdapter(occasionInvitesAdaptor);
            occasionInvitesAdaptor.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

class IgnoreCaseComparator implements Comparator<MyOccasionDataItems> {


        @Override
        public int compare(MyOccasionDataItems ob1, MyOccasionDataItems ob2) {

            return Integer.compare(ob1.getDaysLeft(), ob2.getDaysLeft());
        }
    }

}