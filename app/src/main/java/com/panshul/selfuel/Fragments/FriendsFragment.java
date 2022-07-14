package com.panshul.selfuel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.selfuel.Activity.Profile_Page;
import com.panshul.selfuel.Adapters.FriendAdapter;
import com.panshul.selfuel.Model.FriendsModel;
import com.panshul.selfuel.Model.UidModel;
import com.panshul.selfuel.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    View view;
    List<FriendsModel> friendsList;
    RecyclerView recyclerView;
    SharedPreferences pref;
    List<String> uids;
    DatabaseReference myref,myref1;
    ImageView add,profile;
    ConstraintLayout ui1;
    TextView text1,text2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_friends, container, false);
        uids = new ArrayList<>();
        friendsList = new ArrayList<>();

        ui1 = view.findViewById(R.id.emptyFriendsLayout);
        text1 = view.findViewById(R.id.fragfrndList);
        text2 = view.findViewById(R.id.fragfrndListContent);
        recyclerView = view.findViewById(R.id.friendRecyclerView);
        add = view.findViewById(R.id.friendsAddImageview);
        profile = view.findViewById(R.id.profile7);
        pref = view.getContext().getSharedPreferences("com.panshul.selfuel.userdata", Context.MODE_PRIVATE);



        onClickListeners();
        loadData();
        addData();
        checkData();
        adapter();



        return view;
    }
    public void checkData(){
        loadData();
        if (friendsList.isEmpty()){
            ui1.setVisibility(View.VISIBLE);
            text1.setVisibility(View.INVISIBLE);
            text2.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            ui1.setVisibility(View.INVISIBLE);
            text1.setVisibility(View.VISIBLE);
            text2.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter();
        }

    }
    public void onClickListeners(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog();
                dialog.show( getFragmentManager(),"fragment");
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Profile_Page.class);
                startActivity(intent);
            }
        });

    }
    public void addData(){
        String uid = pref.getString("uid","");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Room").child(uid).child("friends");
        myref1 = db.getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uids.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    UidModel model = ds.getValue(UidModel.class);
                    String uid1 = model.getUid();
                    uids.add(uid1);
                }
                myref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friendsList.clear();
                        for (String uid2:uids){
                            String name = snapshot.child(uid2).child("name").getValue().toString();
                            String score = snapshot.child(uid2).child("points").getValue().toString();
                            friendsList.add(new FriendsModel(name,score,uid2));
                        }
                        saveData();
                        adapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter(){
        FriendAdapter adapter = new FriendAdapter(view.getContext(),friendsList,FriendsFragment.this);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void saveData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.friends", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friendsList);
        editor.putString("friendList",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.friends",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("friendList","");
        Type type = new TypeToken<ArrayList<FriendsModel>>() {}.getType();
        friendsList =gson.fromJson(json,type);
        if(friendsList==null){
            friendsList =new ArrayList<>();
        }
    }
}