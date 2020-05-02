package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

/**
 * The type Activity category.
 */
public class ActivityCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {

    /**
     * The Spinner.
     */
    Spinner spinner;
    /**
     * The Lv.
     */
    ListView lv;
    /**
     * The Tv.
     */
    TextView tv;
    /**
     * The T.
     */
    Intent t;

    /**
     * The Alp values 1.
     */
    ArrayList<String> alpValues1 = new ArrayList<String>();
    /**
     * The C value.
     */
    ArrayList<String> cValue = new ArrayList<>();
    /**
     * The Aa.
     */
    ArrayList<String> aa = new ArrayList<>();
    /**
     * The Bb.
     */
    ArrayList<String> bb = new ArrayList<>();

    /**
     * The Title 1.
     */
    ArrayList<String> title1 = new ArrayList<>();
    /**
     * The Title 2.
     */
    ArrayList<String>title2 = new ArrayList<>();
    /**
     * The Img.
     */
    ArrayList<Integer>img = new ArrayList<>();
    /**
     * The Check 1.
     */
    ArrayList<Boolean>check1 = new ArrayList<>();
    /**
     * The Check 2.
     */
    ArrayList<Boolean>check2 = new ArrayList<>();

    /**
     * The Category.
     */
    String category, /**
     * The Category 1.
     */
    category1, /**
     * The Uid.
     */
    uid, /**
     * The Str 1.
     */
    str1, /**
     * The Str 2.
     */
    str2;
    /**
     * The Str 3.
     */
    int str3, /**
     * The Str 4.
     */
    str4, /**
     * The Value 1.
     */
    value1, /**
     * The .
     */
    i;


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


    /**
     * Fill spinner category.
     */
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

    /**
     * Fill listview.
     */
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

    /**
     * Gets user.
     */
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("History");
        menu.add("Profile");
        menu.add("Area");
        menu.add("Back To Main");
        menu.add("Credits");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //activity menu selection
        String st = item.getTitle().toString();
        if (st.equals("Profile")) {
            t = new Intent(this, Profile.class);
            startActivity(t);
        }
        if (st.equals("Credits")) {
            Toast.makeText(this, "Credits", Toast.LENGTH_LONG).show();
        }

        if (st.equals("Area")) {
            t = new Intent(this,ActivityArea.class);
            startActivity(t);
        }
        if (st.equals("History")) {
            t = new Intent(this,History.class);
            startActivity(t);
        }

        if (st.equals("Back To Main")) {
            if (value1 == 1) {
                t = new Intent(this, mainMaintenance.class);
                startActivity(t);
            }
            if (value1==2 || value1==3){
                t = new Intent(this, mainRUser.class);
                startActivity(t);
            }
        }
        return true;
    }

}
