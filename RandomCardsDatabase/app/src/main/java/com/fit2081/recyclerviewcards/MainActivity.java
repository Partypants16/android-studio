package com.fit2081.recyclerviewcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> data = new ArrayList<>();
    String suits[] = {"Hearts", "Diamonds", "Clubs", "Spade"};
    String cards[] = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    Button btn;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerAdapter adapter;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.add_item);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        recyclerView = findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager


        // make sure the restore is done, before setting the data to RecyclerAdapter
        restoreArrayListFromText();

        adapter = new MyRecyclerAdapter();
        adapter.setData(data);
        recyclerView.setAdapter(adapter);
    }

    public void addItem() {
        Random random = new Random();
        int randCard = random.nextInt(cards.length);
        int randSuit = random.nextInt(suits.length);
        Item item = new Item(suits[randSuit], cards[randCard]);
        data.add(item);
        adapter.notifyDataSetChanged();


        // save the whole list to SharedPreferences on each addItem
        saveArrayListAsText();
    }

    private void saveArrayListAsText(){
        // convert array list to String
        String arrayListString = gson.toJson(data);

        // Save to shared preferences
        SharedPreferences.Editor edit = getPreferences(MODE_PRIVATE).edit();
        edit.putString("data_key", arrayListString);
        edit.apply();
    }

    private void restoreArrayListFromText(){
        // restore data from SharedPreferences
        String arrayListStringRestored = getPreferences(MODE_PRIVATE).getString("data_key", "[]");

        // Convert the restored string back to ArrayList
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        data = gson.fromJson(arrayListStringRestored,type);
    }

}
