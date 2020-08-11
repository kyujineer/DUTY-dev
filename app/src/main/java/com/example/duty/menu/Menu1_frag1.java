package com.example.duty.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.duty.R;


public class Menu1_frag1 extends Fragment {
    // 현재 근무자 frag
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu1_frag1, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.fl_1);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로 가기
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}