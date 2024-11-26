package com.fit2081.basiccalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editTextNumber;
    EditText editTextNumber2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewResult);
        }

    public void onAddButtonClick(View view) {
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);

        String newNumberString = editTextNumber.getText().toString();
        String newNumberString2 = editTextNumber2.getText().toString();

        int firstNumber = Integer.parseInt(newNumberString);
        int secondNumber = Integer.parseInt(newNumberString2);

        double additionResult = firstNumber + secondNumber;

        textView.setText(String.valueOf((int) additionResult));
    }

    public void onSubtractButtonClick(View view) {
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);

        String newNumberString = editTextNumber.getText().toString();
        String newNumberString2 = editTextNumber2.getText().toString();

        int firstNumber = Integer.parseInt(newNumberString);
        int secondNumber = Integer.parseInt(newNumberString2);

        double subtractionResult = firstNumber - secondNumber;

        textView.setText(String.valueOf((int) subtractionResult));
    }

    public void onClearButtonClick(View view) {
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);

        editTextNumber.setText("");
        editTextNumber2.setText("");
        textView.setText("");
    }
}