package com.gift.gemift.Ui.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gift.gemift.Model.Filter;
import com.gift.gemift.databinding.FilterItemBinding;
import com.gift.gemift.databinding.FilterValueItemBinding;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    private Context context;
    private HashMap<Integer, Filter> filters;
    private RecyclerView filterValuesRV;
    private int selectedPostion = 0;

    public FilterAdapter(Context context, HashMap<Integer, Filter> filters, RecyclerView filterValuesRV) {
        this.context = context;
        this.filters = filters;
        this.filterValuesRV = filterValuesRV;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new FilterAdapter.MyViewHolder(FilterItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterValuesRV.setAdapter(new FilterValuesAdapter(context, position));
                selectedPostion = position;
                notifyDataSetChanged();
            }
        });

        filterValuesRV.setAdapter(new FilterValuesAdapter(context, selectedPostion));
        myViewHolder.itemView.setBackgroundColor(selectedPostion == position ? Color.WHITE : Color.TRANSPARENT);
        myViewHolder.binding.title.setText(filters.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View container;
        private final FilterItemBinding binding;

        public MyViewHolder(@NonNull FilterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}

