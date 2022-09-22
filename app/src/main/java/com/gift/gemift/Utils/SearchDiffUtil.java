package com.gift.gemift.Utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.gift.gemift.Model.FriendDataSearchModel;

import java.util.ArrayList;
import java.util.List;

public class SearchDiffUtil extends DiffUtil.Callback {
    private final List<FriendDataSearchModel> oldFriendList;
    private final List<FriendDataSearchModel> newFriendList;

    public SearchDiffUtil(List<FriendDataSearchModel> oldFriendList, List<FriendDataSearchModel> newFriendList) {
        this.oldFriendList = oldFriendList;
        this.newFriendList = newFriendList;
    }

    @Override
    public int getOldListSize() {
        return oldFriendList.size();
    }

    @Override
    public int getNewListSize() {
        return newFriendList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFriendList.get(oldItemPosition).getUser_id().equals(newFriendList.get(newItemPosition).getUser_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (!oldFriendList.get(oldItemPosition).getUser_id().equals(newFriendList.get(newItemPosition).getUser_id())) {
            return false;
        } else
            return (!oldFriendList.get(oldItemPosition).getPhone_number().equals(newFriendList.get(newItemPosition).getPhone_number()));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
