package com.example.ispit;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ShowSavedLocationsList extends AppCompatActivity {

    ListView lvsavedlocations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved_locations_list);

        lvsavedlocations= findViewById(R.id.lv_locations);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        List<Location> savedlocations = myApplication.getMyLocations();

        lvsavedlocations.setAdapter(new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1,savedlocations));
    }
}