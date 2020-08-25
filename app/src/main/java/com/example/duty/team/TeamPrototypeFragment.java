package com.example.duty.team;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duty.R;
import com.example.duty.ScheduleRequestActivity;
import com.example.duty.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamPrototypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamPrototypeFragment extends Fragment {

    private static final String ARG_USER = "argUser";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User user;

    // Variable declaration
    Button btn_request, btn_create, btn_calendar;

    public TeamPrototypeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TeamPrototypeFragment newInstance(User user) {
        TeamPrototypeFragment fragment = new TeamPrototypeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_team_prototype, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // button initialization
        btn_request = (Button) view.findViewById(R.id.btn_request);
        btn_create = (Button) view.findViewById(R.id.btn_create);
        btn_calendar = (Button) view.findViewById(R.id.btn_calendar);

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleRequestActivity.class);
                intent.putExtra("user", user);
                // move to Activity
                startActivity(intent);
            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TeamCalendarActivity.class);
                intent.putExtra("user", user);

                startActivity(intent);
            }
        });


    }
}