package com.example.bill.course_selection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Bill on 4/8/17.
 */

public class StudentVi extends AppCompatActivity {

    private ListView list;
    private static ArrayList selectionid = new ArrayList();
    private static ArrayList<String> coursename = new ArrayList();
    private static ArrayList<String> teachername = new ArrayList();
    private MyDBHelper helper;
    private SQLiteDatabase csdb;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentvi);

        initDB();
        initViews();
        queryData();
        initListenerList();
        initListenerButton();

    }

    private void initDB() {
        helper = new MyDBHelper(StudentVi.this);
        csdb = helper.getWritableDatabase();
    }

    private void initViews() {
        list = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
    }

    private void initListenerButton() {

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteData();
                editText.setText(null);
                initListenerList();
            }
        });
    }

    private void initListenerList() {

        ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < selectionid.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("SelectionId", String.valueOf(selectionid.get(i)));
            map.put("CourseName", coursename.get(i));
            map.put("TeacherName", teachername.get(i));
            myList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(StudentVi.this, myList,
                R.layout.studentvi_list, new String[]{"SelectionId", "CourseName", "TeacherName"},
                new int[]{R.id.textView, R.id.textView2, R.id.textView3});
        list.setAdapter(adapter);
    }

    private void queryData() {

        selectionid.clear();
        coursename.clear();
        teachername.clear();
        String sql = "SELECT SelectionId,CourseName,TeacherName FROM " + helper.view_student + " WHERE StudentId =" + Login.yourid + ";";
        Cursor cursor = csdb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            selectionid.add(cursor.getInt(0));
            coursename.add(cursor.getString(1));
            teachername.add(cursor.getString(2));
        }
    }

    private void deleteData() {

        String sql = "DELETE FROM Selection WHERE SelectionId=" + editText.getText().toString() + ";";
        csdb.execSQL(sql);
        for (int i = 0; i < selectionid.size(); i++) {

            if (Objects.equals(String.valueOf(selectionid.get(i)), editText.getText().toString())) {
                selectionid.remove(i);
                coursename.remove(i);
                teachername.remove(i);
            }
        }
    }
}