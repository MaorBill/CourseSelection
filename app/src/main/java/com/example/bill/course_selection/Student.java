package com.example.bill.course_selection;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Student extends AppCompatActivity{

    private TextView textView;
    private Button button, button2, button3, button4;
    private MyDBHelper helper;
    private SQLiteDatabase csdb;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_student);

        helper = new MyDBHelper(Student.this);
        csdb = helper.getWritableDatabase();

        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        textView.setText(Login.yourid);

        //selecting course
        button.setOnClickListener(new LoginOnClickListener());
        button2.setOnClickListener(new LoginOnClickListener2());
        button3.setOnClickListener(new LoginOnClickListener3());
        button4.setOnClickListener(new LoginOnClickListener4());
    }

    //Select Course
    public class LoginOnClickListener implements OnClickListener{
        public void onClick(View v) {
            queryData();
            if(cursor.getCount() < 3){
                String result = "You can select " + (3-cursor.getCount()) + " more courses!";
                Toast.makeText(Student.this, result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Student.this, StudentSe.class);
                startActivity(intent);
            }else {
                String result = "You have selected 3 courses before!";
                Toast.makeText(Student.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //View Course
    public class LoginOnClickListener2 implements OnClickListener{
        public void onClick(View v) {
            queryData();
            if (cursor.getCount() == 0) {
                String result = "You haven't select the course yet!";
                Toast.makeText(Student.this, result, Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(Student.this,StudentVi.class);
                startActivity(intent);
            }
        }
    }

    public class LoginOnClickListener3 implements OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(Student.this,Chpwd.class);
            startActivity(intent);
        }
    }

    public class LoginOnClickListener4 implements OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(Student.this,Login.class);
            startActivity(intent);
        }
    }

    private void queryData(){
        String sql = "SELECT SelectionId,CourseName,TeacherName FROM "+ helper.view_student +" WHERE StudentId =" + Login.yourid + ";";
        cursor = csdb.rawQuery(sql, null);
    }


}
