package com.example.bill.course_selection;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText, editText2;
    private Button button;
    private static final String[] authority = {"Student", "Teacher", "Admin"};
    private ArrayAdapter<String> adapter;
    private MyDBHelper helper;
    private SQLiteDatabase csdb;
    protected static String yourid;
    protected static String password;
    protected static int selectionNum = 1;
    private String pwd;
    protected static String authority_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //used to delete database
//        deleteDatabase(MyDBHelper.db_name);
        this.initDB();
        this.initViews();
        this.initListeners();
    }

    private void initDB() {

        helper = new MyDBHelper(Login.this);
        csdb = helper.getWritableDatabase();
    }

    private void initViews() {

        spinner = (Spinner) findViewById(R.id.spinner);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        adapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_list_item_1, authority);
    }

    private void initListeners() {

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //get the authority
                int position = spinner.getSelectedItemPosition();
                authority_get = adapter.getItem(position);

                //get id and password
                yourid = editText.getText().toString();
                password = editText2.getText().toString();

                queryData();

                if (Objects.equals(password, pwd)) {
                    Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = null;
                    if (Objects.equals(authority_get, "Student")) {
                        intent = new Intent(Login.this, Student.class);

                    } else if (Objects.equals(authority_get, "Teacher")) {
                        intent = new Intent(Login.this, Teacher.class);

                    } else if (Objects.equals(authority_get, "Admin")) {
                        intent = new Intent(Login.this, Administrator.class);

                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Your id/password is wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //read the Database
    private void queryData() {

        String sql = null;
        if (Objects.equals(authority_get, "Student")) {
            sql = "SELECT StudentPassword FROM " + helper.table_student + " WHERE StudentId=" + yourid + ";";

        } else if (Objects.equals(authority_get, "Teacher")) {
            sql = "SELECT TeacherPassword FROM " + helper.table_teacher + " WHERE TeacherId=" + yourid + ";";

        } else if (Objects.equals(authority_get, "Admin")) {
            sql = "SELECT AdminPassword FROM " + helper.table_admin + " WHERE AdminId=" + yourid + ";";

        }

        Cursor curser = csdb.rawQuery(sql, null);
        while (curser.moveToNext()) {
            pwd = curser.getString(0);
        }
    }
}

