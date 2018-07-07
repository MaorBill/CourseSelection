package com.example.bill.course_selection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by Bill on 4/5/17.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private static final int version = 1;
    public static final String db_name = "CS6.db";
    public static final String table_student = "Student";
    public static final String table_teacher = "Teacher";
    public static final String table_admin = "Admin";
    public static final String table_course = "Course";
    public static final String table_selection = "Selection";

    public static final String view_student = "StudentView";
    public static final String view_teacher = "TeacherView";
    public static final String view_admin = "AdminView";
    public static String sqld1, sqld2, sqld3, sqld4, sqld5, sqld6, sqld7, sqld8;
    public static String sql1, sql2, sql3, sql4, sql5, sql6, sql7, sql8;

    public MyDBHelper(Context context) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTableAndView(db);
        insertData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db_name, int oldVersion, int newVersion) {

    }

    public void createTableAndView(SQLiteDatabase db){

        //DROP THE TABLE and VIEW
        sqld1 = "DROP TABLE IF EXISTS " + table_student + ";";
        sqld2 = "DROP TABLE IF EXISTS " + table_teacher + ";";
        sqld3 = "DROP TABLE IF EXISTS " + table_admin + ";";
        sqld4 = "DROP TABLE IF EXISTS " + table_course + ";";
        sqld5 = "DROP TABLE IF EXISTS " + table_selection + ";";
        sqld6 = "DROP VIEW IF EXISTS " + view_student + ";";
        sqld7 = "DROP VIEW IF EXISTS " + view_teacher + ";";
        sqld8 = "DROP VIEW IF EXISTS " + view_admin + ";";

        //CREATE TABLE and VIEW
        sql1 = "CREATE TABLE IF NOT EXISTS " + table_student +
                "(StudentId integer primary key, " +
                "StudentPassword text, " +
                "StudentName text);";

        sql2 = "CREATE TABLE IF NOT EXISTS " + table_teacher +
                "(TeacherId integer primary key, " +
                "TeacherPassword text, " +
                "TeacherName text);";

        sql3 = "CREATE TABLE IF NOT EXISTS " + table_admin +
                "(AdminId integer primary key, " +
                "AdminPassword text, " +
                "AdminName text);";

        sql4 = "CREATE TABLE IF NOT EXISTS " + table_course +
                "(CourseId integer primary key, " +
                "CourseName text, " +
                "CourseCredit integer, " +
                "CourseHour integer, " +
                "CourseInfo text);";

        sql5 = "CREATE TABLE IF NOT EXISTS " + table_selection +
                "(SelectionId integer primary key, " +
                "StudentId integer, " +
                "CourseId integer, " +
                "TeacherId integer, " +
                "FOREIGN KEY(StudentId) REFERENCES Student(StudentId), " +
                "FOREIGN KEY(CourseId) REFERENCES Course(CourseId), " +
                "FOREIGN KEY(TeacherId) REFERENCES Teacher(TeacherId));";

        sql6 = "CREATE VIEW IF NOT EXISTS " + view_student +
                " AS SELECT Selection.SelectionId, Selection.StudentId, Course.CourseName, Teacher.TeacherName " +
                "FROM Selection, Teacher, Course " +
                "WHERE Selection.CourseId = Course.CourseId AND Selection.TeacherId = Teacher.TeacherId;";

        sql7 = "CREATE VIEW IF NOT EXISTS " + view_teacher +
                " AS SELECT Selection.SelectionId, Selection.TeacherId, Student.StudentName, Course.CourseName " +
                "FROM Selection, Student, Course " +
                "WHERE Selection.StudentId = Student.StudentId " +
                "AND Selection.CourseId = Course.CourseId;";

        sql8 = "CREATE VIEW IF NOT EXISTS " + view_admin +
                " AS SELECT Selection.SelectionId, Student.StudentName, Teacher.TeacherName, Course.CourseName " +
                "FROM Selection, Student, Teacher, Course " +
                "WHERE Selection.StudentId = Student.StudentId " +
                "AND Selection.CourseId = Course.CourseId " +
                "AND Selection.TeacherId = Teacher.TeacherId;";


        db.beginTransaction();
        try {

//            db.execSQL(sqld1);
//            db.execSQL(sqld2);
//            db.execSQL(sqld3);
//            db.execSQL(sqld4);
//            db.execSQL(sqld5);
//            db.execSQL(sqld6);
//            db.execSQL(sqld7);
//            db.execSQL(sqld8);

            db.execSQL(sql1);
            db.execSQL(sql2);
            db.execSQL(sql3);
            db.execSQL(sql4);
            db.execSQL(sql5);
            db.execSQL(sql6);
            db.execSQL(sql7);
            db.execSQL(sql8);

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private void insertData(SQLiteDatabase db) {

        String sql9, sql10, sql11, sql12, sql13, sql14, sql15, sql16, sql17, sql18, sql19, sql20, sql21, sql22, sql23, sql24, sql25, sql26, sql27, sql28, sql29, sql30, sql31, sql32, sql33, sql34, sql35;
        ArrayList sqls = new ArrayList();

        sql9 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(630101, \"630101\", \"Ryan Benedict\");";
        sql10 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(630102, \"630102\", \"Alva Dunlop\");";
        sql11 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(630103, \"630103\", \"Grace Occam\");";
        sql12 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(630104, \"630104\", \"Owen Isaac\");";
        sql13 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(630105, \"630105\", \"Clement Nico\");";
        sql14 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(640101, \"640101\", \"Barbara Bernal\");";
        sql15 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(640102, \"640102\", \"Sylvia Newman\");";
        sql16 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(640103, \"640103\", \"Florence Wolf\");";
        sql17 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(640104, \"640104\", \"Xavier Bart\");";
        sql18 = "INSERT INTO Student(StudentId, StudentPassword, StudentName) VALUES(640105, \"640105\", \"Horace Eve\");";

        sql19 = "INSERT INTO Teacher(TeacherId, TeacherPassword, TeacherName) VALUES(1, \"123456\", \"Cecil Oscar\");";
        sql20 = "INSERT INTO Teacher(TeacherId, TeacherPassword, TeacherName) VALUES(2, \"123456\", \"Irma Pater\");";
        sql21 = "INSERT INTO Teacher(TeacherId, TeacherPassword, TeacherName) VALUES(3, \"123456\", \"Nicola Eisenhower\");";
        sql22 = "INSERT INTO Teacher(TeacherId, TeacherPassword, TeacherName) VALUES(4, \"123456\", \"Lorraine Blume\");";
        sql23 = "INSERT INTO Teacher(TeacherId, TeacherPassword, TeacherName) VALUES(5, \"123456\", \"Salome Morgan\");";
        sql24 = "INSERT INTO Teacher(TeacherId, TeacherPassword, TeacherName) VALUES(6, \"123456\", \"Lydia Walton\");";

        sql25 = "INSERT INTO Admin(AdminId, AdminPassword, AdminName) VALUES(1,\"admin\", \"admin\");";

        sql26 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(1, \"Operating System\", 4, 64, \"Learn the hardware, operating system and kernel.\");";
        sql27 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(2, \"Java Programming\", 4, 64, \"Learn the basics of Java and apply it.\");";
        sql28 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(3, \"C Programming\", 4, 64, \"Learn the basics of C and apply it.\");";
        sql29 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(4, \"Getting Started with Python\", 2, 32, \"Learn the basic of Python.\");";
        sql30 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(5, \"Getting Started with HTML\", 2, 32, \"Learn the basic of HTML.\");";
        sql31 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(6, \"Linux System Operation\", 2, 32, \"Learn to use Linux.\");";
        sql32 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(7, \"Database\", 4, 64, \"The conception of Database.\");";
        sql33 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(8, \"Outsourcing\", 4, 64, \"Divide task and cooperate with outsourcing company.\");";
        sql34 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(9, \"Human-Computer Interaction\", 4, 64, \"Design program to be more human-friendly.\");";
        sql35 = "INSERT INTO Course(CourseId, CourseName, CourseCredit, CourseHour, CourseInfo) VALUES(10, \"Android Development\", 3, 48, \"Develop application with Android.\");";

        sqls.add(sql9);
        sqls.add(sql10);
        sqls.add(sql11);
        sqls.add(sql12);
        sqls.add(sql13);
        sqls.add(sql14);
        sqls.add(sql15);
        sqls.add(sql16);
        sqls.add(sql17);
        sqls.add(sql18);
        sqls.add(sql19);
        sqls.add(sql20);
        sqls.add(sql21);
        sqls.add(sql22);
        sqls.add(sql23);
        sqls.add(sql24);
        sqls.add(sql25);
        sqls.add(sql26);
        sqls.add(sql27);
        sqls.add(sql28);
        sqls.add(sql29);
        sqls.add(sql30);
        sqls.add(sql31);
        sqls.add(sql32);
        sqls.add(sql33);
        sqls.add(sql34);
        sqls.add(sql35);

        db.beginTransaction();
        try {
            for (int i = 0; i < sqls.size(); i++) {
                db.execSQL((String) sqls.get(i));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}