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
import static com.example.appmaintenance.FBreff.refAre;
import static com.example.appmaintenance.FBreff.refCat;
import static com.example.appmaintenance.FBreff.refCom;
import static com.example.appmaintenance.FBreff.refUsers;

/**
 * The type Main maintenance.
 */
public class mainMaintenance extends AppCompatActivity implements AdapterView.OnItemClickListener{

    /**
     * The Lv.
     */
    ListView lv;
    /**
     * The Textview name.
     */
    TextView textviewName;

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
     * The Com value.
     */
    ArrayList<Complain> comValue = new ArrayList<>();

    /**
     * The Str 1.
     */
    String str1, /**
     * The Str 2.
     */
    str2, /**
     * The Uid.
     */
    uid, /**
     * The Value 2.
     */
    value2;
    /**
     * The Str 3.
     */
    int str3, /**
     * The .
     */
    i;
    /**
     * The Str 4.
     */
    int str4;

    /**
     * The Value 1.
     */
    int value1;
    /**
     * The T.
     */
    Intent t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maintenance);

        lv = (ListView) findViewById(R.id.lv);
        textviewName = (TextView) findViewById(R.id.textviewName);

        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        fillListView();

        getUser();

    }

    /**
     * Fill list view.
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
                    comValue.add(complain);
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
                DataAdapter adapter=new DataAdapter(mainMaintenance.this, title1, title2,check1,check2);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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
                value2 = us.getName()+" "+us.getlName();
                textviewName.setText("Hello "+value2+"!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = (String) lv.getItemAtPosition(position);
        Intent a;
        a = new Intent (mainMaintenance.this, complaintPage.class);
        a.putExtra("name",text);
        a.putExtra("screen","0");
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("History");
        menu.add("Category");
        menu.add("Area");
        menu.add("Profile");
        menu.add("Credits");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //activity menu selection
        String st = item.getTitle().toString();
        if (st.equals("History")) {
            t = new Intent(this, History.class);
            startActivity(t);
        }
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
        return true;
    }
}
