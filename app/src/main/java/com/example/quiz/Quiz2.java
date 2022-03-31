package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Quiz2 extends AppCompatActivity {

    Button btnnext;
    RadioGroup btnradio;
    RadioButton btnradoui;
    RadioButton btnradnon;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        btnradio = findViewById(R.id.radiogrp);
        btnradoui = findViewById(R.id.radioButton1);
        btnradnon = findViewById(R.id.radioButton2);
        btnnext = findViewById(R.id.ButtonNext);
        back = findViewById(R.id.back);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButtonMethod();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int CheckedButtonId) {
                switch (CheckedButtonId) {
                    case R.id.radioButton1:
                        Toast.makeText(getApplicationContext(), btnradoui.getText(), Toast.LENGTH_SHORT).show();
                        btnnext.setOnClickListener(view -> {
                            startActivity(new Intent(Quiz2.this, Quiz3.class));
                        });
                        break;
                    case R.id.radioButton2:
                        Toast.makeText(getApplicationContext(), btnradnon.getText(), Toast.LENGTH_SHORT).show();
                        btnnext.setOnClickListener(view -> {
                            Quiz1.score+=20;
                            Intent intent=new Intent(Quiz2.this, Quiz3.class);
                            startActivity(intent);
                        });
                        break;



                }
            }
        });


    }


    void onClickButtonMethod() {
        int selectedId = btnradio.getCheckedRadioButtonId();
        btnradoui = (RadioButton) findViewById(selectedId);

        if(selectedId==-1) {
            Toast.makeText(getApplicationContext(), "Nothing Selected Please select one !", Toast.LENGTH_LONG).show();
        }
    }

    void goBack() {
        Intent intent =new Intent(Quiz2.this,Quiz1.class);
        startActivity(intent);
    }


}






