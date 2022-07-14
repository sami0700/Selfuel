package com.panshul.selfuel.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.selfuel.R;

import java.util.ArrayList;
import java.util.List;

public class Dialog extends DialogFragment {
    Button add,cancel;
    EditText code;
    DatabaseReference myref,myref1,myref2;
    View view;
    String uid,uid1;
    List<String> uids,users;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frnd_code,container,false);

        uids = new ArrayList<>();
        users = new ArrayList<>();

        code = view.findViewById(R.id.editTextTextFrndCode);
        add = view.findViewById(R.id.frndCodeAddFrnd);
        cancel = view.findViewById(R.id.frndCodeCancel);

        SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.selfuel.userdata", Context.MODE_PRIVATE);
        uid = pref.getString("uid","");
        //Log.i("uid",uid);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Room").child(uid).child("friends");
        myref2 = db.getReference("Room");
        myref1 = db.getReference("Users");



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()){
                    add.setEnabled(false);
                    uid1 = code.getText().toString();
                    myref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            uids.clear();
                            for (DataSnapshot ds:snapshot.getChildren()){
                                String uid2 = ds.child("uid").getValue().toString();
                                uids.add(uid2);
                            }
                            if (uids.contains(uid1)){
                                add.setEnabled(true);
                                Toast.makeText(v.getContext(), "Already a friend", Toast.LENGTH_SHORT).show();
                                getDialog().dismiss();
                            }
                            else {
                                myref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        users.clear();
                                        for (DataSnapshot ds:snapshot.getChildren()){
                                            String userid = ds.child("uid").getValue().toString();
                                            users.add(userid);
                                        }
                                        if (!users.contains(uid1)){
                                            add.setEnabled(true);
                                            Toast.makeText(v.getContext(), "User not Found. Enter a correct User ID", Toast.LENGTH_SHORT).show();
                                            getDialog().dismiss();
                                        }else {
                                            add.setEnabled(true);
                                            myref2.child(uid).child("friends").child(uid1).child("uid").setValue(uid1);
                                            myref2.child(uid1).child("friends").child(uid).child("uid").setValue(uid);
                                            Toast.makeText(v.getContext(), "Friend Added", Toast.LENGTH_SHORT).show();

                                            getDialog().dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        return view;
    }
    boolean checkEmpty(){
        if(code.getText().toString().length()==0){
            Toast.makeText(view.getContext(), "Please enter a Code", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
