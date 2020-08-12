package com.example.duty;


import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.duty.menu.Menu1_frag1;
import com.example.duty.menu.MenuActivity;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Intent 받아오기
        Intent intent = getIntent();
        // 유저 생성
        user = new User(
                intent.getStringExtra("ID"),
                intent.getStringExtra("name"),
                intent.getStringExtra("teamId"),
                intent.getStringExtra("teamName"),
                intent.getStringExtra("role"),
                intent.getBooleanExtra("isAdmin",false)
        ); // 고칠예정

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // turn page to menu layout
        intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("user", user);
        // move to Activity
        startActivity(intent);

    }
}