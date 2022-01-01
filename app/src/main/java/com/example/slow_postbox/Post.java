package com.example.slow_postbox;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Post extends AppCompatActivity {

    String postId;

    String title;
    String writer;
    String content;
    String postOpenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postId = getIntent().getStringExtra("search_id");

        String REGEX="[0-9]+";

        if (!postId.matches(REGEX)) {
            Toast toast = Toast.makeText(Post.this, "편지 번호가 잘못되었습니다", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            toast.show();
            finish();
        }

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "select * from post where _id="+postId;
        Cursor cursor;
        cursor = db.rawQuery(query,null);


        while (cursor.moveToNext()) {
            title = cursor.getString(1);
            writer = cursor.getString(2);
            content = cursor.getString(3);
            postOpenDate = cursor.getString(4);
        }

        if(title==null) {
            Toast toast = Toast.makeText(Post.this, "해당하는 편지가 없습니다", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            toast.show();
            finish();
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            Date post = null;
            try {
                post = dateFormat.parse(postOpenDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            TextView viewTitle = findViewById(R.id.title);
            TextView viewWriter = findViewById(R.id.writer);
            TextView viewContent = findViewById(R.id.content);

            viewTitle.setText(title);
            viewWriter.setText(writer);
            if (now.before(post)) {
                //내용 열어주지않음
                viewContent.setText("아직 열 수 없습니다\n"+postOpenDate+"부터 열 수 있습니다");
            }
            else {
                viewContent.setText(content);
            }
        }



    }

    public void goBack(View view){
        finish();
    }
}