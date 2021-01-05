package com.neurobin.aapps.datepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BUGUNISLEMNO extends AppCompatActivity {

    Calendar calendar;
    SimpleDateFormat format;
    private DatabaseReference mDatabase;
    private ListView islemNoList;
    private ArrayList<String> islemNoAdapter=new ArrayList<>();

    private String tarih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bugunislemno);

        format=new SimpleDateFormat("yyyy:MM:dd");
        calendar=Calendar.getInstance();
        tarih=(format.format(calendar.getTime()));


        mDatabase= FirebaseDatabase.getInstance().getReference().child(tarih);
        islemNoList=(ListView)findViewById(R.id.listeList);

        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,islemNoAdapter);
        islemNoList.setAdapter(arrayAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue(String.class);
                islemNoAdapter.add(value);
                arrayAdapter.notifyDataSetChanged();
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
}

