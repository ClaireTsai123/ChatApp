package com.tsai.chatapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tsai.chatapp.model.ChatGroup;
import com.tsai.chatapp.model.ChatMessage;
import com.tsai.chatapp.repository.Repository;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    Repository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    //Auth sign up
    public void signUpAnonymously() {
        repository.firebaseAnonymousAuth(getApplication());
    }

    //get currentUId
    public String getCurrentUid() {
        return repository.getCurrentUseId();
    }

    //sign out
    public void signOut() {
        repository.signOut();
    }

    //Get groups list
    public MutableLiveData<List<ChatGroup>> getChatGroupList() {
        return repository.getChatGroupMutableLiveData();
    }

    public void createNewChatGroup(String groupName) {
        repository.createNewChatGroup(groupName);
    }

    //get message list
    public MutableLiveData<List<ChatMessage>> getChatMessages(String groupName) {
        return repository.getChatMessageMutableLiveData(groupName);
    }


    public void sendMessage(String msg, String groupName) {
        repository.sendMessage(msg,groupName);
    }
}
