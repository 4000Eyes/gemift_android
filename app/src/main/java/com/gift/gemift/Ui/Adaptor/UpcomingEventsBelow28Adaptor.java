package com.gift.gemift.Ui.Adaptor;

import static com.gift.gemift.Utils.Constant.CONTRIBUTOR_LIST;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.MyOccasionDataItems;
import com.gift.gemift.Storage.DB.Entity.FriendListEntity;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.OccasionDetails;
import com.gift.gemift.Ui.DashBoard.Fragments.Product.OccasionProducts;
import com.gift.gemift.databinding.UpcomingEventsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


public class UpcomingEventsBelow28Adaptor extends RecyclerView.Adapter<UpcomingEventsBelow28Adaptor.ViewHolder> {

    private final ArrayList<MyOccasionDataItems> occasionsModelArrayList;
    private final Context mcontext;
    private final HashMap<String, String> interestIdHashMap = new HashMap<>();

    public UpcomingEventsBelow28Adaptor(ArrayList<MyOccasionDataItems> occasionsModelArrayList, Context mcontext) {
        this.occasionsModelArrayList = occasionsModelArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public UpcomingEventsBelow28Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpcomingEventsBelow28Adaptor.ViewHolder(UpcomingEventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UpcomingEventsBelow28Adaptor.ViewHolder holder, int position) {
        String lastName = "";
        if (occasionsModelArrayList.get(position).getSecretFriendLastName() != null) {
            lastName = "" + occasionsModelArrayList.get(position).getSecretFriendLastName();
        }
        holder.binding.txtFriendName.setText(occasionsModelArrayList.get(position).getSecretFriendFirstName() + " " + lastName);
        holder.binding.txtOccasionName.setText(occasionsModelArrayList.get(position).getOccasionName());
        holder.binding.txtOccasionDayRemains.setText(occasionsModelArrayList.get(position).getDaysLeft()+" days away");
        holder.binding.txtOccasionDate.setText("On "+occasionsModelArrayList.get(position).getOccasionDate());
        holder.binding.txtOccasionStatus.setText(""+occasionsModelArrayList.get(position).getOccasionActiveStatus());

        holder.binding.txtGiftText.setVisibility(View.GONE);

        if(occasionsModelArrayList.get(position).getProductCount()==0){
            holder.binding.txtChoose.setVisibility(View.GONE);
        }else {
            holder.binding.txtChoose.setVisibility(View.VISIBLE);
            holder.binding.txtChoose.setText("Voted gifts("+occasionsModelArrayList.get(position).getProductCount()+")");

        }


        holder.binding.txtChoose.setOnClickListener(v -> {
            if(occasionsModelArrayList.get(position).getProductCount()==0){
                holder.binding.txtChoose.setVisibility(View.GONE);
            }else {
                holder.binding.txtChoose.setVisibility(View.VISIBLE);


                ArrayList<FriendListEntity> contributorList = new ArrayList<>();
                Intent i = new Intent(mcontext, OccasionDetails.class);
                i.putExtra(FRIEND_CIRCLE_ID, occasionsModelArrayList.get(position).getFriend_circle_id());
                i.putExtra(SECRET_FIRST_NAME,occasionsModelArrayList.get(position).getSecretFriendFirstName());
                i.putExtra(OCCASION_ID, occasionsModelArrayList.get(position).getOccasionId());
                i.putExtra(OCCASION_NAME, occasionsModelArrayList.get(position).getOccasionName());
                i.putExtra(CONTRIBUTOR_LIST, contributorList);
                i.putExtra(OCCASION_DATE, occasionsModelArrayList.get(position).getNextOccasionDate());
                mcontext.startActivity(i);
            }
        });

        holder.binding.txtPickup.setOnClickListener(view -> {
            Toast.makeText(mcontext, "upcoming", Toast.LENGTH_SHORT).show();

        });
        holder.binding.txtBuyGift.setOnClickListener(view -> {
            Gson gson = new Gson();
            Intent i = new Intent(mcontext, OccasionProducts.class);
            i.putExtra(FRIEND_CIRCLE_ID, occasionsModelArrayList.get(position).getFriend_circle_id());
            i.putExtra(OCCASION_ID, occasionsModelArrayList.get(position).getOccasionId());
            i.putExtra(OCCASION_NAME, occasionsModelArrayList.get(position).getOccasionName());
            i.putExtra(OCCASION_DATE, occasionsModelArrayList.get(position).getNextOccasionDate());
            i.putExtra("InterestList", gson.toJson(interestIdHashMap));
            mcontext.startActivity(i);
        });

        holder.itemView.setOnClickListener(v -> {
            ArrayList<FriendListEntity> contributorList = new ArrayList<>();

            Intent i = new Intent(mcontext, OccasionDetails.class);
            i.putExtra(FRIEND_CIRCLE_ID, occasionsModelArrayList.get(position).getFriend_circle_id());
            i.putExtra(SECRET_FIRST_NAME,occasionsModelArrayList.get(position).getSecretFriendFirstName());
            i.putExtra(OCCASION_ID, occasionsModelArrayList.get(position).getOccasionId());
            i.putExtra(OCCASION_NAME, occasionsModelArrayList.get(position).getOccasionName());
            i.putExtra(CONTRIBUTOR_LIST, contributorList);
            i.putExtra(OCCASION_DATE, occasionsModelArrayList.get(position).getNextOccasionDate());
            mcontext.startActivity(i);
        });

        if (!occasionsModelArrayList.get(position).getOccasionActiveStatus().equals("Active")){
            holder.binding.llSelectionBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return occasionsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final UpcomingEventsBinding binding;

        public ViewHolder(@NonNull UpcomingEventsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
