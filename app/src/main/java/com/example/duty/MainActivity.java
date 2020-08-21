package com.example.duty;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.duty.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Intent 받아오기
        Intent intent = getIntent();
        // 유저 생성
        user = new User(
                intent.getStringExtra(getString(R.string.ID)),
                intent.getStringExtra(getString(R.string.name)),
                intent.getStringExtra(getString(R.string.teamId)),
                intent.getStringExtra(getString(R.string.teamName)),
                intent.getStringExtra(getString(R.string.role)),
                intent.getBooleanExtra(getString(R.string.isAdmin),false),
                intent.getStringExtra(getString(R.string.documentId))
        ); // 고칠예정

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // turn page to menu layout
        intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("user", user);
        Log.e("*******main*****", user.getDocumentId());
        // move to Activity
        startActivity(intent);

    }
}