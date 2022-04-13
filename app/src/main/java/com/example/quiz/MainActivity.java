package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    ImageView PhoneBtn;
    FirebaseAuth mAuth;

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleBtn = findViewById(R.id.google_btn);
        PhoneBtn = findViewById(R.id.phonebtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            navigateToSecondActivity();
        }



        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button btnlogin= findViewById(R.id.loginbtn);
        Button SignUpButton =findViewById(R.id.SignUpBtn);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(view -> loginUser());

        SignUpButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,SignUp.class)));
        googleBtn.setOnClickListener(v -> SignIn());
        PhoneBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,OtpSendActivity.class)));


    }

    void SignIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            }catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void loginUser(){
        String email1=username.getText().toString();
        String password1=password.getText().toString();
        if(TextUtils.isEmpty(email1)){
            username.setError("Email field is empty");
            username.requestFocus();
        }
        else if(TextUtils.isEmpty(password1)){
            password.setError("Password field is empty");
            password.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(MainActivity.this,"Log in successful",Toast.LENGTH_LONG).show();
                            navigateToSecondActivity();
                        } else {
                            // If sign in fails, display a message to the user
                            Toast.makeText(MainActivity.this, "Authentication failed."+ Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    void navigateToSecondActivity(){
        Intent intent = new Intent(MainActivity.this, Quiz1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=mAuth.getCurrentUser();
        if(currentuser!=null){
            navigateToSecondActivity();
        }
    }
}