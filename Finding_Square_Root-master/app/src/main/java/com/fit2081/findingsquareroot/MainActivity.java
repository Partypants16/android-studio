package com.fit2081.findingsquareroot;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // class variables, this way these variables can be accessible in all methods of this class
    EditText editTextNumber;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // all UI initialisation goes inside onCreate method
        editTextNumber = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textViewResult);
        }
    public void onButtonClick(View view) {
// get String from the editTextNumber class variable
        String newNumberString = editTextNumber.getText().toString();
// parse the string value to integer, so we can perform mathematical operations
        int newNumber = Integer.parseInt(newNumberString);
// find the square root
        double result = Math.sqrt(newNumber);
// set the value back to UI
        textView.setText(String.valueOf((int) result));
// try uncommenting the line below and see what happens
// discuss this with the tutors in your lab
// textView.setText((int) result);
    }
}
