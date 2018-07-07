package com.example.bill.course_selection;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentSe extends AppCompatActivity{

    private ListView list;
    private int[] CourseId = {1,2,3,4,5,6,7,8,9,10};
    private String[] CourseName = {
            "Operating System", "Java Programming", "C Programming",
            "Getting Started with Python", "Getting Started with HTML",
            "Linux System Operation", "Database", "Outsourcing",
            "Human-Computer Interaction", "Android Development"};
    private int[] TeacherId = {1,3,1,2,4,1,4,2,4,3};
    private String[] TeacherName = {
            "Cecil Oscar", "Nicola Eisenhower", "Cecil Oscar",
            "Irma Pater", "Lorraine Blume", "Cecil Oscar",
            "Lorraine Blume", "Irma Pater", "Lorraine Blume", "Nicola Eisenhower"};
    private String[] Intro = {
            "Introduction: Learn the hardware, operating system and kernel.",
            "Introduction: Learn the basics of Java and apply it.",
            "Introduction: Learn the basics of C and apply it.",
            "Introduction: Learn the basic of Python.",
            "Introduction: Learn the basics of HTML.",
            "Introduction: Learn to use Linux System.",
            "Introduction: The conception of Database.",
            "Introduction: Divide task and cooperate with outsourcing company.",
            "Introduction: Design program to be more human-friendly.",
            "Introduction: Develop application with Android."
    };
    
    private Button button;
    private int flag;
    private ArrayList<Integer> course = new ArrayList();
    private MyDBHelper helper;
    private SQLiteDatabase csdb;
    private boolean[] course_button = new boolean[10];

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_studentse);

        initDB();

        button = (Button) findViewById(R.id.button);
        list = (ListView) findViewById(R.id.listView);

        List<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("CourseId", String.valueOf(CourseId[i]));
            map.put("CourseName", CourseName[i]);
            map.put("TeacherId", String.valueOf(TeacherId[i]));
            map.put("TeacherName", TeacherName[i]);
            map.put("Intro", Intro[i]);
            myList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(StudentSe.this, myList, R.layout.studentse_list,
                new String[]{"CourseId", "CourseName", "TeacherId", "TeacherName", "Intro"},
                new int[]{R.id.textView, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                final Button button = (Button) view.findViewById(R.id.button_ch);

                queryData();

                if(course_button[position]==true)
                    button.setText("selected");
                else
                    button.setText("choose");

                final Integer index = position;

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if(flag < 3){
                            if(button.getText().toString()=="choose"){
                                button.setText("selected");
                                course_button[index] = true;
                                course.add(index);
                                flag++;

                            }else if(button.getText().toString()=="selected"){
                                button.setText("choose");
                                course_button[index] = false;
                                for(int i=0; i<course.size(); i++){
                                    if(course.get(i)==index){
                                        course.set(i, null);
                                    }
                                }
                                flag--;
                            }
                            Toast.makeText(StudentSe.this, "You have chose " + flag + " Courses.", Toast.LENGTH_SHORT).show();
                        } else if(flag >= 3){
                            Toast.makeText(StudentSe.this, "You can't choose more than 3 courses!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return view;
            }
        };

        list.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StudentSe.this, Student.class);
                Toast.makeText(StudentSe.this, "Selected Successful!", Toast.LENGTH_SHORT).show();
                for(int i=0; i<course.size(); i++){

                    insertData(Login.selectionNum, Login.yourid, CourseId[(int) course.get(i)], TeacherId[(int) course.get(i)]);
                    Login.selectionNum++;
                }
                course.clear();
                startActivity(intent);
            }
        });
    }

    protected void initDB(){
        helper = new MyDBHelper(StudentSe.this);
        csdb = helper.getWritableDatabase();
    }

    protected void insertData(int SelectionId, String StudentId, int CourseId, int TeacherId) {

        String sql = "INSERT INTO Selection(SelectionId, StudentId, CourseId, TeacherId) VALUES (" + SelectionId + "," + StudentId + "," + CourseId + "," + TeacherId + ");";
        csdb.execSQL(sql);
    }

    private void queryData() {

        flag = 0;
        String sql = "SELECT SelectionId,CourseName,TeacherName FROM " + helper.view_student + " WHERE StudentId =" + Login.yourid + ";";
        Cursor cursor = csdb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            flag++;
        }
    }

}
