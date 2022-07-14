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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.selfuel.Activity.Profile_Page;
import com.panshul.selfuel.Model.TaskModel;
import com.panshul.selfuel.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskFragment extends Fragment {
    View view;
    ImageView delete,profile;
    Button subtraction,addition,schedule;
    List<TaskModel> taskList;
    EditText name,content;
    TextView minutes;
    int index;
    public static List<TaskModel> list1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_task, container, false);
        index=-1;

        loadData();
        findViewByIds();
        checkData();
        onClickListeners();
        return view;
    }
    public void checkData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.taskId",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String taskId=preferences.getString("taskSpecificId","");
        if (taskId.length()==0){
           //
            // Log.i("TaskId",taskId);
            content.setText("");
            name.setText("");
        }
        else {
            //Log.i("TaskId2",taskId);
            for (int i=0;i<list1.size();i++){
                //Log.i("taskIds",list1.get(i).getTaskId());
                if (taskId.equals(list1.get(i).getTaskId())){
                    index=i;
                    //Log.i("Index",String.valueOf(i));
                    name.setText(list1.get(i).getTaskName().toString());
                    content.setText(list1.get(i).getTaskContent().toString());
                    editor.putString("taskSpecificId","");
                    editor.apply();
                    break;
                }
            }
        }
    }
    public void findViewByIds(){
        delete = view.findViewById(R.id.deleteImageView);
        profile = view.findViewById(R.id.profile3);
        subtraction = view.findViewById(R.id.subtractionButton);
        addition=view.findViewById(R.id.additionButton);
        name = view.findViewById(R.id.editTextTextTaskName);
        content = view.findViewById(R.id.taskDescription);
        minutes = view.findViewById(R.id.fragment25Minustes);
        schedule = view.findViewById(R.id.scheduleTaskButton);
    }
    public void onClickListeners(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListFragment fragment = new ListFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()) {
                    if (index==-1) {
                        String uniqueId = UUID.randomUUID().toString();
                        TaskModel model = new TaskModel(name.getText().toString(), content.getText().toString(), Integer.valueOf(minutes.getText().toString()), "false", uniqueId);
                        list1.add(model);
                        saveData();
                        ListFragment fragment = new ListFragment();
                        FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.frameLayout, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    else {
                        list1.get(index).setTaskContent(content.getText().toString());
                        list1.get(index).setTaskName(name.getText().toString());
                        list1.get(index).setTime(Integer.valueOf(minutes.getText().toString()));
                        saveData();
                        ListFragment fragment = new ListFragment();
                        FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.frameLayout, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            }
        });
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.valueOf(minutes.getText().toString());
                time = time+25;
                minutes.setText(String.valueOf(time));
            }
        });
        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.valueOf(minutes.getText().toString());
                if(time==25){
                    Toast.makeText(view.getContext(), "Cannot be less than 25", Toast.LENGTH_SHORT).show();
                }
                else {
                    time = time-25;
                    minutes.setText(String.valueOf(time));
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

    }
    public void saveData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.tasklist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        editor.putString("taskList",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.selfuel.tasklist",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("taskList","");
        Type type = new TypeToken<ArrayList<TaskModel>>() {}.getType();
        list1 =gson.fromJson(json,type);
        if(list1==null){
            list1 =new ArrayList<>();
        }
    }
    public boolean checkEmpty(){
        if(name.getText().toString().length()==0){
            Toast.makeText(view.getContext(), "Please enter a Task Name", Toast.LENGTH_SHORT).show();
            return false;
        }if (content.getText().toString().length()==0){
            Toast.makeText(view.getContext(), "Please enter Task Content", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}