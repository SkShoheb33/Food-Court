package com.example.foodcourt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NewAccountActivity extends AppCompatActivity {
    EditText user_name, name, mobile, email, password,confirm_password;
    RadioGroup gender;
    Button createbtn, verify;
    String user_name_s, name_s, mobile_s, email_s, password_s,confirm_password_s;
    private TextView mobile_verify, otp_text;
    private LinearLayout digits;
    private DatabaseReference databaseReference;
    boolean verified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        getWindow().setStatusBarColor(ContextCompat.getColor(NewAccountActivity.this, R.color.black));
        user_name = findViewById(R.id.username);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirmpassword);
        createbtn = findViewById(R.id.createbtn);
        gender = findViewById(R.id.gender);
        //mobile verification otp sends to your mobile number.
        mobile_verify = findViewById(R.id.mobile_verfy);
        mobile_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile_verification();
            }
        });
        //create(register) button on click
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verified)
                register();
                else
                    Toast.makeText(NewAccountActivity.this,"Please verify your mobile-number",Toast.LENGTH_SHORT
                    ).show();
            }
        });
    }
    public void register(){
        user_name_s = user_name.getText().toString();
        name_s = name.getText().toString();
        mobile_s = mobile.getText().toString();
        email_s = email.getText().toString();
        password_s = password.getText().toString();
        confirm_password_s = confirm_password.getText().toString();
        if (TextUtils.isEmpty(user_name_s) || TextUtils.isEmpty(name_s) || TextUtils.isEmpty(email_s) || TextUtils.isEmpty(password_s) || TextUtils.isEmpty(mobile_s)) {
            AlertDialog.Builder dia = new AlertDialog.Builder(NewAccountActivity.this).setTitle("Please").setMessage("Enter all fileds").setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dia.create().show();
        } else {
            if(!password_s.equals(confirm_password_s)){
                Toast.makeText(NewAccountActivity.this,"Please enter same password",Toast.LENGTH_SHORT).show();
            }else {
                /*---------------------------------------------*/
                databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                User user = new User(name_s,"m",mobile_s,email_s,password_s);
                databaseReference.child(user_name_s).setValue(user);
                Toast.makeText(NewAccountActivity.this,"registered successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    public void mobile_verification(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile.getText().toString(), 60, TimeUnit.SECONDS,
                NewAccountActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(NewAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        otp_text = findViewById(R.id.text_otp);
                        digits = findViewById(R.id.digits);
                        digits.setVisibility(View.VISIBLE);
                        otp_text.setVisibility(View.VISIBLE);
                        verify = findViewById(R.id.mobile_verfy_btn);
                        verify.setVisibility(View.VISIBLE);
                        Toast.makeText(NewAccountActivity.this, "otp sent", Toast.LENGTH_SHORT).show();
                        //verifying the otp

                        verify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText digit1 = findViewById(R.id.digit1),
                                        digit2 = findViewById(R.id.digit2),
                                        digit3 = findViewById(R.id.digit3),
                                        digit4 = findViewById(R.id.digit4),
                                        digit5 = findViewById(R.id.digit5),
                                        digit6 = findViewById(R.id.digit6);
                                String opt_entered = digit1.getText().toString() + digit2.getText().toString() + digit3.getText().toString() + digit4.getText().toString()+digit5.getText().toString()+digit6.getText().toString();
                                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(s, opt_entered);
                                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            mobile.setEnabled(false);
                                            Toast.makeText(NewAccountActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                                            verified = true;
                                            digits.setVisibility(View.GONE);
                                            verify.setVisibility(View.GONE);
                                            otp_text.setVisibility(View.GONE);

                                        }else{
                                            AlertDialog.Builder dig = new  AlertDialog.Builder(NewAccountActivity.this)
                                                    .setTitle("Wrong OPT")
                                                    .setMessage("Please enter valid opt")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                        }
                                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            startActivity(new Intent(NewAccountActivity.this,MainActivity.class));
                                                        }
                                                    });
                                            dig.create().show();
                                            //Toast.makeText(NewAccountActivity.this,"Wrong otp",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
        );
    }
}