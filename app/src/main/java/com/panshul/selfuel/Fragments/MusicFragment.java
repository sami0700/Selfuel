package com.panshul.selfuel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.selfuel.Activity.Profile_Page;
import com.panshul.selfuel.Adapters.SongsAdapter;
import com.panshul.selfuel.Model.PlaylistModel;
import com.panshul.selfuel.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {

    View view;
    List<PlaylistModel> playList;
    RecyclerView recyclerView;
    ImageView plus,profile;
    SharedPreferences pref;
    String dataEntered;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_music, container, false);

        dataEntered="false";
        playList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.playListRecyclerView);
        plus = view.findViewById(R.id.plusPlaylist);
        profile = view.findViewById(R.id.profile5);
        onClickListeners();

        pref = view.getContext().getSharedPreferences("com.panshul.selfuel.music",Context.MODE_PRIVATE);
        editor = pref.edit();
        dataEntered = pref.getString("dataEntered","false");


        loadData();
        addData();
        saveData();

        adapter();
        saveData();
        return view;
    }
    public void onClickListeners(){
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Playlist_Fragment fragment = new Add_Playlist_Fragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
        if (playList.isEmpty()) {
            playList.add(new PlaylistModel("Study Music 2021",
                    "https://open.spotify.com/playlist/471N195f5jAVs086lzYglw?si=krfad_Y0QK6v89iMgnwPog",
                    "Playlist by Naeleck"));
            playList.add(new PlaylistModel("Relaxing Songs",
                    "https://open.spotify.com/playlist/4D3hxAbOjVu5jaC5Bnlmky?si=MDSki9M5SCqA-z0hn6RRQw&nd=1",
                    "Playlist by Iyssastreiner"));
            playList.add(new PlaylistModel("Work From Home Hits",
                    "https://open.spotify.com/playlist/37i9dQZF1DX6dlXjpHAMw3?si=FO0qAxdYSBamdmcwRaOG_A&nd=1",
                    "Playlist by Spotify"));
            playList.add(new PlaylistModel("White Noise For Study",
                    "https://open.spotify.com/playlist/1WO1CAQq3FI6oeUkuXVW61?si=kKNb4UwOSsi7biSMkci04w&nd=1",
                    "Playlist by Evan Newman"));
        }

    }
    public void adapter(){
        SongsAdapter adapter = new SongsAdapter(view.getContext(),playList,MusicFragment.this);
        GridLayoutManager manager = new GridLayoutManager(view.getContext(),2);
        //LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
       // manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void saveData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.spotifyList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(playList);
        //Log.i("String",json);
        editor.putString("songs",json);
        editor.apply();
    }
    public void loadData(){
            String input = "";
            SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.spotifyList", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = preferences.getString("songs", "");
            Type type = new TypeToken<ArrayList<PlaylistModel>>() {}.getType();
            playList = gson.fromJson(json, type);
            if (playList == null) {
                playList = new ArrayList<>();
            }
    }
}