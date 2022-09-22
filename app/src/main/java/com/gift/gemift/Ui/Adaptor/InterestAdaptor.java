package com.gift.gemift.Ui.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gift.gemift.Model.InterestList;
import com.gift.gemift.R;
import com.gift.gemift.databinding.InterestListviewBinding;

import java.util.ArrayList;
import java.util.List;

public class InterestAdaptor extends RecyclerView.Adapter<InterestAdaptor.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<InterestList> interetList;
    private ArrayList<InterestList> filterInterestList;
    private ItemFilter mFilter = new ItemFilter();


    public InterestAdaptor(ArrayList<InterestList> interestList, Context context) {
        this.filterInterestList = interestList;
        this.interetList = interestList;
        this.context = context;
    }

    @NonNull
    @Override
    public InterestAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(InterestListviewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull InterestAdaptor.ViewHolder holder, int position) {

        holder.binding.txtInterest.setText(filterInterestList.get(position).getWeb_category_name());


        holder.binding.txtInterest.setBackground(filterInterestList.get(position).isSelected() ? ContextCompat.getDrawable(context, R.drawable.greenedittext) : ContextCompat.getDrawable(context, R.drawable.shapeedittext));

        holder.itemView.setOnClickListener(view -> {
            filterInterestList.get(holder.getAbsoluteAdapterPosition()).setSelected(!filterInterestList.get(holder.getAbsoluteAdapterPosition()).isSelected());
            holder.binding.txtInterest.setBackground(filterInterestList.get(holder.getAbsoluteAdapterPosition()).isSelected() ? ContextCompat.getDrawable(context, R.drawable.greenedittext) : ContextCompat.getDrawable(context, R.drawable.shapeedittext));
            if (filterInterestList.get(holder.getAbsoluteAdapterPosition()).isSelected()) {
                InterestList interestList1 = new InterestList();
                interestList1.setWeb_category_id(filterInterestList.get(holder.getAbsoluteAdapterPosition()).getWeb_category_id());
            }
        });

    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public int getItemCount() {
        return filterInterestList.size();
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                final ArrayList<InterestList> nlist = interetList;
                results.values = nlist;
                results.count = nlist.size();
            } else {
                String filterString = constraint.toString().toLowerCase();
                final ArrayList<InterestList> filteringArray = new ArrayList<>();
                filteringArray.addAll(interetList);
                final List<InterestList> list = filteringArray;
                int count = list.size();
                final ArrayList<InterestList> nlist = new ArrayList<>(count);
                String filterableString;
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).getWeb_category_name();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.add(list.get(i));
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }


            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<InterestList> values = (ArrayList<InterestList>) results.values;
            filterInterestList = values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final InterestListviewBinding binding;

        public ViewHolder(@NonNull InterestListviewBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
