package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email_tv = findViewById(R.id.EmailAddress);
        EditText pass_tv = findViewById(R.id.Password);
        Button login = findViewById(R.id.button);
        TextView register = findViewById(R.id.register);
        Button signupbtn = findViewById(R.id.signupbtn);
        mauth = FirebaseAuth.getInstance();
        String email = email_tv.getText().toString();
        String pass = pass_tv.getText().toString();





        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_tv.getText().toString();
                String password = pass_tv.getText().toString();
                createAccount(email,password);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_tv.getText().toString();
                String password = pass_tv.getText().toString();
                Login(email,password);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mauth.getCurrentUser();
        if (currentUser!=null){
            reload();
        }
    }

    private void reload() {
        Toast.makeText(MainActivity.this,"User already exist",Toast.LENGTH_SHORT).show();
    }

    public void createAccount(String email, String password) {
        mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mauth.getCurrentUser();
                    updateUI(user);
                } else {
                    updateUI(null);
                }
            }
        });
    }
    public void Login(String email, String pass){
        mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                }

        );
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
        }
    }
}
