package com.panshul.selfuel.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.selfuel.Fragments.FriendsFragment;
import com.panshul.selfuel.Model.FriendsModel;
import com.panshul.selfuel.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {

    Context mcontext;
    List<FriendsModel> list1;
    String uid;
    FriendsFragment fragment;

    public FriendAdapter(Context mcontext, List<FriendsModel> list1, FriendsFragment fragment) {
        this.mcontext = mcontext;
        this.list1 = list1;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,score;
        ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.frndName);
            score = itemView.findViewById(R.id.noOfSessions);
            delete = itemView.findViewById(R.id.friendDeleteImage);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.friend_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FriendsModel item = list1.get(position);
        holder.name.setText(item.getName());
        holder.score.setText(item.getScore());
        SharedPreferences pref = mcontext.getSharedPreferences("com.panshul.selfuel.userdata",Context.MODE_PRIVATE);
        uid = pref.getString("uid","");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference myref = db.getReference("Room");

                myref.child(uid).child("friends").child(item.getFriendId()).child("uid").removeValue();
                myref.child(item.getFriendId()).child("friends").child(uid).child("uid").removeValue();
                saveData();
                fragment.checkData();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list1.size();
    }
    public void saveData(){
        SharedPreferences preferences = mcontext.getSharedPreferences("com.panshul.selfuel.friends", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        editor.putString("friendList",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = mcontext.getSharedPreferences("com.panshul.selfuel.friends",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("friendList","");
        Type type = new TypeToken<ArrayList<FriendsModel>>() {}.getType();
        list1 =gson.fromJson(json,type);
        if(list1==null){
            list1 =new ArrayList<>();
        }
    }


}
