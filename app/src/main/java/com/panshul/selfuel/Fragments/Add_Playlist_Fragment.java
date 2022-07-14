package com.panshul.selfuel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.selfuel.Activity.Profile_Page;
import com.panshul.selfuel.Model.PlaylistModel;
import com.panshul.selfuel.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Add_Playlist_Fragment extends Fragment {

    View view;
    EditText playListName,playListLink;
    List<PlaylistModel> playList;
    ImageView cancel,profile;
    String name;
    Button addPlaylist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_add__playlist_, container, false);

       playListName = view.findViewById(R.id.playListName);
       playListLink = view.findViewById(R.id.playListLink);
       addPlaylist = view.findViewById(R.id.addPlaylist);
       cancel = view.findViewById(R.id.deleteImageViewPlaylist);
       profile = view.findViewById(R.id.profile6);

       SharedPreferences pref = view.getContext().getSharedPreferences("com.panshul.selfuel.userdata",Context.MODE_PRIVATE);
       name = pref.getString("name","");

       loadData();
       onClickListener();
       return view;
    }
    public void onClickListener(){
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()){
                    playList.add(new PlaylistModel(playListName.getText().toString(),
                            playListLink.getText().toString(),"Playlist by "+name));
                    saveData();
                    Toast.makeText(v.getContext(), "Playlist Added", Toast.LENGTH_SHORT).show();
                    MusicFragment fragment = new MusicFragment();
                    FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frameLayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Profile_Page.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicFragment fragment = new MusicFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    public boolean checkEmpty(){
        if(playListName.getText().toString().length()==0){
            Toast.makeText(view.getContext(), "Please enter the Playlist name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(playListLink.getText().toString().length()==0){
            Toast.makeText(view.getContext(), "Please enter the Playlist Link", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void saveData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.spotifyList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(playList);
        editor.putString("songs",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.spotifyList",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("songs","");
        Type type = new TypeToken<ArrayList<PlaylistModel>>() {}.getType();
        playList =gson.fromJson(json,type);
        if(playList==null){
            playList =new ArrayList<>();
        }
    }
}