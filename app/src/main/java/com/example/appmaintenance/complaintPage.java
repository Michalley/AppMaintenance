package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.example.appmaintenance.FBreff.refCom;
import static com.example.appmaintenance.FBreff.refHis;

public class complaintPage extends AppCompatActivity {

    String user,date,time,category,zone,notes,name,pic,l;
    int state,emergency;

    EditText etNO;
    TextView tvD, tvT,tvAre,tvCat,tvC;
    ImageView ivC;
    RadioGroup rg;
    RadioButton rb3,rb4,rb5;

    Intent gi,t,ti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);

        tvC = (TextView) findViewById(R.id.tvC);
        etNO = (EditText) findViewById(R.id.etNo);
        tvD = (TextView) findViewById(R.id.tvD);
        tvT = (TextView) findViewById(R.id.tvT);
        tvAre = (TextView) findViewById(R.id.tvAre);
        tvCat = (TextView) findViewById(R.id.tvCat);
        ivC = (ImageView) findViewById(R.id.ivC);
        rg = (RadioGroup) findViewById(R.id.rg1);
        rb3 = (RadioButton) findViewById(R.id.rb3);
        rb4 = (RadioButton) findViewById(R.id.rb4);
        rb5 = (RadioButton) findViewById(R.id.rb5);

        gi = getIntent();
        name = gi.getStringExtra("name");
        l = gi.getStringExtra("screen");
        refCom.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Complain com = dataSnapshot.getValue(Complain.class);
                user = com.getUser();
                date = com.getDate();   tvD.setText(date);
                time = com.getTime();   tvT.setText(time);
                category = com.getCategory();   tvCat.setText(category);
                zone = com.getZone();   tvAre.setText(zone);
                emergency = com.getEmergency();
                state = com.getState();
                notes = com.getNotes();   etNO.setText(notes);
                pic = com.getPic();
                tvC.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Download();

        if (state==1){
            rb5.setChecked(false);
            rb4.setChecked(false);
            rb3.setChecked(true);
        }
        if (state==2){
            rb5.setChecked(false);
            rb4.setChecked(true);
            rb3.setChecked(false);
        }
        if (state==0){
            rb5.setChecked(true);
            rb4.setChecked(false);
            rb3.setChecked(false);
        }

        //Toast.makeText(complaintPage.this,l,Toast.LENGTH_SHORT);
    }

    private void Download(){

        final DatabaseReference dataRef = refCom.child(name).child("pic");

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(ivC);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(complaintPage.this, "No", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Back(View view) {
        if (l.equals("0")) {
            ti = new Intent(complaintPage.this, mainMaintenance.class);
        }
        if (l.equals("1")){
            ti = new Intent(complaintPage.this, ActivityArea.class);
        }
        if (l.equals("2")){
            ti = new Intent(complaintPage.this, ActivityCategory.class);
        }
        startActivity(ti);
    }

    public void save(View view) {
        notes = etNO.getText().toString();
        if (rb3.isChecked()){
            state=1;//started
        }
        if (rb4.isChecked()){
            state=2;//finished
        }
        if (rb5.isChecked()){
            state=0;//not
        }
        AlertDialog.Builder adb;
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Saving...");
        adb.setMessage("Are You Sure?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (state==0) {
                    refCom.child(name).child("notes").setValue(notes);
                    t = new Intent(complaintPage.this, mainMaintenance.class);
                    startActivity(t);
                }
                if (state==1){
                    refCom.child(name).child("notes").setValue(notes);
                    refCom.child(name).child("state").setValue(1);
                    t = new Intent(complaintPage.this, mainMaintenance.class);
                    startActivity(t);
                }
                if (state==2){
                    refCom.child(name).removeValue();
                    Complain co= new Complain(user,date,time,category,zone,emergency,state,notes,name,pic);
                    refHis.child(name).setValue(co);
                    t = new Intent(complaintPage.this, mainMaintenance.class);
                    startActivity(t);
                }
            }
        });
        adb.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

}
