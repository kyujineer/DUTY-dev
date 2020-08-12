package com.example.duty.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duty.R;
import com.example.duty.User;

public class Menu4 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "argUser";

    // TODO: Rename and change types of parameters
    private User user;

    public Menu4() {
    }

    public static Menu4 newInstance(User argUser) {
        Menu4 fragment = new Menu4();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, argUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
        }
        /*

        Log.e("Menu1","debug start");
        Log.e("user", "*****onCreate: "+ user.getID());
        Log.e("user", "*****onCreate: "+ user.getName());
        Log.e("user", "*****onCreate: "+ user.getTeamId());
        Log.e("user", "*****onCreate: "+ user.getTeamName());
        Log.e("user", "*****onCreate: "+ user.getRole());
        Log.e("user", "*****onCreate: "+ user.isAdmin());
        Log.e("Menu1","debug end");

         */
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_4, container, false);
    }
}
