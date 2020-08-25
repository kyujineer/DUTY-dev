package com.example.duty.team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.duty.R;
import com.example.duty.team.data.DateInfo;
import com.example.duty.team.data.MemberInfo;
import com.example.duty.team.data.ShiftInfo;
import com.kelin.scrollablepanel.library.ScrollablePanel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TeamCalendarActivity extends AppCompatActivity {

    public static final SimpleDateFormat DAY_UI_MONTH_DAY_FORMAT = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat WEEK_FORMAT = new SimpleDateFormat("EEE", Locale.US);

    // 간호사 List 생성1
    ArrayList<String> memberList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listitem_main);

        Intent intent = getIntent();

        final ScrollablePanel scrollablePanel = (ScrollablePanel) findViewById(R.id.scrollable_panel);
        final TeamCalendarAdapter myAdapter = new TeamCalendarAdapter();
        generateTestData(myAdapter);
        scrollablePanel.setPanelAdapter(myAdapter);



    }

    private void generateTestData(TeamCalendarAdapter scrollablePanelAdapter) {

        //Column Panel 시작 ------------------------------------------------------------------------

        //간호사 List 생성2
        for(int i = 1; i <= 20; i++) {
            memberList.add("간호사" + i);
            //간호사 List 생성 2 ends
        }

        List<MemberInfo> memberInfoList = new ArrayList<>();


        MemberInfo memberInform = new MemberInfo();
        memberInform.setMemberType("수간호사");
        memberInform.setMemberName(memberList.get(0));
        memberInform.setMemberId(0);
        memberInfoList.add(memberInform);

        for (int i = 1; i <= 5; i++) {
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setMemberType("CHARGE");
            memberInfo.setMemberId(i);
            memberInfo.setMemberName(memberList.get(i));
            memberInfoList.add(memberInfo);
        }
        for (int i = 6; i < memberList.size(); i++) {
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setMemberType("Standard");
            memberInfo.setMemberId(i);
            memberInfo.setMemberName(memberList.get(i));
            memberInfoList.add(memberInfo);
        }
        scrollablePanelAdapter.setMemberInfoList(memberInfoList);

        //Column Panel ends ------------------------------------------------------------------------
        //Row Panel starts

        List<DateInfo> dateInfoList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            DateInfo dateInfo = new DateInfo();
            String date = DAY_UI_MONTH_DAY_FORMAT.format(calendar.getTime());
            String week = WEEK_FORMAT.format(calendar.getTime());
            dateInfo.setDate(date);
            dateInfo.setWeek(week);
            dateInfoList.add(dateInfo);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        scrollablePanelAdapter.setDateInfoList(dateInfoList);

        //Row Panel ends----------------------------------------------------------------------------
        //Cell 내용 start ==========================================================================

        List<List<ShiftInfo>> shiftsInfo = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            List<ShiftInfo> shiftInfoList = new ArrayList<>();
            for (int j = 0; j < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                ShiftInfo shiftInfo = new ShiftInfo();
                shiftInfo.setWorkerName("charge\n" + memberList.get(i)); // 앞의 charge는 그 근무의 차지 간호사를 보여주기 위함이지 꼭 띄울건 아니다.
                shiftInfo.setBegin(true);
                shiftInfo.setStatus(ShiftInfo.Status.randomStatus());
                if (shiftInfoList.size() > 0) {
                    ShiftInfo lastOrderInfo = shiftInfoList.get(shiftInfoList.size() - 1);
                    if (shiftInfo.getStatus().ordinal() == lastOrderInfo.getStatus().ordinal()) {
                        shiftInfo.setId(lastOrderInfo.getId());
                        shiftInfo.setBegin(false);
                        shiftInfo.setWorkerName("");
                    } else {
                        if (new Random().nextBoolean()) {
                            shiftInfo.setStatus(ShiftInfo.Status.OFF);
                        }
                    }
                }
                shiftInfoList.add(shiftInfo);
            }
            shiftsInfo.add(shiftInfoList);
        }
        scrollablePanelAdapter.setShiftsList(shiftsInfo);
    }


}