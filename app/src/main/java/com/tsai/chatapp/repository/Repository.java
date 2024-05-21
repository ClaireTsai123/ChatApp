package com.tsai.chatapp.repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tsai.chatapp.model.ChatGroup;
import com.tsai.chatapp.model.ChatMessage;
import com.tsai.chatapp.views.GroupsActivity;

import java.util.ArrayList;
import java.util.List;

//repository is a bridge between datasource and the rest of app
public class Repository {
    FirebaseDatabase db;
    // about chatgroup
    MutableLiveData<List<ChatGroup>> chatGroupMutableLiveData;
    DatabaseReference reference;

    //about chatmeassage
    MutableLiveData<List<ChatMessage>> chatMessageMutableLiveData;
    DatabaseReference groupReference;

    public Repository() {
       this.chatGroupMutableLiveData = new MutableLiveData<>();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();

        chatMessageMutableLiveData = new MutableLiveData<>();
    }

    //sign up
    public void firebaseAnonymousAuth(Context context) {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //authentication is successful;
                            // switch to new activity(GroupsActivity)
                            Intent i = new Intent(context, GroupsActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }
                }
        );
    }

    //SignOut
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    //Get current userId
    public String getCurrentUseId() {
        return FirebaseAuth.getInstance().getUid();
    }

    //get chatgroups from firebase realtime db
    public MutableLiveData<List<ChatGroup>> getChatGroupMutableLiveData() {
        List<ChatGroup> groups = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groups.clear();//avoid the duplicate
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatGroup chatGroup = new ChatGroup(dataSnapshot.getKey());
                    groups.add(chatGroup);
                }
                chatGroupMutableLiveData.postValue(groups);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatGroupMutableLiveData;
    }

    //creating a new chatgroup into firebase
    public void createNewChatGroup(String name) {
        reference.child(name).setValue(name);
    }

    //getting chatmessages from realtime db

    public MutableLiveData<List<ChatMessage>> getChatMessageMutableLiveData(String groupName) {

        //child(groupname): used to specify a child node under the root reference
        groupReference = db.getReference().child(groupName);
        List<ChatMessage> chatMessages = new ArrayList<>();
        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatMessages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                        chatMessages.add(message);

                }
                chatMessageMutableLiveData.postValue(chatMessages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DataError", "DatabaseError: " + error.getMessage());
            }
        });
        return chatMessageMutableLiveData;
    }


    public void sendMessage(String msg, String groupName) {
        DatabaseReference ref = db.getReference(groupName);

        if (!msg.trim().equals("")) {
            ChatMessage newMsg = new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    msg,
                    System.currentTimeMillis()
            );
            //push to database
            String randomKey = ref.push().getKey();
            ref.child(randomKey).setValue(newMsg);
        }

    }
}
