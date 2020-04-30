package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.appmaintenance.FBreff.mAuth;
import static com.example.appmaintenance.FBreff.refCat;
import static com.example.appmaintenance.FBreff.refCom;
import static com.example.appmaintenance.FBreff.refUsers;

public class ActivityCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {

    Spinner spinner;
    ListView lv;

    TextView tv;

    ArrayList<String> alpValues1 = new ArrayList<String>();
    ArrayList<String> cValue = new ArrayList<>();
    ArrayList<String> aa = new ArrayList<>();
    ArrayList<String> bb = new ArrayList<>();
    ArrayList<String> cc = new ArrayList<>();

    ArrayList<String> title1 = new ArrayList<>();
    ArrayList<String>title2 = new ArrayList<>();
    ArrayList<Integer>img = new ArrayList<>();
    ArrayList<Boolean>check1 = new ArrayList<>();
    ArrayList<Boolean>check2 = new ArrayList<>();

    String category;
    String category1;
    String uid;
    String str1;
    String str2;
    int str3;
    int str4;
    int value1,i;

    Intent t;

    ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        tv = (TextView) findViewById(R.id.textview);


        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        fillSpinnerC();
        fillListView();
        getUser();

    }


    public void fillSpinnerC () {
        refCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alpValues1.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    alpValues1.add(item.getValue().toString());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ActivityCategory.this, android.R.layout.simple_list_item_1, alpValues1);
                spinner.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void fillListView (){
        refCom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title1.clear();
                title2.clear();
                img.clear();
                check2.clear();
                i=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    str1 = (String) data.getKey();
                    Complain complain =data.getValue(Complain.class);
                    //comValue.add(complain);
                    str2 = complain.getDate()+"     "+complain.getTime();
                    str3 = complain.getState();
                    str4 = complain.getEmergency();
                    title1.add(str1);
                    title2.add(str2);
                    if (str4==1){
                        check1.add(false);
                    }
                    if (str4==2){
                        check1.add(true);
                    }

                    if (str3 == 0){
                        check2.add(false);
                    }
                    if (str3 == 1){
                        check2.add(true);
                    }

                }

                DataAdapter adapter=new DataAdapter(ActivityCategory.this, title1, title2,check1,check2);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        bb.clear();
        category = spinner.getSelectedItem().toString();

        if (parent.getId()==spinner.getId()) {
            if (category != spinner.getItemAtPosition(0).toString()) {
                refCom.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        cValue.clear();
                        aa.clear();
                        title1.clear();
                        title2.clear();
                        img.clear();
                        check2.clear();
                        i=0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String str1 = (String) data.getKey();
                            Complain complain =data.getValue(Complain.class);
                            str4 = complain.getEmergency();
                            category1 = complain.getCategory();
                            if (category1.equals(category)){
                                str2 = complain.getDate()+"     "+complain.getTime();
                                str3 = complain.getState();
                                //str4 = complain.getEmergency();
                                title1.add(str1);
                                title2.add(str2);

                                if (str4==1){
                                    check1.add(false);
                                }
                                if (str4==2){
                                    check1.add(true);
                                }

                                if (str3 == 0){
                                    check2.add(false);
                                }
                                if (str3 == 1){
                                    check2.add(true);
                                }
                                bb.add(str1);
                            }
                        }
                        //adp = new ArrayAdapter<String>(ActivityCategory.this,R.layout.support_simple_spinner_dropdown_item,bb);
                        //lv.setAdapter(adp);
                        //Toast.makeText(ActivityCategory.this,,Toast.LENGTH_SHORT).show();
                        DataAdapter adapter=new DataAdapter(ActivityCategory.this, title1, title2,check1,check2);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }else {
                fillListView();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = (String) lv.getItemAtPosition(position);
        Intent a;
        a = new Intent (ActivityCategory.this, complaintPage.class);
        a.putExtra("name",text);
        a.putExtra("screen","2");
        startActivity(a);
    }

    public void getUser() {
        uid = mAuth.getInstance().getCurrentUser().getUid();
        refUsers.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User us = dataSnapshot.getValue(User.class);
                value1=us.getLevel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
