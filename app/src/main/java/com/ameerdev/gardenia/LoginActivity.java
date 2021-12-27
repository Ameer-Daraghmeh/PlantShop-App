package com.ameerdev.gardenia;

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

public class LoginActivity extends AppCompatActivity {

    TextView tv_register;
    Button btn_login;
    EditText et_email,et_password;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();
        tv_register = findViewById(R.id.tv_register);
        btn_login = findViewById(R.id.btn_login);

//test
/**
 * Login Button
 */
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_email.getText().toString())&&
                        !TextUtils.isEmpty(et_password.getText().toString()))
                {
                LoginAuth(et_email.getText().toString(),et_password.getText().toString());
                }
                else {
                    Toast.makeText(LoginActivity.this, "Enter Email and password",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegister();
            }
        });

    }






    void toRegister(){
        Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(intent);
    }


    void LoginAuth(String email , String password){


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("fauth", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("fbauth", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    });


    }
}