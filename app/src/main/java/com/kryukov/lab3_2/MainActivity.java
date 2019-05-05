package com.kryukov.lab3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kryukov.lab3_2.database.DatabaseHelper;
import com.kryukov.lab3_2.database.HelperFactory;
import com.kryukov.lab3_2.database.dao.StudentDAO;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper.updateDatabaseVersion(getApplicationContext());

        HelperFactory.setHelper(getApplicationContext());

        try {
            StudentDAO dao = HelperFactory.getHelper().getStudentDAO();

            dao.deleteBuilder().delete();

            for (int i = 0; i < 5; i++) {
                dao.create(dao.generateStudent());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();

        HelperFactory.releaseHelper();
    }

    public void onShow(View view) {
        Intent intent = new Intent(this, ShowActivity.class);
        startActivity(intent);
    }

    public void onAdd(View view) {
        try {
            StudentDAO dao = HelperFactory.getHelper().getStudentDAO();

            Student student = dao.generateStudent();

            dao.create(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onUpdate(View view) {
        try {
            StudentDAO dao = HelperFactory.getHelper().getStudentDAO();

            Student student = dao.queryForFirst(
                    dao.queryBuilder().orderBy("id", false).prepare()
            );

            if (student != null) {
                student.setName("Иван");
                student.setLastname("Иванов");
                student.setMiddlename("Иванович");

                dao.update(student);
            } else {
                Log.i("MainActivity", "onUpdate: student is null!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
