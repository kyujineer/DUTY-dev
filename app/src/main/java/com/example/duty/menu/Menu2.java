package com.example.duty.menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duty.R;
import com.example.duty.User;
import com.example.duty.calendar.CalendarView;

import java.util.Calendar;

public class Menu2 extends Fragment {

    CalendarView calendarView;
    Context context;
    AlertDialog alertDialog;

    String [] List_Name = {"이반석", "정규진", "임주영"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "argUser";

    // TODO: Rename and change types of parameters
    private User user;

    public Menu2() {
    }

    public static Menu2 newInstance(User argUser) {
        Menu2 fragment = new Menu2();
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

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar_main, container, false);
    }

    //onViewCreated 통한 그리드뷰 클릭시 화면 띄우는 것 Calanedar View class에서 menu 2 로 올김
    // 중요한 건 view.getcontext 는 calandar view 의 context 고 그냥 context 는 adapter의 context?
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // CalendarView Class 내의 기능을 Fragment 인 menu 2 안에서 돌림. 중요한 점은 view.getContext..였다.

        calendarView = (CalendarView) view.findViewById(R.id.activity_calendar);

        calendarView.getGridView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                final View addView = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.calendar_selection_popup, null);
                final TextView todayDate = addView.findViewById(R.id.tv_popUpDate);
                // 추후 업데이트 될 이미지 바꾸는 거랑 버튼 눌럿을시 action.. 아직 구현 안함
                ImageView shiftImage = addView.findViewById(R.id.iv_duty);
                Button btn_request = addView.findViewById(R.id.btn_swapRequest);
                Button btn_alarmSet = addView.findViewById(R.id.btn_alarmSet);

                // 클릭한 날의 날짜 표기 위한 current Day 설정 start
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(calendarView.getDates().get(i));

                String currentDay = calendarView.getChangeDateFormat().format(dateCalendar.getTime());
                todayDate.setText(currentDay);
                // end

                // 근무자 리스트 뷰에 넣기 위의 List_Name 의 스트링 어레이 값 가져옴 일단 나중에 근무자 정보를 위의 어레이리스트에 저장하면 여기 뜰것임 start
                ListView todayWorker = addView.findViewById(R.id.list_name);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(addView.getContext(), android.R.layout.simple_list_item_1, List_Name);

                todayWorker.setAdapter(adapter);
                // end
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
}