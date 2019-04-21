package com.kryukov.lab3_2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kryukov.lab3_2.database.HelperFactory;

import java.sql.SQLException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RWAdapter extends RecyclerView.Adapter<RWAdapter.MyViewHolder> {
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        TextView t2;
        TextView t3;

        MyViewHolder(LinearLayout v) {
            super(v);

            t1 = v.findViewById(R.id.student_id);
            t2 = v.findViewById(R.id.fullname);
            t3 = v.findViewById(R.id.added);
        }
    }

    RWAdapter() {}

    @NonNull
    @Override
    public RWAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student;

        try {
            student = HelperFactory.getHelper().getStudentDAO().queryForAll().get(position);
        } catch (SQLException e) {
            e.printStackTrace();

            return;
        }

        holder.t1.setText(String.valueOf(student.getId()));
        holder.t3.setText(
                String.format(
                        "%s %s %s",
                        student.getName(), student.getLastname(), student.getMiddlename()
                )
        );
        holder.t2.setText(student.getAdded().toLocaleString());
    }

    @Override
    public int getItemCount() {
        try {
            return (int) HelperFactory.getHelper().getStudentDAO().countOf();
        } catch (SQLException e) {
            e.printStackTrace();

            return 0;
        }
    }
}