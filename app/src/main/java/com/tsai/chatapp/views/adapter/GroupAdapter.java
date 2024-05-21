package com.tsai.chatapp.views.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tsai.chatapp.R;
import com.tsai.chatapp.databinding.GroupItemBinding;
import com.tsai.chatapp.model.ChatGroup;
import com.tsai.chatapp.views.ChatActivity;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<ChatGroup> groups;

    public GroupAdapter(List<ChatGroup> groups) {
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.group_item,
                parent,
                false
        );
        return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        //binding datas to an existing viewholder
        ChatGroup currentGroup = groups.get(position);
        holder.groupItemBinding.setChatGroup(currentGroup);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private GroupItemBinding groupItemBinding;

        public GroupViewHolder(GroupItemBinding groupItemBinding) {
            super(groupItemBinding.getRoot());
            this.groupItemBinding = groupItemBinding;

            groupItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ChatGroup chatGroup = groups.get(position);
                    Intent i = new Intent(v.getContext(), ChatActivity.class);
                    i.putExtra("group_name", chatGroup.getGroupName());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
