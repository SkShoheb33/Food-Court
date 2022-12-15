package com.example.foodcourt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btn,create;
    private DatabaseReference rootDatabaseref;
    EditText username1;
    EditText password1;
    String username_entered;
    String password_entered;
    //Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black));
        //create a new account
        create = findViewById(R.id.createAccount);
        //from firebase console we extract users child
        rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("users");
        btn = findViewById(R.id.login);
        username1 = findViewById(R.id.username);
        password1 = (EditText) findViewById(R.id.password);
        // login button on click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              verify_login();
            }
        });
        //create button on click
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }
    public void verify_login(){
        //adding events to  root reference;
        rootDatabaseref.addValueEventListener(new ValueEventListener() {
            //
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //user entered credentials
                    username_entered = username1.getText().toString().trim();
                    password_entered = password1.getText().toString().trim();
                    //user data stored in the fire base;
                    //String username = snapshot.child("user1").child("name").getValue().toString().toLowerCase().replace("'","");
                    String password = snapshot.child(username_entered).child("password").getValue().toString();
                    //verifying the credentials
                    if(password.equals(password_entered)) {
                        Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();
                        login();
                    }else{
                        alert();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "database is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void login(){
        Intent intent = new Intent(this,ItemsActivity.class);
        startActivity(intent);
    }
    public void createAccount(){
        Intent intent = new Intent(this,NewAccountActivity.class);
        startActivity(intent);
    }
    //if we enter worng credentials alert box appears here.
    public void alert(){
        AlertDialog.Builder dig = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Check")
                .setMessage("Please enter valid details")
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this,"Enter valid ",Toast.LENGTH_SHORT).show();
                    }
                });
        dig.create().show();
    }
}