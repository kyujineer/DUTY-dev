package com.example.duty.menu;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duty.R;

public class Menu1 extends Fragment {

    // Variables Declaration
    private View view;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_1, container, false);
        // 근무자, 전번초, 후번초 id 값
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_1);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_menu2);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.ll_menu3);

        // 근무자 onClick Events
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.rl_1:
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        // ll_menu1 = Menu1 전체 테두리 Linearlayout
                        ft.replace(R.id.ll_menu1, new Menu1_frag1(), "fragment_screen");
                        // 뒤로 가기
                        ft.addToBackStack(Menu1_frag1.class.getSimpleName());
                        ft.commit();
                }
            }
        });

        // 전번초 onClick Events
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ll_menu2:
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.ll_menu1, new Menu1_frag2(), "fragment_screen");
                        ft.addToBackStack(Menu1_frag2.class.getSimpleName());
                        ft.commit();
                }
            }
        });

        // 후번초 onClick Events
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ll_menu3:
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.ll_menu1, new Menu1_frag3(), "fragment_screen");
                        ft.addToBackStack(Menu1_frag3.class.getSimpleName());
                        ft.commit();
                }
            }
        });
        return view;
    }
}


