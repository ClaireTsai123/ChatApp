package com.tsai.chatapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.tsai.chatapp.R;
import com.tsai.chatapp.databinding.ActivityLoginBinding;
import com.tsai.chatapp.viewmodel.MyViewModel;

public class LoginActivity extends AppCompatActivity {
    MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //initial ActivityLoginBinding
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        //data binding
        binding.setMyVModel(viewModel);
    }
}