package com.example.duty.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.duty.R;
import com.example.duty.User;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {


    //바텀네비게이션 선언 start
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션
    private FragmentManager fm;
    private FragmentTransaction ft;
    private InitialMenu initialMenu;
    private Menu1 menu1;
    private Menu2 menu2;
    private Menu3 menu3;
    private Menu4 menu4;
    private AdView adView;
    //바텀네비게이션 선언 end

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Intent 받아오기
        Intent intent = getIntent();

        User user = intent.getParcelableExtra("user");
        /*
        Log.e("MenuActivity","debug start");
        Log.e("user", "*****onCreate: "+ user.getID());
        Log.e("user", "*****onCreate: "+ user.getName());
        Log.e("user", "*****onCreate: "+ user.getTeamId());
        Log.e("user", "*****onCreate: "+ user.getTeamName());
        Log.e("user", "*****onCreate: "+ user.getRole());
        Log.e("user", "*****onCreate: "+ user.isAdmin());
        Log.e("MenuActivity","debug end");

         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // ads related
        adView = (AdView) findViewById(R.id.adView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                adView.loadAd(new AdRequest.Builder().build());
            }
        });

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
        menu2 = new Menu2();
        menu3 = new Menu3();
        menu4 = new Menu4();
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

    }
}