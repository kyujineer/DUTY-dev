package com.example.duty.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;


import com.example.duty.R;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarView extends LinearLayout {

    TextView CurrentDate; // 현재날짜 텍스트 뷰
    GridView gridView; //그리드뷰
    TextView ChangeMonth; // 월바꾸기 화면 text view

    private static final int MAX_CALENDAR_DATE = 42;
    Calendar calendar = Calendar.getInstance(Locale.KOREA);
    Context context;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.KOREA);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.KOREA);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    SimpleDateFormat changeDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);

    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();


    GridAdapter gridAdapter;
    AlertDialog alertDialog;

    //근무자 표시
    String [] List_Name = {"이반석", "정규진", "임주영"};



    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitializeLayout();
        SetUpCalendar();

        //왼쪽위의 년도, 월 항목 클릭시 월 변경 가능한 Alert Dialog 띄워주는 것 Start
        CurrentDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View changeView = LayoutInflater.from(context).inflate(R.layout.calendar_change_month, null);
                ImageButton previous = changeView.findViewById(R.id.btn_previousMonth);
                ImageButton next = changeView.findViewById(R.id.btn_nextMonth);
                final TextView tv_changeMonth = changeView.findViewById(R.id.tv_changeMonth);
                final GridView twelveMonth = changeView.findViewById(R.id.twelve_month);
                String currentDay = changeDateFormat.format(calendar.getTime());

                tv_changeMonth.setText(currentDay);
                // 전월로 이동 클릭
                previous.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendar.add(Calendar.MONTH, -1);
                        String previousDay = changeDateFormat.format(calendar.getTime());
                        tv_changeMonth.setText(previousDay);
                        SetUpCalendar();
                    }
                });
                // 다음달로 이동 클릭
                next.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        calendar.add(Calendar.MONTH, 1);
                        String nextDay = changeDateFormat.format(calendar.getTime());
                        tv_changeMonth.setText(nextDay);
                        SetUpCalendar();
                    }
                });

                builder.setView(changeView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        // End
        /* 원래 gridView onItem Click 시 띄우는 것 위치 해 있던 곳.. menu 2 로 이사갔음

        //달력 날짜 클릭시 이벤트 화면 띄워주기 Start
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_selection_popup, null);
                final TextView todayDate = addView.findViewById(R.id.tv_popUpDate);
                // 추후 업데이트 될 이미지 바꾸는 거랑 버튼 눌럿을시 action.. 아직 구현 안함
                ImageView shiftImage = addView.findViewById(R.id.iv_duty);
                Button btn_request = addView.findViewById(R.id.btn_swapRequest);
                Button btn_alarmSet = addView.findViewById(R.id.btn_alarmSet);

                // 클릭한 날의 날짜 표기 위한 current Day 설정 start
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(dates.get(position));

                String currentDay = changeDateFormat.format(dateCalendar.getTime());
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
        */



    }



    // class start
    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        dates.clear();
        CurrentDate = (TextView) view.findViewById(R.id.tv_date);
        gridView = (GridView) view.findViewById(R.id.gridView);
    }

    private void SetUpCalendar() {
        String currentDay = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDay);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1; // 0부터 월요일 시작이라 빼주는 부분
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayOfMonth);

        while(dates.size() < MAX_CALENDAR_DATE) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        gridAdapter = new GridAdapter(context, dates, calendar, eventsList);
        gridView.setAdapter(gridAdapter);

    }


    public GridView getGridView() {
        return gridView;
    }
    public List<Date> getDates() {
        return dates;
    }
    public SimpleDateFormat getChangeDateFormat() {
        return changeDateFormat;
    }

}
