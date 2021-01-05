package com.neurobin.aapps.datepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class URUN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun);
        Button ara=(Button)findViewById(R.id.urun_ara);
        Button kayit=(Button)findViewById(R.id.urun_kayit);

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(URUN.this,URUN_ARAMA.class);
                startActivity(i);
            }
        });
        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(URUN.this,URUN_KAYIT.class);
                startActivity(i);
            }
        });
    }
}
