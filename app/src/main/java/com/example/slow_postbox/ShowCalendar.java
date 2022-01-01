package com.example.slow_postbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;


public class ShowCalendar extends AppCompatActivity {

    public String fname=null;
    public String str=null;
    public CalendarView calendarView;
    public Button cha_Btn,del_Btn,save_Btn;
    public TextView diaryTextView,textView2,textView3;
    public EditText contextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_calendar);
        calendarView=findViewById(R.id.calendarView);
        diaryTextView=findViewById(R.id.diaryTextView);
        save_Btn=findViewById(R.id.save_Btn);
        del_Btn=findViewById(R.id.del_Btn);
        cha_Btn=findViewById(R.id.cha_Btn);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        contextEditText=findViewById(R.id.contextEditText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);

                diaryTextView.setText(Integer.toString(year)+"-"+Integer.toString(month+1)+"-"+Integer.toString(dayOfMonth));
                contextEditText.setText("");
                checkDay(year,month,dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str=contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);

            }
        });
    }

    public void  checkDay(int cYear,int cMonth,int cDay){

        fname=""+cYear+"-"+(cMonth+1)+""+"-"+cDay;//저장할 파일 이름설정

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from calendar where date ='"+fname+"'";
        Cursor cursor;
        cursor = db.rawQuery(query, null);
        String searchDate = null;
        String dateContent = null;

        while (cursor.moveToNext()) {
            searchDate = cursor.getString(1);
            dateContent = cursor.getString(2);
        }

        db.close();
        dbHelper.close();

        if (dateContent == null) {
            //해당 날짜에 존재하는 값이 없으면 저장 버튼과 editText 보이게
        }
        else { //존재하는 값이 있다
            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(dateContent);

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);
        }

        cha_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setText(str);

                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                textView2.setText(contextEditText.getText());
            }

        });
        del_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setText("");
                contextEditText.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                removeDiary(fname);
            }
        });
        if (textView2.getText() == null) {
            textView2.setVisibility(View.INVISIBLE);
            diaryTextView.setVisibility(View.VISIBLE);
            save_Btn.setVisibility(View.VISIBLE);
            cha_Btn.setVisibility(View.INVISIBLE);
            del_Btn.setVisibility(View.INVISIBLE);
            contextEditText.setVisibility(View.VISIBLE);
        }
    }
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){

        //해당 날짜 db 지우기
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "delete from calendar where date='"+readDay+"';";
        db.execSQL(query);
        db.close();
        dbHelper.close();
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){

        String content=contextEditText.getText().toString();

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "insert into calendar (date, content) values('"+readDay+"','"+content+"');";
        db.execSQL(query);

        db.close();
        dbHelper.close();
    }
}