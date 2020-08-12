package com.example.duty.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.example.duty.R;


public class Menu1_frag2 extends Fragment {
    // 전번초 근무자 frag
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu1_frag2, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.fl_2);
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