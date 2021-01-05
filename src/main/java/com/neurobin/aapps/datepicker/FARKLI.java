package com.neurobin.aapps.datepicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FARKLI extends AppCompatActivity {

    private String islem;
    private String islemnotext;
    private String strTarih;

    private Button araBtn;
    private EditText islemnoEdt,tarihEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farkli);

        islemnoEdt = (EditText) findViewById(R.id.islemnoEdt);
        tarihEdt=(EditText)findViewById(R.id.tarihEdt);
        araBtn=(Button)findViewById(R.id.araBtn);

        araBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                islemnotext = islemnoEdt.getText().toString();
                strTarih=tarihEdt.getText().toString();
                islem = (strTarih+":" + islemnotext);

                DatabaseReference oku = FirebaseDatabase.getInstance().getReference().child("islemler").child(islem);

                ValueEventListener dinle = new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        TextView tv = (TextView) findViewById(R.id.textView14);
                        URUNSATIS k = new URUNSATIS();
                        k = dataSnapshot.getValue(URUNSATIS.class);
                        tv.setText("\n tarih:   " + k.tarih + "\n saat:   " + k.saat + "\n içerik:  " + k.veriler + "\n toplam tutar:  " + k.toplam);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                oku.addValueEventListener(dinle);


            }
        });




    }
}
