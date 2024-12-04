package edu.monash.edittextsexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Switch switchIsActive;

    EditText etStudentName;
    EditText etStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to the UI elements
        // findViewById method looks for elements by the Id we set on elements
        // and search for them on current Activity's UI
        etStudentName = findViewById(R.id.editTextName);
        etStudentId = findViewById(R.id.editTextStudentId);
        switchIsActive = findViewById(R.id.switchIsActive);

        sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);
    }


    /**
     * Public method is requirement to Layout can access this method
     *
     * Return Type: Void means nothing to return once function done it's execution
     *
     * @param view Object of class View, without this the JAVA method won't appear
     *             as an option in the dropdown of onClick attribute
     */
    public void onSaveButtonClick(View view){
        // using the referenced UI elements we extract values into plain text format
        String studentNameString = etStudentName.getText().toString();
        String studentIdString = etStudentId.getText().toString();
        boolean studentRecordIsActive = switchIsActive.isChecked();

        // format message to show retrieved student name and Id
        String message = String.format("Hi %s, your student Id is %s", studentNameString, studentIdString);

        // display a Toast message using makeText method, with three parameters explained below
        // 1. context – The context to use. Usually your android.app.Application or android.app.Activity object.
        // 2. text – The text to show. Can be formatted text.
        // 3. duration – How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Initialise the SharedPreference class  == File to store data
        //SharedPreferences sharedPreferences = getSharedPreferences("FileName.txt", MODE_PRIVATE);

        // use new SharedPreference reference to access the editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // prepare data in key value format
        editor.putString(KeyStore.KEY_STUDENT_NAME, studentNameString);
        editor.putInt(KeyStore.KEY_STUDENT_ID, Integer.parseInt(studentIdString));
        editor.putBoolean(KeyStore.KEY_RECORD_ACTIVE, studentRecordIsActive);

        // commit or apply the changes to the file
        // editor.commit(); // does saving in the same thread
        // or
        editor.apply(); // async saving of data
    }

    public void onRestoreButtonClick(View v){
        String studentNameRestored = sharedPreferences.getString(KeyStore.KEY_STUDENT_NAME, "John Default");
        int studentIdRestored = sharedPreferences.getInt(KeyStore.KEY_STUDENT_ID, 999);
        boolean isActiveRestored = sharedPreferences.getBoolean(KeyStore.KEY_RECORD_ACTIVE, false);

        etStudentName.setText(studentNameRestored);
        etStudentId.setText(String.valueOf(studentIdRestored));
        switchIsActive.setChecked(isActiveRestored);
    }
}