package com.neurobin.aapps.datepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ISLEMLER extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islemler);

        Button bt=(Button)findViewById(R.id.gunluk_islem);
        Button bt2=(Button)findViewById(R.id.aylik_islem);
        Button bt3=(Button)findViewById(R.id.button11);
        Button bt4=(Button)findViewById(R.id.button13);



        TextView tarih=(TextView)findViewById(R.id.tarih);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ISLEMLER.this,BUGUNKI.class);
                startActivity(i);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ISLEMLER.this,FARKLI.class);
                startActivity(i);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ISLEMLER.this,BUGUNISLEMNO.class);
                startActivity(i);
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ISLEMLER.this,FARKLIISLEMNO.class);
                startActivity(i);
            }
        });




    }
}

