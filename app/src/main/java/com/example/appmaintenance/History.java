package com.example.appmaintenance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.appmaintenance.FBreff.mAuth;
import static com.example.appmaintenance.FBreff.refHis;
import static com.example.appmaintenance.FBreff.refUsers;

public class History extends AppCompatActivity {

    ListView lv;

    Intent t;

    ArrayList<String> cValue = new ArrayList<>();

    String str1,uid; int value1;
    String value2,str2,value3;

    ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = (ListView) findViewById(R.id.lv);
        getUser();
        fillListView();
        setWhatList();

    }


    public void fillListView (){
        refHis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cValue.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    str1 = (String) data.getKey();
                    Complain c = dataSnapshot.getValue(Complain.class);
                    str2 = c.getUser();
                    setWhatList();
                }
                adp = new ArrayAdapter<String>(History.this, R.layout.support_simple_spinner_dropdown_item, cValue);
                lv.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getUser() {
        uid = mAuth.getInstance().getCurrentUser().getUid();
        refUsers.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User us = dataSnapshot.getValue(User.class);
                value1=us.getLevel();
                value2=us.getName()+" "+us.getlName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void setWhatList(){
        if (value1==2 ){
            if (str2.equals(value2)) {
                cValue.add(str1);
            }
        }
        if (value1==3) {
            if (str2.equals(value2)) {
                cValue.add(str1);
            }
        }
        if (value1==1){
            cValue.add(str1);
        }
        adp = new ArrayAdapter<String>(History.this, R.layout.support_simple_spinner_dropdown_item, cValue);
        lv.setAdapter(adp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Profile");
        if (value1==1) {
            menu.add("Category");
            menu.add("Area");
        }
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

        if (st.equals("Category")) {
            t = new Intent(this,ActivityCategory.class);
            startActivity(t);
        }
        if (st.equals("Area")) {
            t = new Intent(this,ActivityArea.class);
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
