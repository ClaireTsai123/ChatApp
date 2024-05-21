package com.tsai.chatapp.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tsai.chatapp.BR;
import com.tsai.chatapp.R;
import com.tsai.chatapp.databinding.RowChatBinding;
import com.tsai.chatapp.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatMessage> messages;
    private Context context;

    public ChatAdapter(List<ChatMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_chat, parent,false);
        RowChatBinding binding = DataBindingUtil.bind(view);
        return new ChatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
       holder.getRowChatBinding().setVariable(BR.chatMessage, messages.get(position));
       holder.getRowChatBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
       private RowChatBinding rowChatBinding;

        public ChatViewHolder( RowChatBinding binding) {
            super(binding.getRoot());
            setRowChatBinding(binding);
        }

        public void setRowChatBinding(RowChatBinding rowChatBinding) {
            this.rowChatBinding = rowChatBinding;
        }

        public RowChatBinding getRowChatBinding() {
            return rowChatBinding;
        }
    }

}
