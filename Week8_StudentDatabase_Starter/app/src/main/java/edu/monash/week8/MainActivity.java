package edu.monash.week8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.monash.week8.provider.Student;
import edu.monash.week8.provider.StudentViewModel;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Switch switchIsActive;

    EditText etStudentName;
    EditText etStudentId;

    // for storing the list of items, in this case Students
//    ArrayList<String> listItems = new ArrayList<>();
//
//    // Adapter which will bridge the connection between data & the UI
//    ArrayAdapter<String> adapter;
//
//    // to hold the reference to ListView UI element
//    private ListView myListView;

    // this time define an ArrayList of type Student
    ArrayList<Student> listStudents = new ArrayList<>();

    // declare RecyclerAdapter as class variable
    MyRecyclerAdapter recyclerAdapter;

    // recycler view variable
    private RecyclerView recyclerView;

    // A LayoutManager is responsible for measuring and positioning item views within a RecyclerView
    RecyclerView.LayoutManager layoutManager;

    private StudentViewModel studentViewModel;

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
//        myListView = findViewById(R.id.listview);

        // initialise array adapter by providing current activities context
        // layout for each item & the arraylist used as data source
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
//
//        // link the ArrayAdapter to the UI element
//        myListView.setAdapter(adapter);


        // get reference to the recycler view
        recyclerView = findViewById(R.id.recyclerview);

        // A Linear RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        layoutManager = new LinearLayoutManager(this);
        // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        recyclerView.setLayoutManager(layoutManager);


        recyclerAdapter = new MyRecyclerAdapter();
        ///recyclerAdapter.setData(listStudents);
        recyclerView.setAdapter(recyclerAdapter);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        // subscribe to LiveData of type ArrayList<Student>,
        // any changes detected in the database will be notified to MainActivity
        studentViewModel.getAllStudents().observe(this, newData -> {
            // cast List<Student> to ArrayList<Student>
            recyclerAdapter.setData(new ArrayList<Student>(newData));
            recyclerAdapter.notifyDataSetChanged();
        });

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
            deleteAll();
        }
        return true;
    }

    private void deleteAll() {
        etStudentName.setText("");
        etStudentId.setText("");
        switchIsActive.setChecked(false);

//        listStudents.clear();
//        recyclerAdapter.notifyDataSetChanged();
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

//        addItemToListView(studentNameString + " : " + studentIdString);
        Student nStudent = new Student(studentNameString, Integer.parseInt(studentIdString), studentRecordIsActive);
        studentViewModel.insert(nStudent);

        // addStudentToRecyclerView(nStudent);

    }

    /**
     * Use this method to add new item to the ListView
     * This method can be called anywhere inside this MainActivity

     */
//    private void addItemToListView(String currentItem){
//        // add the item to the ArrayList
//        listItems.add(currentItem);
//
//        // inform Adapter, data has changed and this will refresh the UI with updated data
//        adapter.notifyDataSetChanged();
//    }

    /**
     * Use this method to add new student to the RecyclerView
     * This method can be called anywhere inside this MainActivity
     * @param newStudent new instance of Student class
     */
    private void addStudentToRecyclerView(Student newStudent){
        // add the new student to the ArrayList
        listStudents.add(newStudent);

        // inform Adapter, data has changed and this will refresh the UI with updated data
        recyclerAdapter.notifyDataSetChanged();;
    }

    public void onDeleteButtonClick(View v) {
        studentViewModel.deleteAll();
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