package com.example.duty.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duty.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 이곳은 달력안에 들어가는 1~30일 에 대해 설정할 수 있슴... 
public class GridAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;
    TextView calendar_day;
    TextView events_day;

    public GridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Events> events) {
        super(context, R.layout.calendar_layout);

        this.dates = dates;
        this.currentDate = currentDate;
        this.events = events;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();



        dateCalendar.setTime(monthDate);

        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        //현재 달과 전달의 날짜 비교 위함
        int weekDay = dateCalendar.get(Calendar.DAY_OF_WEEK);


        View view = convertView;


        if(view == null) {
            view = inflater.inflate(R.layout.calendar_single_cell, parent,false);
        }
        // 현 월만 색이나 다른 걸로 표현 하기위 한 구문

        // 날짜 표기하는 text view
        calendar_day = (TextView) view.findViewById(R.id.calendar_day);

            if(displayMonth == currentMonth && displayYear == currentYear) {
                calendar_day.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
            }
            else {
                view.setBackgroundColor(Color.parseColor("#cccccc"));
            }
            // 토요일 일요일 색상 바꾸기!
            if(weekDay == Calendar.SUNDAY) {
                calendar_day.setTextColor(getContext().getResources().getColor(R.color.colorSun));
            }
            else if(weekDay == Calendar.SATURDAY) {
                calendar_day.setTextColor(getContext().getResources().getColor(R.color.colorSat));
            }

        calendar_day.setText(String.valueOf(DayNo));

        //D, N, E, O 표기하는 text view 항목.
        events_day = (TextView) view.findViewById(R.id.events_id);
            if(Condition.getCondition() == 0) {
                if((DayNo % 2) == 1) {
                    events_day.setText("D");
                } else {
                    events_day.setText("O");
                }
            }


        return view;

    }

    @Override
    public int getCount() {
        return dates.size();

    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);

    }


}
