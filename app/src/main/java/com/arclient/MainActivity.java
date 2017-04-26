package com.arclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    ProgressDialog p1;
    public static final String TAG="TAG";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        p1=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    public void get(View view) {
        String user = Username.getText().toString();
        String pass = Password.getText().toString();
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "enter ", Toast.LENGTH_SHORT).show();
        } else
        {
            p1.setMessage("Logging in...");
            p1.show();
            mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                p1.dismiss();
                                Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                p1.dismiss();
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                pass();

                                //Start next activity
                            }
                        }
                    }
            );



        }

    }

    private void pass() {
        Intent in = new Intent(this,Home.class);
        startActivity(in);
    }
}
