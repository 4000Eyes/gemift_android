package com.gift.gemift.Ui.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.gift.gemift.Model.Filter;
import com.gift.gemift.Storage.AppPreference.Preferences;
import com.gift.gemift.databinding.FilterValueItemBinding;
import com.gift.gemift.databinding.FriendListViewBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterValuesAdapter extends RecyclerView.Adapter<FilterValuesAdapter.MyViewHolder> {

    private Context context;
    private Integer filterIndex;

    public FilterValuesAdapter(Context context, Integer filterIndex) {
        this.context = context;
        this.filterIndex = filterIndex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new FilterValuesAdapter.MyViewHolder(FilterValueItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        final Filter tmpFilter = Preferences.filters.get(filterIndex);
        myViewHolder.binding.value.setOnClickListener(v -> {
            List<String> selected = tmpFilter.getSelected();
            if(myViewHolder.binding.value.isChecked()) {
                selected.add(tmpFilter.getValues().get(position));
                tmpFilter.setSelected(selected);
            } else {
                selected.remove(tmpFilter.getValues().get(position));
                tmpFilter.setSelected(selected);
            }
            Preferences.filters.put(filterIndex, tmpFilter);
        });
        myViewHolder.binding.value.setText(tmpFilter.getValues().get(position));
        if(tmpFilter.getSelected().contains(tmpFilter.getValues().get(position))) {
            myViewHolder.binding.value.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return Preferences.filters.get(filterIndex).getValues().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final FilterValueItemBinding binding;

        public MyViewHolder(@NonNull FilterValueItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
