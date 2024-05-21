package com.tsai.chatapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tsai.chatapp.R;
import com.tsai.chatapp.databinding.ActivityChatBinding;
import com.tsai.chatapp.model.ChatMessage;
import com.tsai.chatapp.viewmodel.MyViewModel;
import com.tsai.chatapp.views.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private List<ChatMessage> chatMessageList;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private MyViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //getting group name from the GroupAdapter click
        String groupName = getIntent().getStringExtra("group_name");
        viewModel.getChatMessages(groupName).observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                chatMessageList = new ArrayList<>();
                chatMessageList.addAll(chatMessages);
//                adapter = new ChatAdapter(chatMessageList, ChatActivity.this);
                adapter = new ChatAdapter(chatMessageList, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                //scroll to the latest message added
                int latestPosition = adapter.getItemCount() - 1;
                if (latestPosition > 0) {
                    recyclerView.smoothScrollToPosition(latestPosition);
                }
            }
        });

        binding.setVModel(viewModel);
        binding.sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = binding.edittextChatMessage.getText().toString();
                viewModel.sendMessage(msg, groupName);
                binding.edittextChatMessage.getText().clear();
            }
        });
    }
}