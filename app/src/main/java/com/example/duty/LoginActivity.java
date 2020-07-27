package com.example.duty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.example.duty.menu.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    // Variables Declaration
    FirebaseFirestore db;
    Button login;
    Button register;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Variables Initialization
        db = FirebaseFirestore.getInstance();
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);

        // onClick Events

        //onLoginClick
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get ID and password
                final EditText id = (EditText) findViewById(R.id.et_id);
                final EditText pw = (EditText) findViewById(R.id.et_pw);

                // hi
                // check if id is available; if true, login; if false, fail login;
                db.collection("regularUsers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                // if true
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        if (doc.get("ID").equals(id.getText().toString())) {
                                            // turn page to menu layout
                                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                            // move to Activity
                                            startActivity(intent);
                                        }
                                    }
                                }
                                // if false
                                else {
                                    return;
                                }

                            }
                        });
            }
        });
        //onRegisterClick
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // turn page to register layout
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // move to Activity
                startActivity(intent);
            }
        });

    }








}