package com.kryukov.lab3_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kryukov.lab3_2.database.HelperFactory;

import java.util.Objects;

public class ShowActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new RWAdapter());
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
}
