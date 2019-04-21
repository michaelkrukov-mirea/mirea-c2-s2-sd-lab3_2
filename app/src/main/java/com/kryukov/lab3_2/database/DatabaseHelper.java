package com.kryukov.lab3_2.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kryukov.lab3_2.Student;
import com.kryukov.lab3_2.database.dao.StudentDAO;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="lab3_2.db";

    private static final int DATABASE_VERSION = 2;

    private StudentDAO StudentDAO;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, Student.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){

        db.beginTransaction();

        try{
            if (oldVer == 1) {
                db.execSQL("BEGIN TRANSACTION");
                db.execSQL("CREATE TEMPORARY TABLE students_backup(id, fullname, added)");
                db.execSQL("INSERT INTO students_backup SELECT id, fullname, added FROM students");
                db.execSQL("DROP TABLE students");
                db.execSQL("CREATE TABLE students (id integer primary key, name text not null, lastname text not null, middlename text not null, added text not null)");

                Cursor cursor = db.query(
                        "students_backup",
                        new String[] {"id", "fullname", "added"},
                        null,
                        new String[]{},
                        null,
                        null,
                        null,
                        null
                );

                try {
                    while (cursor.moveToNext()) {
                        String[] fullname = cursor.getString(1).split(" ");

                        db.execSQL(
                                "INSERT INTO `students`(id, name, lastname, middlename, added) VALUES (?, ?, ?, ?, ?)",
                                new String[]{
                                        cursor.getString(0), fullname[0],
                                        fullname[1], fullname[2], cursor.getString(2)
                                }
                        );
                    }
                } finally {
                    cursor.close();
                }

                db.execSQL("DROP TABLE students_backup");

                db.setTransactionSuccessful();
            }
        }
        finally {
            db.endTransaction();
        }

        oldVer += 1;

        // Next migrations
    }

    //синглтон для StudentDAO
    public StudentDAO getStudentDAO() throws SQLException{
        if(StudentDAO == null){
            StudentDAO = new StudentDAO(getConnectionSource(), Student.class);
        }

        return StudentDAO;
    }

    @Override
    public void close(){
        super.close();

        StudentDAO = null;
    }
}
