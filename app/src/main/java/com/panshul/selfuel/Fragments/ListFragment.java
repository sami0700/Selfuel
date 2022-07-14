package com.panshul.selfuel.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.selfuel.Activity.Profile_Page;
import com.panshul.selfuel.Adapters.TaskAdapter;
import com.panshul.selfuel.Model.TaskModel;
import com.panshul.selfuel.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    public static List<TaskModel> taskList;
    ImageView add,profile;
    ConstraintLayout ui1;
    CardView ui2;
    Button emptyButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.taskRecyclerView);
        add = view.findViewById(R.id.additionImageView);
        ui1 = view.findViewById(R.id.clockEmptyLayout3);
        ui2=view.findViewById(R.id.listFragmentCardView);
        profile = view.findViewById(R.id.profile1);
        emptyButton = view.findViewById(R.id.emptyTaskButton);
        onClickListeners();
        taskList=new ArrayList<>();
        //addData();
        loadData();
        checkData();
        adapter();
        saveData();
        return view;
    }
    public void checkData(){
        if (taskList.isEmpty()){
            //ui2.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            ui1.setVisibility(View.VISIBLE);
        }else {
            ui1.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            //ui2.setVisibility(View.VISIBLE);
        }
    }
    public void addData(){
        taskList.add(new TaskModel("Task Name","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Amet amet, morbi aliquam nisl fermentum volutpat " +
                "eleifend id donec. In velit posuere interdum quam volutpat.",
        50,"true","nhuszbvdijgohengmsknf"));
        taskList.add(new TaskModel("Task Name","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Amet amet, morbi aliquam nisl fermentum volutpat " +
                "eleifend id donec. In velit posuere interdum quam volutpat.",
                50,"true","nhuszbvdiswhtfemsknf"));
        taskList.add(new TaskModel("Task Name","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Amet amet, morbi aliquam nisl fermentum volutpat " +
                "eleifend id donec. In velit posuere interdum quam volutpat.",
                50,"true","nrbhfswgjnsmzkbglohengmsknf"));
        taskList.add(new TaskModel("Task Name","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Amet amet, morbi aliquam nisl fermentum volutpat " +
                "eleifend id donec. In velit posuere interdum quam volutpat.",
                50,"true","nhuszbvdidciugvmsknf"));
        taskList.add(new TaskModel("Task Name","Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Amet amet, morbi aliquam nisl fermentum volutpat " +
                "eleifend id donec. In velit posuere interdum quam volutpat.",
                50,"true","nhuszbefhujohengmsknf"));

    }
    public void adapter(){
        TaskAdapter adapter = new TaskAdapter(view.getContext(),taskList,ListFragment.this);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        checkData();
    }
    public void onClickListeners(){
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Profile_Page.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFragment fragment = new TaskFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFragment fragment = new TaskFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    public void saveData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.tasklist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("taskList",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.tasklist",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("taskList","");
        Type type = new TypeToken<ArrayList<TaskModel>>() {}.getType();
        taskList =gson.fromJson(json,type);
        if(taskList==null){
            taskList =new ArrayList<>();
        }
    }
}