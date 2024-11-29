package edu.monash.week6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Switch switchIsActive;

    EditText etStudentName;
    EditText etStudentId;

    // for storing the list of items, in this case Students
    ArrayList<String> listItems = new ArrayList<>();

    // Adapter which will bridge the connection between data & the UI
    ArrayAdapter<String> adapter;

    // to hold the reference to ListView UI element
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get reference to the UI elements
        // findViewById method looks for elements by the Id we set on elements
        // and search for them on current Activity's UI
        etStudentName = findViewById(R.id.editTextName);
        etStudentId = findViewById(R.id.editTextStudentId);
        switchIsActive = findViewById(R.id.switchIsActive);

        // get reference to ListView
        myListView = findViewById(R.id.listview);

        // initialise array adapter by providing current activities context
        // layout for each item & the arraylist used as data source
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);

        // link the ArrayAdapter to the UI element
        myListView.setAdapter(adapter);

        sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        MyCustomBroadcastReceiver myCustomBroadcastReceiver = new MyCustomBroadcastReceiver();
        registerReceiver(
                myCustomBroadcastReceiver,
                new IntentFilter(SMSReceiver.SMS_FILTER),
                RECEIVER_EXPORTED
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.option_add_student) {
            onSaveButtonClick(null);
        } else if (itemId == R.id.option_clear_student) {
            clearFields();
        } else if (itemId == R.id.option_restore_student) {
            onRestoreButtonClick(null);
        }
        return true;
    }

    private void clearFields() {
        etStudentName.setText("");
        etStudentId.setText("");
        switchIsActive.setChecked(false);

        listItems.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * Public method is requirement to Layout can access this method
     * <p>
     * Return Type: Void means nothing to return once function done it's execution
     *
     * @param view Object of class View, without this the JAVA method won't appear
     *             as an option in the dropdown of onClick attribute
     */
    public void onSaveButtonClick(View view) {
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

        addItemToListView(studentNameString + " : " + studentIdString);
    }

    /**
     * Use this method to add new item to the ListView
     * This method can be called anywhere inside this MainActivity
     * @param currentItem String representing the item to be added
     */
    private void addItemToListView(String currentItem){
        // add the item to the ArrayList
        listItems.add(currentItem);

        // inform Adapter, data has changed and this will refresh the UI with updated data
        adapter.notifyDataSetChanged();
    }

    public void onRestoreButtonClick(View v) {
        String studentNameRestored = sharedPreferences.getString(KeyStore.KEY_STUDENT_NAME, "John Default");
        int studentIdRestored = sharedPreferences.getInt(KeyStore.KEY_STUDENT_ID, 999);
        boolean isActiveRestored = sharedPreferences.getBoolean(KeyStore.KEY_RECORD_ACTIVE, false);

        etStudentName.setText(studentNameRestored);
        etStudentId.setText(String.valueOf(studentIdRestored));
        switchIsActive.setChecked(isActiveRestored);
    }

    class MyCustomBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedMessage = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            myStringTokenizer(context, receivedMessage);
        }

        /**
         * String Tokenizer is used to parse the incoming message
         * The protocol is to have the account holder name and account number separate by a semicolon
         *
         * @param context is carry forward Context to show the Toast message
         * @param msg     is the incoming message that needs to be tokenized.
         **/
        public void myStringTokenizer(Context context, String msg) {
            try {
                StringTokenizer sT = new StringTokenizer(msg, ";");
                String studentName = sT.nextToken();
                String studentIdString = sT.nextToken();
                String isActiveRecordString = sT.nextToken();

                // if parsing the values to any of the data type fails a run time exception will be raised
                int studentId = Integer.parseInt(studentIdString);
                boolean isActiveRecord = Boolean.parseBoolean(isActiveRecordString);

                etStudentName.setText(studentName);
                etStudentId.setText(String.valueOf(studentId));
                switchIsActive.setChecked(isActiveRecord);
            } catch (Exception ex) {
                Toast.makeText(context, "Invalid incoming message", Toast.LENGTH_LONG).show();
            }
        }
    }
}