package com.example.duty;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.duty.menu.MenuActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

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

        /*

        Log.e("MainActivity","debug start");
        Log.e("user", "*****onCreate: "+ user.getID());
        Log.e("user", "*****onCreate: "+ user.getName());
        Log.e("user", "*****onCreate: "+ user.getTeamId());
        Log.e("user", "*****onCreate: "+ user.getTeamName());
        Log.e("user", "*****onCreate: "+ user.getRole());
        Log.e("user", "*****onCreate: "+ user.isAdmin());
        Log.e("MainActivity","debug end");

         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // turn page to menu layout
        intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("user", user);
        // move to Activity
        startActivity(intent);

    }
}