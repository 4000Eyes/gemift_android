package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.ContributorsModel;
import com.gift.gemift.Model.FriendDataSearchModel;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.AddFriendCircle;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.CreateFriendCircle;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.SearchFriend;
import com.gift.gemift.Utils.SearchDiffUtil;
import com.gift.gemift.Utils.Utils;
import com.gift.gemift.databinding.ContactListBinding;
import com.gift.gemift.databinding.SearchFriendViewBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.gift.gemift.Utils.Constant.GEMIFT_SECRETFRIEND;


public class SearchFriendListAdaptor extends RecyclerView.Adapter<SearchFriendListAdaptor.ViewHolder> {

    private final Context mContext;
    private final List<FriendDataSearchModel> newFriendList = new ArrayList<>();
    boolean isSelectedMode = false;
    ContactListBinding contactListBinding;
    ArrayList<FriendDataSearchModel> selectedItems = new ArrayList<>();
    AppPreferences appPreferences;
    ArrayList<ContributorsModel> contactslist = new ArrayList<>();

    public SearchFriendListAdaptor(Context mContext, ContactListBinding binding) {
        this.mContext = mContext;
        this.contactListBinding = binding;
    }


    @NonNull
    @Override
    public SearchFriendListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        appPreferences = new AppPreferences(mContext);
        return new SearchFriendListAdaptor.ViewHolder(SearchFriendViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SearchFriendListAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.txtFullName.setText(newFriendList.get(position).getFull_name());
        holder.binding.txtPhoneNumber.setText(newFriendList.get(position).getPhone_number());
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(mContext, AddFriendCircle.class);
//            intent.putExtra("friendDetail", new Gson().toJson(newFriendList.get(position), FriendDataSearchModel.class));
//            mContext.startActivity(intent);
//            Intent intent = new Intent(mContext, CreateFriendCircle.class);
//            intent.putExtra("friendDetail", new Gson().toJson(newFriendList.get(position), FriendDataSearchModel.class));
//            mContext.startActivity(intent);
//
//        });
        holder.itemView.setBackgroundColor(newFriendList.get(position).isSelected() ? Color.GRAY : Color.WHITE);

        if (appPreferences.getIdentifier().equals(GEMIFT_SECRETFRIEND)) {

            holder.itemView.setOnClickListener(v -> {
//                contactListBinding.appbar.imgDone.setVisibility(View.VISIBLE);
                if (selectedItems.contains(newFriendList.get(holder.getAbsoluteAdapterPosition()))) {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    selectedItems.remove(newFriendList.get(holder.getAbsoluteAdapterPosition()));
                    contactslist.remove(newFriendList.get(holder.getAbsoluteAdapterPosition()));

                } else {
                    holder.itemView.setBackgroundColor(R.color.colorPrimary);
                    selectedItems.add(newFriendList.get(holder.getAbsoluteAdapterPosition()));
                    ContributorsModel contributorsModel = new ContributorsModel(newFriendList.get(position).getUser_id(),newFriendList.get(position).getFull_name(),"",newFriendList.get(position).getPhone_number(),"",newFriendList.get(position).getFirst_name(),newFriendList.get(position).getLast_name(),"","","gemift_contacts");
                    contactslist.add(contributorsModel);

                }
                    Intent objIntent = new Intent();
                    objIntent.putExtra("data", contactslist);
                    ((Activity) mContext).setResult(RESULT_OK, objIntent);
                    ((Activity) mContext).finish();


            });
        } else {

            holder.itemView.setOnClickListener(v -> {
                if(newFriendList.get(holder.getAbsoluteAdapterPosition()).isSelected()){
                    newFriendList.get(holder.getAbsoluteAdapterPosition()).setSelected(false);
                }else {
                    newFriendList.get(holder.getAbsoluteAdapterPosition()).setSelected(true);
                }
//                newFriendList.get(holder.getAbsoluteAdapterPosition()).setSelected(!newFriendList.get(holder.getAbsoluteAdapterPosition()).isSelected());
                holder.itemView.setBackgroundColor(newFriendList.get(position).isSelected() ? Color.GRAY : Color.WHITE);
                contactListBinding.appbar.imgDone.setVisibility(View.VISIBLE);
//                if (selectedItems.contains(newFriendList.get(holder.getAbsoluteAdapterPosition()))) {
//                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//                    selectedItems.remove(newFriendList.get(holder.getAbsoluteAdapterPosition()));
//                    contactslist.remove(newFriendList.get(holder.getAbsoluteAdapterPosition()));
//
//                } else {
//                    holder.itemView.setBackgroundColor(R.color.colorPrimary);
                    if(newFriendList.get(position).isSelected()){
                        selectedItems.add(newFriendList.get(holder.getAbsoluteAdapterPosition()));
                        ContributorsModel contributorsModel = new ContributorsModel(newFriendList.get(position).getUser_id(),newFriendList.get(position).getFull_name(),newFriendList.get(position).getPhone_number(),newFriendList.get(position).getPhone_number(),"","","","","","gemift_contacts");
                        contributorsModel.setSelected(true);
                        contactslist.add(contributorsModel);

                    }else {
                       for(int i=0;i<contactslist.size();i++){
                          if(contactslist.get(i).getPhone_number().equals(newFriendList.get(position).getPhone_number())){

                              contactslist.remove(i);
                          }

                       }
                      }

//                }

            });

        }


        contactListBinding.appbar.imgDone.setOnClickListener(v -> {
            Intent objIntent = new Intent();
            objIntent.putExtra("data", contactslist);
            ((Activity) mContext).setResult(RESULT_OK, objIntent);
            ((Activity) mContext).finish();

        });

    }

    @Override
    public int getItemCount() {
        return newFriendList.size();
    }

    public void updateFriendSearch(List<FriendDataSearchModel> oldFriendList) {
        final SearchDiffUtil diffCallback = new SearchDiffUtil(this.newFriendList, oldFriendList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.newFriendList.clear();
        this.newFriendList.addAll(oldFriendList);
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SearchFriendViewBinding binding;

        public ViewHolder(@NonNull SearchFriendViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
