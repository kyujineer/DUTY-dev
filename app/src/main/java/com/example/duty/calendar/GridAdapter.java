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
import com.example.duty.team.data.ShiftInfo;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

// 이곳은 달력안에 들어가는 1~30일 에 대해 설정할 수 있슴... 
public class GridAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;
    TextView calendar_day;
    TextView events_day;
    Random random;

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


        if (view == null) {
            view = inflater.inflate(R.layout.calendar_single_cell, parent, false);
        }
        // 현 월만 색이나 다른 걸로 표현 하기위 한 구문

        // 날짜 표기하는 text view
        calendar_day = (TextView) view.findViewById(R.id.calendar_day);

        if (displayMonth == currentMonth && displayYear == currentYear) {
            calendar_day.setTextColor(getContext().getResources().getColor(R.color.colorBlack));
        } else {
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        // 토요일 일요일 색상 바꾸기!
        if (weekDay == Calendar.SUNDAY) {
            calendar_day.setTextColor(getContext().getResources().getColor(R.color.colorSun));
        } else if (weekDay == Calendar.SATURDAY) {
            calendar_day.setTextColor(getContext().getResources().getColor(R.color.colorSat));
        }

        calendar_day.setText(String.valueOf(DayNo));



        //D, N, E, O 표기하는 text view 항목.

        random = new Random();
        List<String> tempShiftList = new ArrayList<>();
        tempShiftList.add("DAY");
        tempShiftList.add("OFF");
        tempShiftList.add("NIGHT");
        tempShiftList.add("EVENING");

        events_day = (TextView) view.findViewById(R.id.events_id);

        /*//ShiftDataList 생성하여 위의 랜덤으로 형성된 근무 형태 저장.

        List<ShiftData> shiftDataList = new ArrayList<>();
        ShiftData shiftData = new ShiftData();

*/
        if (Condition.getCondition() == 0) {

            switch (tempShiftList.get(random.nextInt(tempShiftList.size()))) {
                case "DAY":
                    events_day.setText("Day");
                    //shiftData.setStatus(ShiftData.Status.DAY);
                    break;
                case "OFF":
                    events_day.setText("Off");
                    //shiftData.setStatus(ShiftData.Status.OFF);
                    break;
                case "NIGHT":
                    events_day.setText("Night");
                    //shiftData.setStatus(ShiftData.Status.NIGHT);
                    break;
                case "EVENING":
                    events_day.setText("Evening");
                    //shiftData.setStatus(ShiftData.Status.EVENING);
                    break;
                default:
                    events_day.setText("");


            }
            //shiftDataList.add(shiftData);


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

