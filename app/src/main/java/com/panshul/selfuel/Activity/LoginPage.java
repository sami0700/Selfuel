package com.panshul.selfuel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.panshul.selfuel.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends AppCompatActivity {

    EditText password,email;
    Button login;
    String passwordId,emailId;
    FirebaseAuth mauth;
    DatabaseReference myref;
    String uid;
    SharedPreferences.Editor editor;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        password = findViewById(R.id.loginPassword);
        email = findViewById(R.id.loginEmail);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.loginToSignup);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref = db.getReference("Users");

        SharedPreferences pref = getSharedPreferences("com.panshul.selfuel.userdata",MODE_PRIVATE);
        editor = pref.edit();

        mauth = FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()!=null){
            FirebaseUser user = mauth.getCurrentUser();
            uid = user.getUid();
            datasave();
            Intent intent = new Intent(LoginPage.this,MainActivity.class);
            startActivity(intent);
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,SignUpPage.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkempty()){
                    if(checkemail()){
                        login.setEnabled(false);
                        emailId = email.getText().toString();
                        passwordId = password.getText().toString();
                        mauth.signInWithEmailAndPassword(emailId,passwordId).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    datasave();
                                    login.setEnabled(true);
                                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                else {
                                    email.setText("");
                                    password.setText("");
                                    login.setEnabled(true);
                                    Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });


    }
    public void datasave(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    FirebaseUser user = mauth.getCurrentUser();
                    uid = user.getUid();

                    editor.putString("uid", uid);
                    String emaili = snapshot.child(uid).child("email").getValue().toString();
                    editor.putString("emailId", emaili);

                    String fcm = snapshot.child(uid).child("fcm").getValue().toString();
                    editor.putString("fcm", fcm);

                    String name = snapshot.child(uid).child("name").getValue().toString();
                    editor.putString("name", name);

                    String phone = snapshot.child(uid).child("phoneNumber").getValue().toString();
                    editor.putString("phone", phone);

                    String points = snapshot.child(uid).child("points").getValue().toString();
                    editor.putString("points", points);

                    editor.apply();
                }
                catch (NullPointerException e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean checkempty(){
        if(email.getText().length()==0){
            Toast.makeText(this, "Please enter a Email Id", Toast.LENGTH_SHORT).show();
            return false;

        }
        else if(password.getText().length()==0){
            Toast.makeText(this, "Please enter a Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
    public boolean checkemail(){
        String tempemail=email.getText().toString().trim();
        Pattern emailpattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        Matcher emailMatcher= emailpattern.matcher(tempemail);
        if(emailMatcher.matches()){
            return true;
        }
        email.setError("Please enter a valid email id");
        email.requestFocus();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}