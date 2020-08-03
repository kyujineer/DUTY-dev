package com.example.duty.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.duty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarView extends LinearLayout {

    TextView CurrentDate; // 현재날짜 텍스트 뷰
    GridView gridView; //그리드뷰

    private static final int MAX_CALENDAR_DATE = 42;
    Calendar calendar = Calendar.getInstance(Locale.KOREA);
    Context context;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.KOREA);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.KOREA);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    GridAdapter gridAdapter;
    AlertDialog alertDialog;



    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitializeLayout();
        SetUpCalendar();

        CurrentDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View changeView = LayoutInflater.from(context).inflate(R.layout.calendar_change_month, null);
                ImageButton previous = changeView.findViewById(R.id.btn_previousMonth);
                ImageButton next = changeView.findViewById(R.id.btn_nextMonth);
                final GridView twelveMonth = changeView.findViewById(R.id.twelve_month);

                builder.setView(changeView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });


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
}
