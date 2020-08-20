package com.example.duty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.duty.calendar.CalendarView;
import com.example.duty.calendar.Condition;


import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {



    int shiftKind; // shiftKind = 0 (Day) , 1 (Evening) , 2 (Night) , 3 (Off) , 4(지우기모드)
    RadioGroup radioGroup;

    CalendarView calendarView;
    Button btn_save, btn_submit, btn_erase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        //condition이 1일 때 희망근무설정 달력 표기
        Condition.setCondition(1);

        //radio group 설정
        radioGroup = (RadioGroup) findViewById(R.id.group_radio);
        shiftKind = 4;

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.radio_day) {
                    shiftKind = 0;
                } else if(i == R.id.radio_evening) {
                    shiftKind = 1;
                } else if(i == R.id.radio_night) {
                    shiftKind = 2;
                } else if(i == R.id.radio_off) {
                    shiftKind = 3;
                }
            }
        };

        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        //calendar View 형성
        calendarView = (CalendarView) findViewById(R.id.test_activity_calendar);

        calendarView.getGridView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                TextView textVIew = (TextView) view.findViewById(R.id.events_id);
                if(shiftKind == 0) {
                    textVIew.setText("Day");
                }
                else if(shiftKind == 1) {
                    textVIew.setText("Evening");
                }
                else if(shiftKind == 2) {
                    textVIew.setText("Night");
                }
                else if(shiftKind == 3) {
                    textVIew.setText("Off"); //Off
                }
                else if(shiftKind == 4) {
                    textVIew.setText(""); //지울때
                }

            }
        });

        //저장 & 제출 & 지우기 버튼
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_erase = (Button) findViewById(R.id.btn_erase);
        //저장 버튼 눌렀을 시
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("저장");
                builder.setMessage("저장 되었습니다.");
                builder.show();

            }
        });
        //제출 버튼 눌렀을시
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("제출");
                builder.setMessage("정말 제출 하시겠습니까?");

                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"다시 확인해 주십시오.",Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"제출하셨습니다.",Toast.LENGTH_SHORT).show();
                                //제출시 저장하는 것 JSON 여기있어야 할듯
                                getJSON();

                            }
                        });
                builder.show();
            }
        });

        btn_erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("지우기");
                builder.setMessage("지우기 모드 선택 하세요.");

                builder.setNegativeButton("전부 지우기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"다시 확인해 주십시오.",Toast.LENGTH_SHORT).show();

                            }
                        });
                builder.setPositiveButton("선택 지우기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"지우실 날짜를 선택하세요.",Toast.LENGTH_SHORT).show();
                                shiftKind = 4;


                            }
                        });
                builder.show();
            }
        });




    }


    public JSONObject getJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("1","day");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }




}
