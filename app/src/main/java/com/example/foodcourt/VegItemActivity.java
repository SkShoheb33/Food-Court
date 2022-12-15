package com.example.foodcourt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class VegItemActivity extends AppCompatActivity {
    Button btn1,btn2,btn3;
    Chip chip1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg_item);
        btn1 = findViewById(R.id.button3); // menu button
        btn2 = findViewById(R.id.button4); // profile button
        btn3 = findViewById(R.id.button5); // home button
        chip1 = findViewById(R.id.chip2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenu();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
        chip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTONonVeg();
            }
        });
    }
    public void goToMenu(){
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
    }
    public void goToProfile(){
        Toast.makeText(this, "profile is clicked", Toast.LENGTH_SHORT).show();
    }
    public void goToHome(){
        Toast.makeText(this, "Home is clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ItemsActivity.class);
        startActivity(intent);
    }
    public  void goTONonVeg(){
        Intent intent = new Intent(this,ItemsActivity.class);
        startActivity(intent);
    }
}