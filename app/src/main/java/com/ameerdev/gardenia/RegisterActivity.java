package com.ameerdev.gardenia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.GardeniaApi;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Firebase Connection
     */
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference =
            db.collection("Users");
    private FirebaseUser currentUser;


    EditText et_username, et_phoneNumber, et_email, et_password, et_confirm_password;
    Button btn_register;
    TextView tv_login;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.create_acct_progress);

        et_username = findViewById(R.id.et_username);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);

        btn_register = findViewById(R.id.btn_register);
        tv_login = findViewById(R.id.tv_login);

        btn_register.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(et_username.getText().toString())&&
                    !TextUtils.isEmpty(et_phoneNumber.getText().toString())&&
                    !TextUtils.isEmpty(et_email.getText().toString())&&
                    !TextUtils.isEmpty(et_password.getText().toString().trim())&&
                    et_password.getText().toString().trim().
                            equals(et_confirm_password.getText().toString().trim())
            )
            {
                createUser(et_username.getText().toString(),
                        et_phoneNumber.getText().toString()
                , et_email.getText().toString(),
                        et_password.getText().toString().trim());

            }else
            {
                Toast.makeText(RegisterActivity.this, "Empty Fields Not Allowed",
                        Toast.LENGTH_SHORT).show();
            }

        });

        tv_login.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
            startActivity(intent);
        });



    }

    private void createUser(String username , String phoneNumber, String email, String password) {

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //we take user to MainActivity
                            currentUser = firebaseAuth.getCurrentUser();
                            assert currentUser != null;
                            final String currentUserId = currentUser.getUid();

                            //Create a user Map so we can create a user in the User collection
                            Map<String, String> userObj = new HashMap<>();
                            userObj.put("userId", currentUserId);
                            userObj.put("username", username);
                            userObj.put("email", email);
                            userObj.put("phoneNumber", phoneNumber);
                            userObj.put("password", password);


                            //save to our firestore database
                            collectionReference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            documentReference.get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                progressBar.setVisibility(View.INVISIBLE);

                                                                GardeniaApi gardeniaApi = GardeniaApi.getInstance(); //Global API
                                                                gardeniaApi.setUserId(currentUserId);
                                                                gardeniaApi.setUsername(username);

                                                                Intent intent = new Intent(RegisterActivity.this,
                                                                        MainActivity.class);
//                                                                intent.putExtra("username", username);
//                                                                intent.putExtra("userId", currentUserId);
                                                                startActivity(intent);


                                                            } else {

                                                                progressBar.setVisibility(View.INVISIBLE);
                                                            }

                                                        }
                                                    });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("freg", "signInWithEmail:failure", task.getException());
                                        }
                                    });
                        }
                        else{
                            Log.d("freg", "signUpWithEmail:failure", task.getException());

                        }
                    }
                });

    }



}