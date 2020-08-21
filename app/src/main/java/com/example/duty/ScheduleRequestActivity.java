package com.example.duty;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duty.calendar.CalendarView;
import com.example.duty.calendar.Condition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ScheduleRequestActivity extends AppCompatActivity {

    int shiftKind; // shiftKind = 0 (Day) , 1 (Evening) , 2 (Night) , 3 (Off) , 4(지우기모드)
    RadioGroup radioGroup;
    Button btn_save, btn_submit, btn_erase;

    // statics
    static CalendarView calendarView;
    static Map<String, Object> newDocument;
    static List<String> schedule;
    static Calendar calendar;

    // Firebase
    FirebaseFirestore firebaseFirestore;
    final Map<String, Object> existingDocument = new HashMap<>();
    boolean exists;
    String existingId;

    // User
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_request);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        Log.e("***UserInformation***", user.getDocumentId());

        //condition이 1일 때 희망근무설정 달력 표기
        Condition.setCondition(1);

        // radio group Initialize
        radioGroup = (RadioGroup) findViewById(R.id.group_radio);
        shiftKind = 0;
        //calendar View Initialize
        calendarView = (CalendarView) findViewById(R.id.test_activity_calendar);
        // List and map initialize;
        newDocument = new HashMap<>();
        schedule = new ArrayList<>();
        //저장 & 제출 & 지우기 버튼 initialize
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_erase = (Button) findViewById(R.id.btn_erase);
        // Firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
        exists = false;
        existingId = "";
        // Calendar initialize
        calendar = Calendar.getInstance(Locale.KOREA);
        calendar.set(Calendar.DAY_OF_MONTH,1);

        // initialize existingDocument
        firebaseFirestore.collection(getString(R.string.Collection_regUser))
                .document(user.getDocumentId())
                .collection(getString(R.string.Collection_dutyRequest))
                .whereEqualTo("Month",calendarView.getCurrentMonth())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) exists = false;
                            else {
                                existingId = task.getResult().getDocuments().get(0).getId();
                                exists = true;
                            }
                        }
                        else {

                        }
                    }
                });

        // 라디오 버튼 선택 세팅
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_day:
                        shiftKind = 0;
                        break;
                    case R.id.radio_evening:
                        shiftKind = 1;
                        break;
                    case R.id.radio_night:
                        shiftKind = 2;
                        break;
                    case R.id.radio_off:
                        shiftKind = 3;
                        break;
                }
            }
        });

        // D,N,E,O 선택시 세팅
        calendarView.getGridView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.events_id);
                switch (shiftKind) {
                    case 0:
                        textView.setText("Day");
                        break;
                    case 1:
                        textView.setText("Evening");
                        break;
                    case 2:
                        textView.setText("Night");
                        break;
                    case 3:
                        textView.setText("Off");
                        break;
                    case 4:
                        textView.setText("");
                        break;
                }
            }
        });

        //저장 버튼 눌렀을 시
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // map the values to hashmap
                map();
                Log.e("****check****", newDocument.toString());

                final View thisView = view;

                // send to firebase TODO
                if (user.isAdmin()) {
                    firebaseFirestore.collection(getString(R.string.Collection_adminUser));
                }
                else {

                    String currentMonthDocumentId;

                    Log.e("*************","begin");

                    if (exists) {
                        Log.e("*************","exists begin");
                        firebaseFirestore.collection(getString(R.string.Collection_regUser))
                                .document(user.getDocumentId())
                                .collection(getString(R.string.Collection_dutyRequest))
                                .document(existingId)
                                .set(newDocument)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(thisView.getContext());
                                            builder.setTitle("저장");
                                            builder.setMessage("저장 되었습니다.");
                                            builder.show();
                                        }
                                        else {
                                            Log.e("","");
                                        }
                                    }
                                });
                        Log.e("*************","exists end");
                    }
                    else {
                        Log.e("*************","no exists begin");
                        firebaseFirestore.collection(getString(R.string.Collection_regUser))
                                .document(user.getDocumentId())
                                .collection(getString(R.string.Collection_dutyRequest))
                                .add(newDocument)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(thisView.getContext());
                                            builder.setTitle("저장");
                                            builder.setMessage("저장 되었습니다.");
                                            builder.show();
                                        }
                                        else {
                                            Log.e("","");
                                        }
                                    }
                                });
                        Log.e("*************","no exists end");
                    }
                }
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
                                //제출시 저장하는 것 JSON 여기있어야 할듯 //TODO
                            }
                        });
                builder.show();
            }
        });

        //TODO
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
    } // end of onCreate

    public static void map() {

        newDocument.put("Year",calendar.get(Calendar.YEAR));
        newDocument.put("Month",calendar.get(Calendar.MONTH) + 1);

        int size = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int beginFrom = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = beginFrom; i < size+beginFrom; i++) {

            ViewGroup cell = (ViewGroup) calendarView.getGridView().getChildAt(i);
            TextView day = cell.findViewById(R.id.calendar_day);
            TextView event = cell.findViewById(R.id.events_id);

            schedule.add(event.getText().toString());

        }
        newDocument.put("duty",schedule);

    }
}