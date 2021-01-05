package com.neurobin.aapps.datepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SATIS extends AppCompatActivity {

    FirebaseDatabase database2=FirebaseDatabase.getInstance();
    DatabaseReference myRef = database2.getReference();

    final Activity activity=this;

    Calendar calendar;
    SimpleDateFormat format;
    SimpleDateFormat format2;
    SimpleDateFormat format3;
    SimpleDateFormat format4;

    private Button satisKayitBtn,barkodEkleBtn,barkodAraBtn,fiyatEkleBtn;
    private EditText fiyatEdt,adetEdt,barkodEdt;
    private TextView toplamTxt;
    private ListView liste;

    private double toplam=0;
    private int i=0;
    int adetInt;
    private String islem_no,islem_tarih,saat,tarih;
    private String veriler="";

    ArrayList<String> itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis);

        format=new SimpleDateFormat("yyyy:MM:dd:HHmmssSS");
        format2=new SimpleDateFormat("HH:mm:ss");
        format3=new SimpleDateFormat("yyyy:MM:dd");
        format4=new SimpleDateFormat("HHmmssSS");

        liste=(ListView)findViewById(R.id.listeList);
        adetEdt=(EditText)findViewById(R.id.adetEdt);
        toplamTxt=(TextView)findViewById(R.id.toplamTxt);
        fiyatEkleBtn=(Button)findViewById(R.id.fiyatEkleBtn);
        barkodAraBtn = (Button)findViewById(R.id.barkodBtn);
        satisKayitBtn=(Button)findViewById(R.id.satisKayitBtn);
        barkodEdt=(EditText)findViewById(R.id.barkodEdt);
        barkodEkleBtn=(Button)findViewById(R.id.barkodEkleBtn);
        fiyatEdt=(EditText)findViewById(R.id.fiyatEdt);


        itemlist=new ArrayList<String>();
        adetEdt.setText("1");

        final ArrayAdapter<String> adaptor = new ArrayAdapter<String>(SATIS.this, android.R.layout.simple_list_item_multiple_choice, itemlist);




        fiyatEkleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toplam=toplam+Double.parseDouble(fiyatEdt.getText().toString());
                veriler =veriler+"****"+"barkodsuz ürün"+" "+fiyatEdt.getText().toString()+"₺ ****";
                itemlist.add("****"+"barkodsuz ürün"+" "+fiyatEdt.getText().toString()+"₺ ****");
                adaptor.notifyDataSetChanged();
                liste.setAdapter(adaptor);

                toplamTxt.setText("Toplam fiyat: " + String.valueOf(toplam) + "₺");
                fiyatEdt.setText("");
                adetEdt.setText("1");
                barkodEdt.setText("");

            }
        });

        satisKayitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();

                tarih=(format.format(calendar.getTime()));
                saat=(format2.format(calendar.getTime()));
                islem_tarih=(format3.format(calendar.getTime()));
                islem_no=(format4.format(calendar.getTime()));

                ISLEM_KAYIT(islem_tarih,islem_no);

                URUN_KAYIT(veriler,tarih,saat,toplam);
                Intent i=new Intent(SATIS.this,MainActivity.class);
                startActivity(i);

            }
        });

        barkodAraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

        barkodEkleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference oku= FirebaseDatabase.getInstance().getReference().child("urun").child(barkodEdt.getText().toString());

                ValueEventListener dinle=new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ISLEM_KAYIT k2=new ISLEM_KAYIT();
                        k2=dataSnapshot.getValue(ISLEM_KAYIT.class);

                        Urunler k=new Urunler();
                        k=dataSnapshot.getValue(Urunler.class);

                        adetInt = Integer.parseInt(adetEdt.getText().toString());

                        String satir=("****"+adetInt+"x"+k.urun_ad+" "+k.urun_fiyat+"₺ ****");

                        itemlist.add("****"+adetInt+"x"+k.urun_ad+" "+k.urun_fiyat+"₺ ****");
                        toplam=toplam+(k.urun_fiyat*adetInt);

                        adaptor.notifyDataSetChanged();
                        liste.setAdapter(adaptor);
                        toplamTxt.setText("Toplam fiyat: " + String.valueOf(toplam) + "₺");

                        veriler =veriler+satir;
                        fiyatEdt.setText("");
                        adetEdt.setText("1");
                        barkodEdt.setText("");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "barkod hatası!!", Toast.LENGTH_LONG).show();

                    }
                };

                oku.addValueEventListener(dinle);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivitiy", "Cancelled scan");
                Toast.makeText(this, "Okunmadı", Toast.LENGTH_LONG).show();

            }

            else
                {
                Log.d("MainActivity", "Okundu");
                Toast.makeText(this, "Barkod:" + result.getContents(), Toast.LENGTH_LONG).show();
                barkodEdt.setText(result.getContents());

                final ArrayAdapter<String> adaptor = new ArrayAdapter<String>(SATIS.this, android.R.layout.simple_list_item_multiple_choice, itemlist);


                DatabaseReference okum= FirebaseDatabase.getInstance().getReference().child("urun").child(barkodEdt.getText().toString());

                ValueEventListener dinleme=new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        adetInt = Integer.parseInt(adetEdt.getText().toString());

                        ISLEM_KAYIT k2=new ISLEM_KAYIT();
                        k2=dataSnapshot.getValue(ISLEM_KAYIT.class);

                        Urunler k=new Urunler();

                        k=dataSnapshot.getValue(Urunler.class);


                        String satir=("****"+adetInt+"x"+k.urun_ad+" "+k.urun_fiyat+"₺ ****");

                        itemlist.add("****"+adetInt+"x"+k.urun_ad+" "+k.urun_fiyat+"₺ ****");
                        toplam=toplam+(k.urun_fiyat*adetInt);
                        toplamTxt.setText("Toplam fiyat: " + String.valueOf(toplam) + "₺");

                        adaptor.notifyDataSetChanged();
                        liste.setAdapter(adaptor);
                        veriler =veriler+satir;

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };

                okum.addValueEventListener(dinleme);
            }
        }

        else
            {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    private void URUN_KAYIT(String veriler,String tarih,String saat,double toplam){

        URUNSATIS sat=new URUNSATIS(tarih,saat,veriler,toplam);
        myRef.child("islemler").child(tarih).setValue(sat);

    }

    private void ISLEM_KAYIT(String tarih,String islem_no){

        myRef.child(tarih).child(islem_no).setValue(islem_no);

    }





}
