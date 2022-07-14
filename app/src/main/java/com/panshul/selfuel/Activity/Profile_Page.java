package com.panshul.selfuel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.panshul.selfuel.R;

public class Profile_Page extends AppCompatActivity {

    TextView name,email,phone,uid,point;
    String name1,email1,phone1,uid1,point1;
    ImageView back,share;
    Button logout,reset;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page);
        name = findViewById(R.id.profileNameAns);
        email = findViewById(R.id.profileEmailAns);
        phone = findViewById(R.id.profilePhoneNumAns);
        point = findViewById(R.id.profilePointNum);
        uid = findViewById(R.id.profileUserCodeAns);
        back = findViewById(R.id.profileBackButton);
        share=findViewById(R.id.profileShareImageView);
        logout = findViewById(R.id.profileLogoutButton);
       // reset = findViewById(R.id.profileChangePwdButton);

        mauth = FirebaseAuth.getInstance();

        SharedPreferences pref = getSharedPreferences("com.panshul.selfuel.userdata",MODE_PRIVATE);
        name1 = pref.getString("name","");
        email1 = pref.getString("emailId","");
        phone1 = pref.getString("phone","");
        point1 = pref.getString("points","");
        uid1 = pref.getString("uid","");

        name.setText(name1);
        email.setText(email1);
        phone.setText(phone1);
        point.setText(point1);
        uid.setText(uid1);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String app_url = " https://play.google.com/store/apps/details?id=com.adgvit.externals";
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String text = "Selfuel - A pomodoro app\n" +
                        "\n" +
                        "Optimize your work and increase productivity today.\n" +
                        "Accept my invite and let's rock together\n" +
                        "\n" +
                        "Join now\n" +
                        uid1;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,text);
                v.getContext().startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                clearData();
                SharedPreferences userdata = getSharedPreferences("com.panshul.selfuel.userdata", Context.MODE_PRIVATE);
                SharedPreferences.Editor  editoruser = userdata.edit();
                editoruser.clear();
                editoruser.apply();
                Intent intent = new Intent(Profile_Page.this,LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }
    public void clearData(){
        SharedPreferences userdata = getSharedPreferences("com.panshul.selfuel.userdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser = userdata.edit();
        editoruser.clear();
        editoruser.apply();

        SharedPreferences userdata1 = getSharedPreferences("com.panshul.selfuel.tasklist",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser1 = userdata1.edit();
        editoruser1.clear();
        editoruser1.apply();

        SharedPreferences userdata2 = getSharedPreferences("com.panshul.selfuel.taskId",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editoruser2 = userdata2.edit();
        editoruser2.clear();
        editoruser2.apply();

        SharedPreferences preferences = getSharedPreferences("com.panshul.selfuel.spotifyList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = preferences.edit();
        editor3.clear();
        editor3.apply();

        SharedPreferences preferenc = getSharedPreferences("com.panshul.selfuel.pomodoro", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor4 = preferenc.edit();
        editor4.clear();
        editor4.apply();

        SharedPreferences preferen = getSharedPreferences("com.panshul.selfuel.clock", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor5 = preferen.edit();
        editor5.clear();
        editor5.apply();


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}