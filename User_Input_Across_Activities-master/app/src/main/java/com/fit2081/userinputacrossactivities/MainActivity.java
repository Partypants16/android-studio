package com.fit2081.userinputacrossactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    /**
     * Public method is requirement to Layout can access this method
     *
     * Return Type: Void means nothing to return once function done it's execution
     *
     * @param view Object of class View, without this the JAVA method won't appear
     * as an option in the dropdown of onClick attribute
     */
    public void onGetStudentButtonClick(View view){
// get reference to the UI elements
// findViewById method looks for elements by the Id we set on elements
// and search for them on current Activity's UI
        EditText tvStudentName = findViewById(R.id.editTextName);
        EditText tvStudentId = findViewById(R.id.editTextStudentId);
// using the referenced UI elements we extract values into plain text format
        String studentNameString = tvStudentName.getText().toString();
        String studentIdString = tvStudentId.getText().toString();
// format message to show retrieved student name and Id
        String message = String.format("Hi %s, your student Id is %s", studentNameString, studentIdString);
// display a Toast message using makeText method, with three parameters explained below
// 1. context – The context to use. Usually your android.app.Application or android.app.Activity object.
// 2. text – The text to show. Can be formatted text.
// 3. duration – How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClickNewActivity(View view){
        Intent intent = new Intent(this, ShowStudentDetails.class);
// put some data in the same transaction before we perform launch new activity
// data is need to be put in key-value pairs
// both sender and destination code should know the keys to set/extract values
        EditText tvStudentName = findViewById(R.id.editTextName);
        EditText tvStudentId = findViewById(R.id.editTextStudentId);
        String studentNameString = tvStudentName.getText().toString();
        String studentIdString = tvStudentId.getText().toString();
        intent.putExtra("name", studentNameString);
        intent.putExtra("id", studentIdString);
// finally launch the activity using startActivity method
        startActivity(intent);
    }

    public void saveDataToSharedPreference(String nameValue, int studentIdValue){
// initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
// use .edit function to access file using Editor variable
        SharedPreferences.Editor editor = sharedPreferences.edit();
// save key-value pairs to the shared preference file
        editor.putString("KEY_STUDENT_NAME", nameValue);
        editor.putInt("KEY_STUDENT_ID", studentIdValue);
// use editor.apply() to save data to the file asynchronously (in background without freezing the UI)
// doing in background is very common practice for any File Input/Output operations
        editor.apply();
// or
// editor.commit()
// commit try to save data in the same thread/process as of our user interface
    }
}