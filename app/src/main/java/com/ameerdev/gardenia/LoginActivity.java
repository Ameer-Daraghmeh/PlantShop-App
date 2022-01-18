package com.ameerdev.gardenia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ameerdev.gardenia.models.Plant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import util.GardeniaApi;

public class LoginActivity extends AppCompatActivity {

    TextView tv_register;
    Button btn_login;
    EditText et_email,et_password;
    ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.create_acct_progress);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();
        tv_register = findViewById(R.id.tv_register);
        btn_login = findViewById(R.id.btn_login);

/**
 * Login Button
 */
        btn_login.setOnClickListener(view -> {

            if (!TextUtils.isEmpty(et_email.getText().toString())&&
                    !TextUtils.isEmpty(et_password.getText().toString()))
            {
                LoginAuth(et_email.getText().toString(),et_password.getText().toString());
            }
            else {
                Toast.makeText(LoginActivity.this, "Enter Email and password",
                        Toast.LENGTH_SHORT).show();
            }

        });
/**
 * Register TextView
 */
        tv_register.setOnClickListener(view -> toRegister());
    }



    void toRegister(){
        Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(intent);
    }


    void LoginAuth(String email , String password){

        progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("fauth", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            GardeniaApi gardeniaApi = GardeniaApi.getInstance();
                            assert user != null;
                            gardeniaApi.setUserId(user.getUid());

                            db.collection("Users")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot users : task.getResult()) {
                                                    //Log.d("suuu", document.getId() + " => " + document.getData());
                                                    if (users.getData().get("userId").toString().equals(gardeniaApi.getUserId())){
                                                        gardeniaApi.setUsername(users.getData().get("username").toString());
                                                    }

                                                }
                                            } else {
                                                                                          }
                                        }
                                    });

                            Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Wrong Email or password",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    });
    }


}