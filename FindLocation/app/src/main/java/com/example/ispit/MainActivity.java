package com.example.ispit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btLocation,btsavedlocation,btsavelocation,btshowmap;
    TextView textView1,textView2,textView3,textView4,textView5;

    Location currentLocation;
    List<Location> savedlocations;

    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLocation = findViewById(R.id.bt_location);
        textView1= findViewById(R.id.text_view1);
        textView2= findViewById(R.id.text_view2);
        textView3= findViewById(R.id.text_view3);
        textView4= findViewById(R.id.text_view4);
        textView5= findViewById(R.id.text_view5);
        btsavelocation= findViewById(R.id.bt_savelocation);
        btsavedlocation = findViewById(R.id.bt_savedlocation);
        btshowmap = findViewById(R.id.bt_showmap);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                  getLocation();
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    }

            }
        });
        btsavelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication myApplication = (MyApplication)getApplicationContext();
                savedlocations = myApplication.getMyLocations();
                savedlocations.add(currentLocation);
            }
        });
        btsavedlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ShowSavedLocationsList.class);
                  startActivity(i);
            }
        });
        btshowmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null)
                {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        textView1.setText(String.valueOf(location.getLatitude()));
                        textView2.setText(String.valueOf(location.getLongitude()));
                        textView3.setText(String.valueOf(location.getAccuracy()));
                        textView4.setText(String.valueOf(location.getSpeed()));
                        textView5.setText(addresses.get(0).getAddressLine(0));
                        currentLocation = location;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else
                    {
                        Toast.makeText(MainActivity.this, "Turn on your GPS", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}