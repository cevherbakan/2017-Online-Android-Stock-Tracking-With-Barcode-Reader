package com.neurobin.aapps.datepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NOTLAR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notlar);
        Button ekleBtn=(Button)findViewById(R.id.button16);
        Button notlarBtn=(Button)findViewById(R.id.button15);
        Button silBtn=(Button)findViewById(R.id.button17);



        ekleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NOTLAR.this,NOT_EKLE.class);
                startActivity(i);
            }
        });

        notlarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NOTLAR.this,TUM_NOTLAR.class);
                startActivity(i);
            }
        });

        silBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference sil= FirebaseDatabase.getInstance().getReference().child("not");
                sil.removeValue();

            }
        });


    }
}
