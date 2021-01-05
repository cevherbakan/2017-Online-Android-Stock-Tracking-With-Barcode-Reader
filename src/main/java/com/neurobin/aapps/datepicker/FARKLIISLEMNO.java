package com.neurobin.aapps.datepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FARKLIISLEMNO extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView islemNoList;
    private ArrayList<String> islemNoAdapter=new ArrayList<>();
    private String tarih;

    private Button bulBtn;
    private EditText tarihEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farkliislemno);

        bulBtn=(Button)findViewById(R.id.araBtn);
        tarihEdt=(EditText)findViewById(R.id.tarihEdt);

        bulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tarih=(tarihEdt.getText().toString());

                mDatabase= FirebaseDatabase.getInstance().getReference().child(tarih);
                islemNoList=(ListView)findViewById(R.id.listeList);

                final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(FARKLIISLEMNO.this,android.R.layout.simple_list_item_1,islemNoAdapter);
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
        });

    }
}
