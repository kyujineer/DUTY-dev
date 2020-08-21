package com.example.duty.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.duty.R;
import com.example.duty.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    private User user;
    //바텀네비게이션 선언 start
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션
    private FragmentManager fm;
    private FragmentTransaction ft;
    private InitialMenu initialMenu;
    private Menu1 menu1;
    private Menu2 menu2;
    private Menu3 menu3;
    private Menu4 menu4;
    //바텀네비게이션 선언 end
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    //Toolbar RecyclerView Variables Declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Intent 받아오기
        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //아래 메뉴아이콘 클릭시 화면 전환 구현 start
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu_bottom);
        //클릭시 setMenu(i) i 값 바꿔주는 거
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_menu1:
                        setMenu(1);
                        break;
                    case R.id.action_menu2:
                        setMenu(2);
                        break;
                    case R.id.action_menu3:
                        setMenu(3);
                        break;
                    case R.id.action_menu4:
                        setMenu(4);
                        break;
                }
                return true;
            }
        });
        menu1 = Menu1.newInstance(user);
        menu2 = Menu2.newInstance(user);
        menu3 = Menu3.newInstance(user);
        menu4 = Menu4.newInstance(user);
        initialMenu = new InitialMenu();
        setMenu(0);
        //아래 메뉴아이콘 클릭시 화면 전환 구현 end


    }

    private void setMenu(int i) {
        //메뉴아이콘 클릭에 대한 구문
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (i) {
            case 0:
                ft.replace(R.id.frame_main, initialMenu);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.frame_main, menu1);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frame_main, menu2);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.frame_main, menu3);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.frame_main, menu4);
                ft.commit();
                break;
        }
        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // label 비활성화
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    // 상단 툴바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_feed:
                Intent intent = new Intent(MenuActivity.this , Toolbar_feed_activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}