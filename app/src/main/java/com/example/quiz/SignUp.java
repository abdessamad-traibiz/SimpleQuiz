package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class SignUp extends AppCompatActivity {


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    EditText email;
    EditText password,confirmpassword;
    Button btnregister;
    EditText name;
    TextView AlreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email =findViewById(R.id.login2);
        password=findViewById(R.id.Password2);
        confirmpassword=findViewById(R.id.Password3);
        btnregister=findViewById(R.id.registerBtn);
        name=findViewById(R.id.Name);
        AlreadyHaveAccount=findViewById(R.id.AlreadyHaveAccount);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnregister.setOnClickListener(view -> {createUser();
        });
        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignIn();
            }
        });

    }
    public void createUser(){
        String email1=email.getText().toString();
        String password1=password.getText().toString();
        String name1=name.getText().toString();
        String password2=confirmpassword.getText().toString();
        if(TextUtils.isEmpty(email1)){
            email.setError("Email field is empty");
            email.requestFocus();
        }
        if(TextUtils.isEmpty(password1)){
            password.setError("Password field is empty");
            password.requestFocus();
        }
        if(TextUtils.isEmpty(name1)){
            name.setError("Name field is empty");
            name.requestFocus();
        }
        if(!password2.equals(password1)){
            confirmpassword.setError("Password not equals please check the fields");
            confirmpassword.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                                User user = new User();
                                user.setName1(name1);
                                user.setEmail1(email1);
                                user.setPassword1(password1);
                                mDatabase.child("users").child(currentUserID).setValue(user);
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user
                                Toast.makeText(SignUp.this, "Account not Created"+task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }
    }
    void goToSignIn() {
        Intent intent = new Intent(SignUp.this,MainActivity.class);
        startActivity(intent);
    }
}