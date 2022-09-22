package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.ContactsInfo;
import com.gift.gemift.Model.ContributorsModel;
import com.gift.gemift.Model.FriendDataSearchModel;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.databinding.ContactListBinding;
import com.gift.gemift.databinding.ContactListViewBinding;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.gift.gemift.Utils.Constant.GEMIFT_SECRETFRIEND;
import static com.gift.gemift.Utils.Constant.PH_SECRETFRIEND;


public class ContactAdaptor extends RecyclerView.Adapter<ContactAdaptor.ViewHolder> implements Filterable {

    private final List<ContactsInfo> contactsInfos;
    private final Context context;
    AppPreferences appPreferences;
    boolean isSelectedMode = false;
    ContactListBinding contactListBinding;
    ArrayList<ContactsInfo> selectedItems = new ArrayList<ContactsInfo>();
    private ArrayList<ContactsInfo> fullContactsdetails;
    ArrayList<ContributorsModel> contactslist = new ArrayList<>();

    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ContactsInfo> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullContactsdetails);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ContactsInfo item : fullContactsdetails) {
                    if (item.getDisplayName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactsInfos.clear();
            contactsInfos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public ContactAdaptor(List<ContactsInfo> contactsInfos, Context context, ContactListBinding binding) {
        this.contactsInfos = contactsInfos;
        this.context = context;
        this.contactListBinding = binding;
        this.fullContactsdetails = new ArrayList<>(contactsInfos);
    }
    @NonNull
    @Override
    public ContactAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        appPreferences = new AppPreferences(context);
        return new ViewHolder(ContactListViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ContactAdaptor.ViewHolder holder, int position) {

        holder.binding.txtContactName.setText(contactsInfos.get(position).getDisplayName());

        if(!contactsInfos.get(position).getCountryCode().equals("")){
            holder.binding.txtContactPhone.setText("+"+contactsInfos.get(position).getCountryCode()+" "+contactsInfos.get(position).getPhoneNumber());
        }else {
            holder.binding.txtContactPhone.setText(contactsInfos.get(position).getPhoneNumber());

        }

        holder.itemView.setBackgroundColor(contactsInfos.get(position).isSelected() ? Color.GRAY : Color.WHITE);

        if (appPreferences.getIdentifier().equals(GEMIFT_SECRETFRIEND) || appPreferences.getIdentifier().equals(PH_SECRETFRIEND)) {

            holder.itemView.setOnClickListener(v -> {
//                contactListBinding.appbar.imgDone.setVisibility(View.VISIBLE);
                if (selectedItems.contains(contactsInfos.get(holder.getAbsoluteAdapterPosition()))) {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    selectedItems.remove(contactsInfos.get(holder.getAbsoluteAdapterPosition()));
                    ContributorsModel contributorsModel = new ContributorsModel("",contactsInfos.get(position).getDisplayName(),"",contactsInfos.get(position).getPhoneNumber(),"","","","","","phonecontacts");
                    contributorsModel.setCountry_code(contactsInfos.get(position).getCountryCode());
                    contactslist.remove(contributorsModel);
                } else {
                       holder.itemView.setBackgroundColor(R.color.colorPrimary);
                       selectedItems.add(contactsInfos.get(holder.getAbsoluteAdapterPosition()));

                       String lastName = "";
                       String firstName= "";
                       if(contactsInfos.get(position).getDisplayName().contains(" ")){
                           String name = contactsInfos.get(position).getDisplayName().trim();
                           if(name.split("\\w+").length>1){

                               lastName = name.substring(name.lastIndexOf(" ")+1);
                               firstName = name.substring(0, name.lastIndexOf(' '));
                           }
                           else{
                               firstName = name;
                           }
                       }else {
                           firstName = contactsInfos.get(position).getDisplayName().trim();
                       }

                       ContributorsModel contributorsModel = new ContributorsModel("",contactsInfos.get(position).getDisplayName(),"",contactsInfos.get(position).getPhoneNumber(),"",firstName,lastName,"","","phonecontacts");
                       contributorsModel.setCountry_code(contactsInfos.get(position).getCountryCode());
                       contactslist.add(contributorsModel);

                }
                Intent objIntent = new Intent();
                objIntent.putExtra("data", contactslist);
                ((Activity) context).setResult(RESULT_OK, objIntent);
                ((Activity) context).finish();


            });
        } else {


            holder.itemView.setOnClickListener(v -> {
                if(contactsInfos.get(holder.getAbsoluteAdapterPosition()).isSelected()){
                    contactsInfos.get(holder.getAbsoluteAdapterPosition()).setSelected(false);
                }else {
                    contactsInfos.get(holder.getAbsoluteAdapterPosition()).setSelected(true);
                }

                contactListBinding.appbar.imgDone.setVisibility(View.VISIBLE);

                        String lastName = "";
                        String firstName= "";
                        if(contactsInfos.get(position).getDisplayName().contains(" ")){
                           String name = contactsInfos.get(position).getDisplayName().trim();
                           if(name.split("\\w+").length>1){

                               lastName = name.substring(name.lastIndexOf(" ")+1);
                               firstName = name.substring(0, name.lastIndexOf(' '));
                           }
                           else{
                               firstName = name;
                           }
                       }else {
                            firstName = contactsInfos.get(position).getDisplayName().trim();
                        }

                if(contactsInfos.get(position).isSelected()){
                          if (!contactsInfos.get(position).getCountryCode().isEmpty()){
                              holder.itemView.setBackgroundColor(contactsInfos.get(holder.getAbsoluteAdapterPosition()).isSelected() ? Color.GRAY : Color.WHITE);

                              selectedItems.add(contactsInfos.get(holder.getAbsoluteAdapterPosition()));
                              ContributorsModel contributorsModel = new ContributorsModel("",contactsInfos.get(position).getDisplayName(),"",contactsInfos.get(position).getPhoneNumber(),"",firstName,lastName,"","","phone_contacts");
                              contributorsModel.setCountry_code(contactsInfos.get(position).getCountryCode());
                              contactslist.add(contributorsModel);
                          }else {
                              showAlert(position, holder,firstName,lastName);
                          }

                }else {

                    holder.itemView.setBackgroundColor(contactsInfos.get(holder.getAbsoluteAdapterPosition()).isSelected() ? Color.GRAY : Color.WHITE);


                    for(int i=0;i<contactslist.size();i++){
                        if(contactslist.get(i).getPhone_number().equals(contactsInfos.get(position).getPhoneNumber())){
                            contactslist.remove(i);
                        }
                    }
                }
            });
        }


        contactListBinding.appbar.imgDone.setOnClickListener(v -> {

            Intent objIntent = new Intent();
            objIntent.putExtra("data", contactslist);
            ((Activity) context).setResult(RESULT_OK, objIntent);
            ((Activity) context).finish();

        });

    }

    private void showAlert(int position, ViewHolder holder, String firstName, String lastName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View layout = LayoutInflater.from(context).inflate(R.layout.layout_country, null);
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
        Button submit = layout.findViewById(R.id.submit);
        CountryCodePicker ccp = layout.findViewById(R.id.ccp);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        submit.setOnClickListener(view -> {
            ccp.getSelectedCountryCode();

            holder.itemView.setBackgroundColor(contactsInfos.get(holder.getAbsoluteAdapterPosition()).isSelected() ? Color.GRAY : Color.WHITE);

            selectedItems.add(contactsInfos.get(holder.getAbsoluteAdapterPosition()));
            ContributorsModel contributorsModel = new ContributorsModel("",contactsInfos.get(position).getDisplayName(),"",contactsInfos.get(position).getPhoneNumber(),"",firstName,lastName,"","","phone_contacts");
            contributorsModel.setCountry_code(ccp.getSelectedCountryCode());
            contactslist.add(contributorsModel);
            alertDialog.dismiss();
        });
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    @Override
    public int getItemCount() {
        return contactsInfos.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ContactListViewBinding binding;

        public ViewHolder(@NonNull ContactListViewBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
