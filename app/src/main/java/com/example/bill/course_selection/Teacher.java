package com.example.bill.course_selection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bill on 4/5/17.
 */

public class Teacher extends AppCompatActivity {

    private ListView list;
    private ArrayList selectionid = new ArrayList();
    private ArrayList coursename = new ArrayList();
    private ArrayList studentname = new ArrayList();
    private MyDBHelper helper;
    private SQLiteDatabase csdb;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        initDB();
        initViews();
        queryData();
        initListener();

    }

    protected void initDB(){
        helper = new MyDBHelper(Teacher.this);
        csdb = helper.getWritableDatabase();
    }

    protected void initViews(){
        list = (ListView) findViewById(R.id.listView);

    }

    private void initListener(){
        ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < selectionid.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("SelectionId", String.valueOf(selectionid.get(i)));
            map.put("CourseName", (String) coursename.get(i));
            map.put("StudentName", (String) studentname.get(i));
            myList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(Teacher.this, myList,
                R.layout.teacher_list, new String[]{"SelectionId", "CourseName", "StudentName"},
                new int[]{R.id.textView, R.id.textView2, R.id.textView3});
        list.setAdapter(adapter);
    }

    private void queryData(){

        selectionid.clear();
        coursename.clear();
        studentname.clear();
        String sql = "SELECT SelectionId,CourseName,StudentName FROM " + helper.view_teacher + " WHERE TeacherId =" + Login.yourid + ";";
        Cursor cursor = csdb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            selectionid.add(cursor.getInt(0));
            coursename.add(cursor.getString(1));
            studentname.add(cursor.getString(2));
        }
    }
}
