package com.example.duty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RegisterActivity extends AppCompatActivity {

    // Variable declaration
    CheckBox isAdmin, validateId, validateTeamName, validateTeamId, validatePassword, validateName, validateRole;
    Button register;
    EditText id, pw, name, role, teamId, teamName;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Intent 받아오기
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Variable Initialization
        isAdmin = (CheckBox) findViewById(R.id.cb_isAdmin);
        validateId = (CheckBox) findViewById(R.id.cb_id);
        validateTeamName = (CheckBox) findViewById(R.id.cb_teamName);
        validateTeamId = (CheckBox) findViewById(R.id.cb_teamId);
        validatePassword = (CheckBox) findViewById(R.id.cb_pw);
        validateName = (CheckBox) findViewById(R.id.cb_name);
        validateRole = (CheckBox) findViewById(R.id.cb_role);
        register = (Button) findViewById(R.id.btn_register);
        id = (EditText) findViewById(R.id.et_id);
        pw = (EditText) findViewById(R.id.et_pw);
        name = (EditText) findViewById(R.id.et_name);
        role = (EditText) findViewById(R.id.et_role);
        teamId = (EditText) findViewById(R.id.et_teamId);
        teamName = (EditText) findViewById(R.id.et_teamName);
        db = FirebaseFirestore.getInstance();

        // OnFocusChanges
        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    db.collection(getString(R.string.Collection_regUser))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // track if id exists;
                                        boolean exist = false;
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            // if id exists, change flag
                                            if (doc.getData().get(getString(R.string.ID)).equals(id.getText().toString())) {
                                                exist = true;
                                                break;
                                            }
                                        }
                                        if (exist == true) {
                                            validateId.setChecked(false);
                                            Toast.makeText(RegisterActivity.this, "ID Not Available", Toast.LENGTH_LONG).show();
                                        } else {
                                            validateId.setChecked(true);
                                            // Toast.makeText(RegisterActivity.this, "Available ID", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Log.e("Error getting document", " ", task.getException());
                                    }
                                }
                            });
                }
            }
        });
        pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    if (pw.getText().toString().isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();
                        validatePassword.setChecked(false);
                    }
                    else {
                        validatePassword.setChecked(true);
                    }
                }
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    if (name.getText().toString().isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Name is Empty", Toast.LENGTH_SHORT).show();
                        validateName.setChecked(false);
                    }
                    else {
                        validateName.setChecked(true);
                    }
                }
            }
        });
        role.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    if (role.getText().toString().isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Role is Empty", Toast.LENGTH_SHORT).show();
                        validateRole.setChecked(false);
                    }
                    else {
                        validateRole.setChecked(true);
                    }
                }
            }
        });
        teamId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    db.collection(getString(R.string.Collection_teams))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // track if id exists;
                                        boolean exist = false;
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            // if id exists, change flag
                                            if (doc.getId().equals(teamId.getText().toString())) {
                                                exist = true;
                                                break;
                                            }
                                        }
                                        if (exist == true) {
                                            validateTeamId.setChecked(true);
                                            // Toast.makeText(RegisterActivity.this, "Available Team ID", Toast.LENGTH_LONG).show();
                                        } else {
                                            validateTeamId.setChecked(false);
                                            Toast.makeText(RegisterActivity.this, "Not Available Team ID", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Log.e("Error getting document", " ", task.getException());
                                    }
                                }
                            });
                }
            }
        });
        teamName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    db.collection(getString(R.string.Collection_teams))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // track if teamName exists
                                        boolean exist = false;
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            // if team name exists, change flag
                                            if (doc.getData().get(getString(R.string.teamName)).equals(teamName.getText().toString())) {
                                                exist = true;
                                                break;
                                            }
                                        }
                                        if (exist == true) {
                                            validateTeamName.setChecked(false);
                                            Toast.makeText(RegisterActivity.this, "Team Name Not Available", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            validateTeamName.setChecked(true);
                                            // Toast.makeText(RegisterActivity.this, "Available Team Name", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Log.e("Error getting document", " ", task.getException());
                                    }
                                }
                            });
                }
            }
        });

        // OnClicks
        isAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // disable teamId widget
                if (isAdmin.isChecked()) {
                    teamId.setEnabled(false);
                    validateTeamId.setEnabled(false);
                    teamName.setEnabled(true);
                    validateTeamName.setEnabled(true);
                }
                // enable teamId widget
                else {
                    teamId.setEnabled(true);
                    validateTeamId.setEnabled(true);
                    teamName.setEnabled(false);
                    validateTeamName.setEnabled(false);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //id, pw, name, role, teamId, teamName
                // if admin user
                if (isAdmin.isChecked()) {
                    // TODO Do nothing, will implement later.
                }
                // if regular user
                else {
                    // TODO Do nothing, will implement later.
                }


                // Move back to login activity
                // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            }
        });


    }


}