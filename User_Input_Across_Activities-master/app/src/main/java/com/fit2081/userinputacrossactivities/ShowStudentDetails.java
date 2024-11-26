package com.fit2081.userinputacrossactivities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ShowStudentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_student_details);

        String studentName = getIntent().getExtras().getString("name");
        String studentId = getIntent().getExtras().getString("id");

        TextView tvName = findViewById(R.id.textViewStudentName);
        TextView tvId = findViewById(R.id.textViewStudentId);

        tvName.setText(studentName);
        tvId.setText(studentId);
        }
    }

