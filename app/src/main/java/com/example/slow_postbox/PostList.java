package com.example.slow_postbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PostList extends AppCompatActivity {

    ListView listView;
    String searchId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        listView = findViewById(R.id.listview);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from post";
        Cursor cursor = db.rawQuery(query, null);

        String[] strs = new String[]{"_id", "title", "writer"};
        int[] ints = new int[] {R.id.listview_txt1, R.id.listview_txt2, R.id.listview_txt3};

        SimpleCursorAdapter adapter = null;
        adapter = new SimpleCursorAdapter(listView.getContext(), R.layout.listview_txt, cursor, strs, ints, 0);

        listView.setAdapter(adapter);

    }

    public void openPost(View view) {
        EditText tmp = (EditText)findViewById(R.id.searchId);
        searchId = tmp.getText().toString();


        Intent intent = new Intent(PostList.this, Post.class);
        intent.putExtra("search_id",searchId);
        startActivity(intent);

    }



}