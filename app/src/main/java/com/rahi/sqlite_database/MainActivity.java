package com.rahi.sqlite_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rahi.sqlite_database.Database.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText et_email, et_pass, et_name,et_profession,et_age;
    Button btn,btn2;
    DatabaseHelper db;
    String email,pass,name,profession;
    String age;
    int age1;

    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_profession = findViewById(R.id.et_prof);
        et_age = findViewById(R.id.et_age);

        db = new DatabaseHelper(this);

        btn.setOnClickListener(view -> {

            email = et_email.getText().toString();
            pass = et_pass.getText().toString();
            name = et_name.getText().toString();
            profession = et_profession.getText().toString();
            age = et_age.getText().toString();
//            age1 = Integer.parseInt(age);

            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(email);

            if(!matcher.matches() || email.isEmpty() || email.equals("")){
                et_email.setError("Please Enter Valid Email");
            }
            else if(pass.isEmpty() || pass.equals("")){
                et_pass.setError("Please Check Password");
            }
            else if(name.isEmpty() || name.equals("")){
                et_name.setError("Please Check Name");
            }
            else if(profession.isEmpty() || profession.equals("")){
                et_profession.setError("Please Check Profession");
            }
            else if(age.isEmpty() || age.equals("")){
                et_age.setError("Please Check Password");
            }
            else{
                Boolean checkInsertedData = db.InsertData(name,email,pass,profession,age);
                if(checkInsertedData == true) {
                    Intent intent = new Intent(this,MainActivity2.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    et_email.setText("");
                    et_name.setText("");
                    et_age.setText("");
                    et_pass.setText("");
                    et_profession.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Done", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn2.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}