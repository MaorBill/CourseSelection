package com.example.bill.course_selection;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by Bill on 25/05/2017.
 */

public class Chpwd extends AppCompatActivity {

    private EditText editText, editText2;
    private Button button, button2;
    private MyDBHelper helper;
    private SQLiteDatabase csdb;
    private static String authority_get, yourid, oldpwd, newpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chpwd);

        this.initDB();
        this.initViews();
        this.initListeners();
    }

    private void initDB() {

        helper = new MyDBHelper(Chpwd.this);
        csdb = helper.getWritableDatabase();
    }

    private void initViews() {

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);

    }

    private void initListeners() {

        //get authority, id and password
        authority_get = Login.authority_get;
        yourid = Login.yourid;
        oldpwd = editText.getText().toString();
        newpwd = editText2.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (Objects.equals(authority_get, "Student")) {
                    intent = new Intent(Chpwd.this, Student.class);

                } else if (Objects.equals(authority_get, "Teacher")) {
                    intent = new Intent(Chpwd.this, Teacher.class);

                } else if (Objects.equals(authority_get, "Admin")) {
                    intent = new Intent(Chpwd.this, Administrator.class);

                }
                startActivity(intent);
            }
        });
    }

    //read the Database
    private void updateData() {

        String sql = null;
        Intent intent = null;
        if (Objects.equals(authority_get, "Student")) {
            sql = "UPDATE " + helper.table_student + " SET StudentPassword='" + newpwd + "' WHERE StudentId=" + yourid +";";
            intent = new Intent(Chpwd.this, Student.class);

        } else if (Objects.equals(authority_get, "Teacher")) {
            sql = "UPDATE " + helper.table_teacher + " SET TeacherPassword='" + newpwd + "' WHERE TeacherId=" + yourid +";";
            intent = new Intent(Chpwd.this, Teacher.class);

        } else if (Objects.equals(authority_get, "Admin")) {
            sql = "UPDATE " + helper.table_admin + " SET AdminPassword='" + newpwd + "' WHERE AdminId=" + yourid +";";
            intent = new Intent(Chpwd.this, Administrator.class);

        }
        csdb.execSQL(sql);
        Toast.makeText(Chpwd.this, "Your password has changed!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}

