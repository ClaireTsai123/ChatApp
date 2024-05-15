package com.tsai.chatapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsai.chatapp.R;
import com.tsai.chatapp.databinding.ActivityGroupsBinding;
import com.tsai.chatapp.model.ChatGroup;
import com.tsai.chatapp.viewmodel.MyViewModel;
import com.tsai.chatapp.views.adapter.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {
    private List<ChatGroup> chatGroupList;
    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private ActivityGroupsBinding binding;
    private MyViewModel viewModel;

    private Dialog createGroupdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_groups);

        recyclerView = binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        viewModel.getChatGroupList().observe(this, new Observer<List<ChatGroup>>() {
            @Override
            public void onChanged(List<ChatGroup> chatGroups) {
                chatGroupList = new ArrayList<>();
                chatGroupList.addAll(chatGroups);
                groupAdapter = new GroupAdapter(chatGroupList);
                recyclerView.setAdapter(groupAdapter);
                groupAdapter.notifyDataSetChanged();

            }
        });

        binding.ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateChartGroupDialog();
            }
        });
    }

    private void showCreateChartGroupDialog() {
        createGroupdialog = new Dialog(this);


        View view = LayoutInflater.from(this).inflate(R.layout.chatgroup_create_layout,null);
        createGroupdialog.setContentView(view);
        createGroupdialog.show();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(createGroupdialog.getWindow().getAttributes());

        // setting width to 90% of display
        layoutParams.width = (int) (displayMetrics.widthPixels * 0.9f);

        // setting height to 90% of display
        layoutParams.height = (int) (displayMetrics.heightPixels * 0.9f);
        createGroupdialog.getWindow().setAttributes(layoutParams);

        FloatingActionButton create_btn = view.findViewById(R.id.btn_created);
        EditText name = view.findViewById(R.id.et_groupName);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = name.getText().toString();
                Toast.makeText(GroupsActivity.this,"Your chat group: " + groupName, Toast.LENGTH_SHORT).show();

                viewModel.createNewChatGroup(groupName);
                createGroupdialog.dismiss();
            }
        });
    }
}