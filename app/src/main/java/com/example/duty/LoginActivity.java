package com.example.duty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
    Button login, register;
    CheckBox isAdmin;
    EditText id, pw;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Intent 받아오기
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Variables Initialization
        db = FirebaseFirestore.getInstance();
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        isAdmin = (CheckBox) findViewById(R.id.cb_isAdmin);

        // get ID and password
        id = (EditText) findViewById(R.id.et_id);
        pw = (EditText) findViewById(R.id.et_pw);

        // onClick Events

        //onLoginClick
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // for testing
                if (id.getText().toString().equals("1") && pw.getText().toString().equals("1")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(getString(R.string.ID), "testID");
                    intent.putExtra(getString(R.string.name), "testName");
                    intent.putExtra(getString(R.string.teamId), "testTeamId");
                    intent.putExtra(getString(R.string.teamName), "testTeamName");
                    intent.putExtra(getString(R.string.role), "testRole");
                    intent.putExtra(getString(R.string.isAdmin), false);
                    startActivity(intent);
                }

                // if isAdmin
                if (isAdmin.isChecked()) {
                    // check if id is available; if true, login; if false, fail login;
                    db.collection(getString(R.string.Collection_adminUser))
                            .whereEqualTo(getString(R.string.ID),id.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // if true
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            // check if PASSWORD is valid
                                            if (doc.getString(getString(R.string.PASSWORD)).equals(pw.getText().toString())) {
                                                // turn page to menu layout
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                // hand over user information
                                                intent.putExtra(getString(R.string.ID), doc.getString("ID"));
                                                intent.putExtra(getString(R.string.name), doc.getString("name"));
                                                intent.putExtra(getString(R.string.teamId), doc.getString("teamId"));
                                                intent.putExtra(getString(R.string.teamName), doc.getString("teamName"));
                                                intent.putExtra(getString(R.string.role), doc.getString("role"));
                                                intent.putExtra(getString(R.string.isAdmin), doc.getBoolean("isAdmin"));
                                                // move to Activity
                                                startActivity(intent);
                                            }
                                            // if PASSWORD is invalid
                                            else {
                                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    // if false
                                    else {
                                        Toast.makeText(LoginActivity.this, "Invalid Login ID", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }
                            });
                }
                // if not isAdmin
                else {
                    // check if id is available; if true, login; if false, fail login;
                    db.collection(getString(R.string.Collection_regUser))
                            .whereEqualTo(getString(R.string.ID),id.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // if true
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            // check if PASSWORD is valid
                                            if (doc.getString(getString(R.string.PASSWORD)).equals(pw.getText().toString())) {
                                                // turn page to menu layout
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                // hand over user information
                                                intent.putExtra(getString(R.string.ID), doc.getString("ID"));
                                                intent.putExtra(getString(R.string.name), doc.getString("name"));
                                                intent.putExtra(getString(R.string.teamId), doc.getString("teamId"));
                                                intent.putExtra(getString(R.string.teamName), doc.getString("teamName"));
                                                intent.putExtra(getString(R.string.role), doc.getString("role"));
                                                intent.putExtra(getString(R.string.isAdmin), doc.getBoolean("isAdmin"));
                                                // move to Activity
                                                startActivity(intent);
                                            }
                                            // if PASSWORD is invalid
                                            else {
                                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    // if false
                                    else {
                                        Toast.makeText(LoginActivity.this, "Invalid Login ID", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }
                            });
                }
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